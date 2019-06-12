/**
 * Created on 2015年12月23日
 *
 * Copyright(c) Open Data Mining, 2015.  All rights reserved. 
 */
package gis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
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
 * <pre>
 * 各类坐标系的转换算法，包括WGS84\WebMercator（Web墨卡托）\GCJ02\BD09MC\BD09LL。
 * 经测试，误差在3米内。
 * GCJ02 国测局加密坐标
 * DB09LL 百度经纬度坐标，在GCJ02上再次加密
 * DB09MC 百度魔卡托坐标 米制
 * </pre>
 * 
 * <pre>
 * <b>
 * 典型使用方式：直接调用相应静态函数。
 * 典型代码如下（实验坐标：N29°31′45.63″ E106°29′20.12″）：</b>
	double lon = 106D + (29d / 60d) + (20.12d / 3600d);
	double lat = 29d + (31d / 60d) + (45.63d / 3600d);
	Point2D.Double c = CordinateConverter.wgs84ToGCJ02(lon, lat);
 * </pre>
 * 
 * @author 何山(heshan07@foxmail.com)
 */
public class CordinateConverter {
	// 3.141592653589793238462643383279502884197
	// private static final double PI = 3.14159265358979324;
	// private static final double X_PI = 3.14159265358979324 * 3000.0 / 180.0;
	private static double PI = Math.PI;
	private static double AXIS = 6378245.0; //
	private static double OFFSET = 0.00669342162296594323; // (a^2 - b^2) / a^2
	private static double X_PI = PI * 3000.0 / 180.0;

	// 百度米制坐标范围
	private static final Double[] BD_MCBAND = { 12890594.86, 8362377.87, 5591021d, 3481989.83, 1678043.12, 0d };

	// 百度经纬度坐标范围
	private static final Double[] BD_LLBAND = { 75d, 60d, 45d, 30d, 15d, 0d };

	// 百度米制坐标转百度经纬度坐标所需矩阵
	private static final Double[][] BD_MC2LL = {
			{ 1.410526172116255e-8, 0.00000898305509648872, -1.9939833816331, 200.9824383106796, -187.2403703815547,
					91.6087516669843, -23.38765649603339, 2.57121317296198, -0.03801003308653, 17337981.2 },
			{ -7.435856389565537e-9, 0.000008983055097726239, -0.78625201886289, 96.32687599759846, -1.85204757529826,
					-59.36935905485877, 47.40033549296737, -16.50741931063887, 2.28786674699375, 10260144.86 },
			{ -3.030883460898826e-8, 0.00000898305509983578, 0.30071316287616, 59.74293618442277, 7.357984074871,
					-25.38371002664745, 13.45380521110908, -3.29883767235584, 0.32710905363475, 6856817.37 },
			{ -1.981981304930552e-8, 0.000008983055099779535, 0.03278182852591, 40.31678527705744, 0.65659298677277,
					-4.44255534477492, 0.85341911805263, 0.12923347998204, -0.04625736007561, 4482777.06 },
			{ 3.09191371068437e-9, 0.000008983055096812155, 0.00006995724062, 23.10934304144901, -0.00023663490511,
					-0.6321817810242, -0.00663494467273, 0.03430082397953, -0.00466043876332, 2555164.4 },
			{ 2.890871144776878e-9, 0.000008983055095805407, -3.068298e-8, 7.47137025468032, -0.00000353937994,
					-0.02145144861037, -0.00001234426596, 0.00010322952773, -0.00000323890364, 826088.5 } };

	// 百度经纬度坐标转百度米制坐标所需矩阵
	private static final Double[][] BD_LL2MC = {
			{ -0.0015702102444, 111320.7020616939, 1704480524535203d, -10338987376042340d, 26112667856603880d,
					-35149669176653700d, 26595700718403920d, -10725012454188240d, 1800819912950474d, 82.5 },
			{ 0.0008277824516172526, 111320.7020463578, 647795574.6671607, -4082003173.641316, 10774905663.51142,
					-15171875531.51559, 12053065338.62167, -5124939663.577472, 913311935.9512032, 67.5 },
			{ 0.00337398766765, 111320.7020202162, 4481351.045890365, -23393751.19931662, 79682215.47186455,
					-115964993.2797253, 97236711.15602145, -43661946.33752821, 8477230.501135234, 52.5 },
			{ 0.00220636496208, 111320.7020209128, 51751.86112841131, 3796837.749470245, 992013.7397791013,
					-1221952.21711287, 1340652.697009075, -620943.6990984312, 144416.9293806241, 37.5 },
			{ -0.0003441963504368392, 111320.7020576856, 278.2353980772752, 2485758.690035394, 6070.750963243378,
					54821.18345352118, 9540.606633304236, -2710.55326746645, 1405.483844121726, 22.5 },
			{ -0.0003218135878613132, 111320.7020701615, 0.00369383431289, 823725.6402795718, 0.46104986909093,
					2351.343141331292, 1.58060784298199, 8.77738589078284, 0.37238884252424, 7.45 } };

