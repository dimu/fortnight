package gis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/**
 * 坐标转换程序,源程序来源与网络,BMB修改版
 * 
 * WGS84坐标系：即地球坐标系，国际上通用的坐标系。Earth
 * 
 * GCJ02坐标系：即火星坐标系，WGS84坐标系经加密后的坐标系。Mars
 * 
 * BD09坐标系：即百度坐标系，GCJ02坐标系经加密后的坐标系。 Bd09
 * 
 * 搜狗坐标系、图吧坐标系等，估计也是在GCJ02基础上加密而成的。
 * 
 * 百度地图API 百度坐标 腾讯搜搜地图API 火星坐标 搜狐搜狗地图API 搜狗坐标* 阿里云地图API 火星坐标 图吧MapBar地图API 图吧坐标
 * 高德MapABC地图API 火星坐标 灵图51ditu地图API 火星坐标
 * 
 * @author BMB
 *
 */
public class CoordinateConvert {
	private static double PI = Math.PI;
	private static double AXIS = 6378245.0; //
	private static double OFFSET = 0.00669342162296594323; // (a^2 - b^2) / a^2
	private static double X_PI = PI * 3000.0 / 180.0;

	// GCJ-02=>BD09 火星坐标系=>百度坐标系
	public static double[] gcj2BD09(double glat, double glon) {
		double x = glon;
		double y = glat;
		double[] latlon = new double[2];
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
		latlon[0] = z * Math.sin(theta) + 0.006;
		latlon[1] = z * Math.cos(theta) + 0.0065;
		return latlon;
	}

	// BD09=>GCJ-02 百度坐标系=>火星坐标系
	public static double[] bd092GCJ(double glat, double glon) {
		double x = glon - 0.0065;
		double y = glat - 0.006;
		double[] latlon = new double[2];
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		latlon[0] = z * Math.sin(theta);
		latlon[1] = z * Math.cos(theta);
		return latlon;
	}

	// BD09=>WGS84 百度坐标系=>地球坐标系
	public static double[] bd092WGS(double glat, double glon) {
		double[] latlon = bd092GCJ(glat, glon);
		return gcj2WGS(latlon[0], latlon[1]);
	}

	// WGS84=》BD09 地球坐标系=>百度坐标系
	public static double[] wgs2BD09(double wgLat, double wgLon) {
		double[] latlon = wgs2GCJ(wgLat, wgLon);
		return gcj2BD09(latlon[0], latlon[1]);
	}

	// WGS84=》GCJ02 地球坐标系=>火星坐标系
	public static double[] wgs2GCJ(double wgLat, double wgLon) {
		double[] latlon = new double[2];
		if (outOfChina(wgLat, wgLon)) {
			latlon[0] = wgLat;
			latlon[1] = wgLon;
			return latlon;
		}
		double[] deltaD = delta(wgLat, wgLon);
		latlon[0] = wgLat + deltaD[0];
		latlon[1] = wgLon + deltaD[1];
		return latlon;
	}

	// GCJ02=>WGS84 火星坐标系=>地球坐标系(粗略)
	public static double[] gcj2WGS(double glat, double glon) {
		double[] latlon = new double[2];
		if (outOfChina(glat, glon)) {
			latlon[0] = glat;
			latlon[1] = glon;
			return latlon;
		}
		double[] deltaD = delta(glat, glon);
		latlon[0] = glat - deltaD[0];
		latlon[1] = glon - deltaD[1];
		return latlon;
	}