	/**
	 * WGS-84 to GCJ-02
	 * 
	 * @param wgsLon
	 *            wgs84经度
	 * @param wgsLat
	 *            wgs84纬度
	 * @return
	 */
	public static Point2D.Double wgs84ToGCJ02(double wgsLon, double wgsLat) {
		if (outOfChina(wgsLon, wgsLat))
			return new Point2D.Double(wgsLon, wgsLat);
		Point2D.Double d = delta(wgsLon, wgsLat);
		return new Point2D.Double(wgsLon + d.getX(), wgsLat + d.getY());
	}

	/**
	 * GCJ-02 to WGS-84
	 * 
	 * @param gcjLat
	 *            gcj02纬度
	 * @param gcjLon
	 *            gcj02经度
	 * @return
	 */
	public static Point2D.Double gcj02ToWGS84(double gcjLon, double gcjLat) {
		if (outOfChina(gcjLon, gcjLat))
			return new Point2D.Double(gcjLon, gcjLat);
		Point2D.Double d = delta(gcjLon, gcjLat);
		return new Point2D.Double(gcjLon - d.getX(), gcjLat - d.getY());
	}

	/**
	 * GCJ-02 to WGS-84 exactly
	 * 
	 * @param gcjLon
	 *            gcj02经度
	 * @param gcjLat
	 *            gcj02纬度
	 * @return
	 */
	public static Point2D.Double gcj02ToWGS84PreHandle(double gcjLon, double gcjLat) {
		double initDelta = 0.01;
		double threshold = 0.000000001;
		double dLat = initDelta, dLon = initDelta;
		double mLat = gcjLat - dLat, mLon = gcjLon - dLon;
		double pLat = gcjLat + dLat, pLon = gcjLon + dLon;
		double wgsLat, wgsLon, i = 0;
		while (true) {
			wgsLon = (mLon + pLon) / 2;
			wgsLat = (mLat + pLat) / 2;
			Point2D.Double tmp = wgs84ToGCJ02(wgsLon, wgsLat);
			dLon = tmp.getX() - gcjLon;
			dLat = tmp.getY() - gcjLat;
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
		return new Point2D.Double(wgsLon, wgsLat);
	}

	/**
	 * GCJ-02 to BD09LL（百度经纬度坐标系）
	 * 
	 * @param gcjLon
	 *            gcj02经度
	 * @param gcjLat
	 *            gcj02纬度
	 * @return
	 */
	public static Point2D.Double gcj02ToBD09LL(double gcjLon, double gcjLat) {
		double x = gcjLon, y = gcjLat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * X_PI);
		double bdLon = z * Math.cos(theta) + 0.0065;
		double bdLat = z * Math.sin(theta) + 0.006;
		return new Point2D.Double(bdLon, bdLat);
	}