	// GCJ02=>WGS84 火星坐标系=>地球坐标系（精确）
	public static double[] gcj2WGSExactly(double gcjLat, double gcjLon) {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
		double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLat = (mLat + pLat) / 2;
			wgsLon = (mLon + pLon) / 2;
			double[] tmp = wgs2GCJ(wgsLat, wgsLon);
			dLat = tmp[0] - gcjLat;
			dLon = tmp[1] - gcjLon;
			if ((Math.abs(dLat) < threshold) && (Math.abs(dLon) < threshold))
				break;

			if (dLat > 0)
				pLat = wgsLat;
			else
				mLat = wgsLat;
			if (dLon > 0)
				pLon = wgsLon;
			else
				mLon = wgsLon;

			if (++i > 10000)
				break;
		}
		double[] latlon = new double[2];
		latlon[0] = wgsLat;
		latlon[1] = wgsLon;
		return latlon;
	}

	// 两点距离
	public static double distance(double latA, double logA, double latB, double logB) {
		int earthR = 6371000;
		double x = Math.cos(latA * Math.PI / 180) * Math.cos(latB * Math.PI / 180)
				* Math.cos((logA - logB) * Math.PI / 180);
		double y = Math.sin(latA * Math.PI / 180) * Math.sin(latB * Math.PI / 180);
		double s = x + y;
		if (s > 1)
			s = 1;
		if (s < -1)
			s = -1;
		double alpha = Math.acos(s);
		double distance = alpha * earthR;
		return distance;
	}

	public static double[] delta(double wgLat, double wgLon) {
		double[] latlng = new double[2];
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - OFFSET * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
		dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
		latlng[0] = dLat;
		latlng[1] = dLon;
		System.out.println(dLon + ":" + dLat);
		return latlng;
	}

	public static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	public static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	public static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}
	
	@Test
	public void convertTest() {
		double[] convert = wgs2GCJ(29.4525700101432, 107.679199601075);
		System.out.println(convert[0] + ":" + convert[1]);
//		int[] xx = getTileNumber1(convert[1], convert[0], 18);
//		System.out.println(xx[0] + ":" + xx[1]);
	}
	
	public static double[] delta1(double wgLat, double wgLon) {
		double[] latlng = new double[2];
		double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
		double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);
		double radLat = wgLat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - OFFSET * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLat = (dLat * 180.0) / ((AXIS * (1 - OFFSET)) / (magic * sqrtMagic) * PI);
		dLon = (dLon * 180.0) / (AXIS / sqrtMagic * Math.cos(radLat) * PI);
		latlng[0] = dLat;
		latlng[1] = dLon;
		System.out.println(dLon + ":" + dLat);
		return latlng;
	}

	public void parserLineString1(String line, ListMultimap<String, String> lmm) {
		String[] lineArray = line.split(",");
		for (String item : lineArray) {
			String[] lonLat = item.split(" ");
			double[] convert = wgs2GCJ(Double.valueOf(lonLat[1]), Double.valueOf(lonLat[0]));
			// Double x = convert.getX();
			// Double y = convert.getY();
			// 纬度
			double lat = convert[0];
			// 经度
			double lon = convert[1];

			int[] zxy = getTileNumber1(lat, lon, 18);

			int cx = zxy[0];
			int cy = zxy[1];
			int zx = zxy[2];
			int zy = zxy[3];

			// int zx = new Double(Double.sum(x, -cx) * 256).intValue();
			// int zy = new Double(Double.sum(y, -cy) * 256).intValue();
			// System.out.println("double x,y :" + convert.getX() + ":" +
			// convert.getY());
			// System.out.println("long x, y: " + cx + ":" + cy);
			// System.out.println("zb x, y:" + zx + ":" + zy);
			lmm.put(cx + "_" + cy, zx + "_" + zy);
		}
	}

	@Test
	public void parserCSV1() {
		File csv = new File("d:\\1.csv");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		ListMultimap<String, String> lmm = ArrayListMultimap.create();
		try {
			List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				everyLine = line;
				everyLine = everyLine.substring(18, line.length() - 3);
				// System.out.println(everyLine);
				allString.add(everyLine);
			}
			System.out.println("csv表格中所有行数：" + allString.size());

			for (String item : allString) {
				parserLineString1(item, lmm);
			}
			Set<String> keys = lmm.keySet();
			for (String key : keys) {
				List<String> list = lmm.get(key);
				System.out.println("keys :" + key + " values: " + list.toString());
				draw1(key, list);
			}
			System.out.println("所有坐标系个数： " + lmm.keySet().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void draw1(String key, List<String> value) throws IOException {

		BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		Graphics gc = image.createGraphics();
		gc.setColor(Color.GREEN);
		// gc.fillPolygon(xArray, yArray, length);
		int length = value.size();
		int[] xArray = new int[length];
		int[] yArray = new int[length];
		for (int i = 0; i <= length - 1; i++) {
			xArray[i] = Integer.valueOf(value.get(i).split("_")[0]);
			yArray[i] = Integer.valueOf(value.get(i).split("_")[1]);
		}

		for (int j = 0; j <= length - 2; j++) {
			gc.drawLine(xArray[j], yArray[j], xArray[j + 1], yArray[j + 1]);
		}

		gc.dispose();
		ImageIO.write(image, "png", new File("d:/test1/" + key + ".png"));
		// File imageFile = new File("D://2.png");
		// if (!imageFile.exists()) {
		// imageFile.createNewFile();
		// }
		// BufferedImage bi = ImageIO.read(imageFile);
		// Graphics gc = bi.createGraphics();
		// gc.fillPolygon(xArray, yArray, length);
		//
		// gc.dispose();
		// bi.flush();
	}
	
	public static int[] getTileNumber1(final double lat, final double lon, final int zoom) {
		// double xtile = Math.floor((lon + 180) / 360 * (1 << zoom));
			int itile =(int) Math
		 .floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 /
		 Math.cos(Math.toRadians(lat))) / Math.PI) / 2
		 * (1 << zoom));
		double xtile = (lon + 180) / 360 * (1 << zoom);
		double ytile = (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
				* (1 << zoom);
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (itile < 0)
			itile = 0;
		if (itile >= (1 << zoom))
			itile = ((1 << zoom) - 1);

		int zy = new Double(Double.sum(ytile, -itile) * 256).intValue();
		itile = Integer.valueOf(String.valueOf((int) Math.pow(2, zoom))) - 1 - itile;

		int cx = (int) Math.floor(xtile);
//		int cy = itile;

		int zx = new Double(Double.sum(xtile, -cx) * 256).intValue();

		

		return new int[] { cx, itile, zx, zy };

	}

}