	/**
	 * BD-09ll to GCJ-02
	 * 
	 * @param bdLon
	 *            bd09经度
	 * @param bdLat
	 *            bd09纬度
	 * @return
	 */
	public static Point2D.Double bd09llToGCJ02(double bdLon, double bdLat) {
		double x = bdLon - 0.0065, y = bdLat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * X_PI);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * X_PI);
		double gcjLon = z * Math.cos(theta);
		double gcjLat = z * Math.sin(theta);
		return new Point2D.Double(gcjLon, gcjLat);
	}

	/**
	 * BD-09ll to BD09MC（百度米制坐标）
	 * 
	 * @param bdLon
	 *            bd09经度
	 * @param bdLat
	 *            bd09纬度
	 * @return
	 */
	public static Point2D.Double bd09llToBD09MC(Double bdLon, Double bdLat) {
		Double[] cE = null;
		bdLon = getLoop(bdLon, -180, 180);
		bdLat = getRange(bdLat, -74, 74);
		for (int i = 0; i < BD_LLBAND.length; i++) {
			if (bdLat >= BD_LLBAND[i]) {
				cE = BD_LL2MC[i];
				break;
			}
		}
		if (cE != null) {
			for (int i = BD_LLBAND.length - 1; i >= 0; i--) {
				if (bdLat <= -BD_LLBAND[i]) {
					cE = BD_LL2MC[i];
					break;
				}
			}
		}
		return converter(bdLon, bdLat, cE);
	}

	/**
	 * BD-09mc to BD-09ll
	 * 
	 * @param x
	 *            BD-09mc x值,相当于经度
	 * @param y
	 *            BD-09mc y值,相当于纬度
	 * @return
	 */
	public static Point2D.Double bd09mcToBD09LL(Double x, Double y) {
		Double[] cF = null;
		x = Math.abs(x);
		y = Math.abs(y);

		for (int cE = 0; cE < BD_MCBAND.length; cE++) {
			if (y >= BD_MCBAND[cE]) {
				cF = BD_MC2LL[cE];
				break;
			}
		}
		Point2D.Double location = converter(x, y, cF);
		return location;
	}

	/**
	 * WGS-84 to Web mercator <br/>
	 * mercatorLon -> x, mercatorLat -> y
	 * 
	 * @param wgsLon
	 *            wgs经度
	 * @param wgsLat
	 *            wgs纬度
	 * @return
	 */
	public Point2D.Double wgs84ToWebMercator(double wgsLon, double wgsLat) {
		double x = wgsLon * 20037508.34 / 180.;
		double y = Math.log(Math.tan((90. + wgsLat) * PI / 360.)) / (PI / 180.);
		y = y * 20037508.34 / 180.;
		return new Point2D.Double(x, y);
	}

	/**
	 * Web mercator to WGS-84 <br/>
	 * mercatorLon -> x, mercatorLat -> y
	 * 
	 * @param mercatorLon
	 * @param mercatorLat
	 * @return
	 */
	public static Point2D.Double webMercatorToWGS84(double mercatorLon, double mercatorLat) {
		double x = mercatorLon / 20037508.34 * 180.;
		double y = mercatorLat / 20037508.34 * 180.;
		y = 180 / PI * (2 * Math.atan(Math.exp(y * PI / 180.)) - PI / 2);
		return new Point2D.Double(x, y);
	}

	/**
	 * 计算两个经纬度坐标点之间的距离 longitude latitude distance
	 * 
	 * @param lonA
	 *            A点经度
	 * @param latA
	 *            A点纬度
	 * 
	 * @param lonB
	 *            B点经度
	 * @param latB
	 *            B点纬度
	 * @return
	 */
	public double llDistance(double lonA, double latA, double lonB, double latB) {
		double earthR = 6371000.;
		double x = Math.cos(latA * PI / 180.) * Math.cos(latB * PI / 180.) * Math.cos((lonA - lonB) * PI / 180);
		double y = Math.sin(latA * PI / 180.) * Math.sin(latB * PI / 180.);
		double s = x + y;
		if (s > 1)
			s = 1;
		if (s < -1)
			s = -1;
		double alpha = Math.acos(s);
		double distance = alpha * earthR;
		return distance;
	}

	private static Double getLoop(Double lng, Integer min, Integer max) {
		while (lng > max) {
			lng -= max - min;
		}
		while (lng < min) {
			lng += max - min;
		}
		return lng;
	}

	private static Double getRange(Double lat, Integer min, Integer max) {
		if (min != null) {
			lat = Math.max(lat, min);
		}
		if (max != null) {
			lat = Math.min(lat, max);
		}
		return lat;
	}

	private static Point2D.Double converter(Double x, Double y, Double[] cE) {
		Double xTemp = cE[0] + cE[1] * Math.abs(x);
		Double cC = Math.abs(y) / cE[9];
		Double yTemp = cE[2] + cE[3] * cC + cE[4] * cC * cC + cE[5] * cC * cC * cC + cE[6] * cC * cC * cC * cC
				+ cE[7] * cC * cC * cC * cC * cC + cE[8] * cC * cC * cC * cC * cC * cC;
		xTemp *= (x < 0 ? -1 : 1);
		yTemp *= (y < 0 ? -1 : 1);
		return new Point2D.Double(xTemp, yTemp);
	}

	private static Point2D.Double delta(double lon, double lat) {
		// 卫星椭球坐标投影到平面地图坐标系的投影因子。
		double a = 6378245.0;
		// 椭球的偏心率。
		double ee = 0.00669342162296594323;
		double dLat = transformLat(lon - 105.0, lat - 35.0);
		double dLon = transformLon(lon - 105.0, lat - 35.0);
		double radLat = lat / 180.0 * PI;
		double magic = Math.sin(radLat);
		magic = 1 - ee * magic * magic;
		double sqrtMagic = Math.sqrt(magic);
		dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
		dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
		return new Point2D.Double(dLon, dLat);
	}

	private static boolean outOfChina(double lon, double lat) {
		if (lon < 72.004 || lon > 137.8347)
			return true;
		if (lat < 0.8293 || lat > 55.8271)
			return true;
		return false;
	}

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
		return ret;
	}

	public static void main(String[] args) {

		// 上方新人居 北纬N29°32′60″ 东经E106°28′17.89
		// 纬度
		// double lat = 29d + (32d / 60d) + (2.82d / 3600d);
		// 经度
		// double lon = 106D + (28d / 60d) + (17.89d / 3600d);

		// 电信实业大厦 北纬N29°31′45.63″ 东经E106°29′20.12
		// 经度
		double lon = 106D + (29d / 60d) + (20.12d / 3600d);
		// 纬度
		double lat = 29d + (31d / 60d) + (45.63d / 3600d);

		// 重庆市规划展览馆 北纬N29°34′10.96″ 东经E106°35′3.36
		// 经度
		// double lon = 106D + (35d / 60d) + (3.36d / 3600d);
		// 纬度
		// double lat = 29d + (34d / 60d) + (10.96d / 3600d);

		// 重庆市江北国际机场 北纬N29°43′14.76″ 东经E106°38′4.59″
		// 经度
		// double lon = 106D + (38d / 60d) + (4.59d / 3600d);
		// 纬度
		// double lat = 29d + (43d / 60d) + (14.76d / 3600d);

		System.out.println("转换成WGS-84坐标(GPS模块,谷歌地球):" + lon + "," + lat);

		Point2D.Double c = CordinateConverter.wgs84ToGCJ02(lon, lat);
		System.out.println("转换成GCJ-02坐标(谷歌、高德、腾讯):" + c);

		c = CordinateConverter.gcj02ToBD09LL(c.getX(), c.getY());
		System.out.println("转换成BD-09ll坐标(百度):" + c);

		c = CordinateConverter.bd09llToBD09MC(c.getX(), c.getY());
		System.out.println("转换成BD-09mc坐标(百度):" + String.format("%f", c.getX()) + "," + String.format("%f", c.getY()));

		lon = 106.60361388888889;
		lat = 29.53547777777778;
		c = wgs84ToGCJ02(lon, lat);
		System.out.println("转换成GCJ-02坐标(谷歌、高德、腾讯):" + c);

		c = gcj02ToBD09LL(c.getX(), c.getY());
		System.out.println("转换成BD-09ll坐标(百度):" + c);

		c = bd09llToBD09MC(c.getX(), c.getY());
		System.out.println("转换成BD-09mc坐标(百度):" + String.format("%f", c.getX()) + "," + String.format("%f", c.getY()));

	}

	public Point2D.Double wgs84ToWebMercator(double wgsLon, double wgsLat, int zoom) {
		double pow = Math.pow(2, 18 - zoom);
		double x = wgsLon * 20037508.34 / 180.;
		x = x / pow;
		x = x / 256;
		double y = Math.log(Math.tan((90. + wgsLat) * PI / 360.)) / (PI / 180.);
		y = y * 20037508.34 / 180.;
		y = y / pow;
		y = y / 256;
		return new Point2D.Double(x, y);
	}

	public static void draw(int[] xArray, int[] yArray, int length) throws IOException {

		BufferedImage image = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		Graphics gc = image.createGraphics();
		gc.setColor(Color.red);
		// gc.fillPolygon(xArray, yArray, length);
		gc.drawLine(39, 246, 42, 241);
		gc.drawLine(42, 241, 50, 233);
		gc.dispose();
		ImageIO.write(image, "png", new File("d:/test.png"));
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

	@Test
	public void wlGisTest() {
		// MULTILINESTRING((107.746287096869 29.3583097113645,107.746346119437
		// 29.3569044154467,107.746452988703 29.3567014336912,107.746770592984
		// 29.3564582886497))

		String line = "107.746287096869 29.3583097113645,107.746346119437 29.3569044154467,107.746452988703 29.3567014336912,107.746770592984 29.3564582886497";
		String[] lineArray = line.split(",");

		for (String item : lineArray) {
			String[] lonLat = item.split(" ");
			Point2D.Double convert = wgs84ToWebMercator(Double.valueOf(lonLat[0]), Double.valueOf(lonLat[1]), 16);
			Double x = convert.getX();
			Double y = convert.getY();
			long cx = x.longValue();
			long cy = y.longValue();
			int zx = new Double(Double.sum(x, -cx) * 256).intValue();
			int zy = new Double(Double.sum(y, -cy) * 256).intValue();
			System.out.println("double x,y :" + convert.getX() + ":" + convert.getY());
			System.out.println("long x, y: " + cx + ":" + cy);
			System.out.println("zb x, y:" + zx + ":" + zy);

		}

		// Point2D.Double convert = wgs84ToWebMercator(107.769539334489,
		// 29.3321521664022, 16);
		// System.out.println(convert.getX() + ":" + convert.getY());
	}

	@Test
	public void drawMulti() throws IOException {
		int[] xArray = new int[] { 39, 42, 50 };
		int[] yArray = new int[] { 246, 241, 233 };
		draw(xArray, yArray, 2);
	}

	public void parserLineString(String line, ListMultimap<String, String> lmm) {
		String[] lineArray = line.split(",");
		for (String item : lineArray) {
			String[] lonLat = item.split(" ");
			Point2D.Double convert = wgs84ToWebMercator(Double.valueOf(lonLat[0]), Double.valueOf(lonLat[1]), 16);
			Double x = convert.getX();
			Double y = convert.getY();
			Double cx = Math.floor(x);
			Double cy = Math.floor(y);
			int zx = new Double(Double.sum(x, -cx) * 256).intValue();
			int zy = new Double(Double.sum(y, -cy) * 256).intValue();
			// System.out.println("double x,y :" + convert.getX() + ":" +
			// convert.getY());
			// System.out.println("long x, y: " + cx + ":" + cy);
			// System.out.println("zb x, y:" + zx + ":" + zy);
			lmm.put(cx + "_" + cy, zx + "_" + zy);
		}
	}

	public static void draw(String key, List<String> value) throws IOException {

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
		ImageIO.write(image, "png", new File("d:/test/" + key + ".png"));
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

	@Test
	public void parserCSV() {
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
				draw(key, list);
			}
			System.out.println("所有坐标系个数： " + lmm.keySet().size());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* 国际通用TMS服务瓦片编号计算，信息来源于WIKI学术数据公式转换 */
	public static String getTileNumber(final double lat, final double lon, final int zoom, int timezone) {
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math
				.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
						* (1 << zoom));
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);

		return tengXunTtileVlaue(xtile, ytile, zoom, timezone);

	}

	public static int[] getTileNumber1(final double lat, final double lon, final int zoom) {
		// double xtile = Math.floor((lon + 180) / 360 * (1 << zoom));
		// double ytile = Math
		// .floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 /
		// Math.cos(Math.toRadians(lat))) / Math.PI) / 2
		// * (1 << zoom));
		double xtile = (lon + 180) / 360 * (1 << zoom);
		double ytile = (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2
				* (1 << zoom);
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);

		ytile = Integer.valueOf(String.valueOf((int) Math.pow(2, zoom))) - 1 - ytile;

		int cx = (int) Math.floor(xtile);
		int cy = (int) Math.floor(ytile);

		int zx = new Double(Double.sum(xtile, -cx) * 256).intValue();

		int zy = new Double(Double.sum(ytile, -cy) * 256).intValue();

		return new int[] { cx, cy, zx, zy };

	}

	/* 腾讯TMS服务器瓦片编号计算，基于国际通用TMS瓦片编号结果计算 */
	public static String tengXunTtileVlaue(int x, int y, int z, int timezone) {
		y = Integer.valueOf(String.valueOf((int) Math.pow(2, z))) - 1 - y;

		String url = "http://rtt2b.map.qq.com/rtt/?" + "z=" + z + "&x=" + String.valueOf(x) + "&y=" + String.valueOf(y)
				+ "&timeKey" + String.valueOf(timezone);

		return url;
	}

	// WGS84=》GCJ02 地球坐标系=>火星坐标系
	public static double[] wgs2GCJ(double wgLat, double wgLon) {
		double[] latlon = new double[2];
		if (outOfChina(wgLat, wgLon)) {
			latlon[0] = wgLat;
			latlon[1] = wgLon;
			return latlon;
		}
		double[] deltaD = delta1(wgLat, wgLon);
		latlon[0] = wgLat + deltaD[0];
		latlon[1] = wgLon + deltaD[1];
		return latlon;
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
			double[] convert = wgs2GCJ(Double.valueOf(lonLat[0]), Double.valueOf(lonLat[1]));
			// Double x = convert.getX();
			// Double y = convert.getY();
			// 经度
			double lon = convert[0];
			// 纬度
			double lat = convert[1];

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

	@Test
	public void convertTest() {
		double[] convert = wgs2GCJ(29.4525700101432,107.679199601075);
		System.out.println(convert[0] + ":" + convert[1]);
		int[] xx = getTileNumber1(convert[1], convert[0], 18);
		System.out.println(xx[0] + ":" + xx[1]);
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

}
