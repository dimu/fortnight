package ftp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;


import java.sql.DriverManager;
import java.sql.SQLException;

public class Ftpclientutil {

	public static FTPClient ftpClient = new FTPClient();
	public static List<String> fileList = new ArrayList<String>();
	public static List<String> filenames = new ArrayList<String>();

	public static String getfilepath;
	public static String path;

	public static boolean open(String ip, int port, String username, String password) {

		if (ftpClient != null && ftpClient.isConnected())
			return true;
		try {

			ftpClient.connect("192.168.0.88", 21);
			ftpClient.login("dwx", "111111");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			ftpClient = null;
			return false;
		}
	}
	// 鍏抽棴杩炴帴
	public void closeConnect() {
		try {
			ftpClient.disconnect();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public static void getFileNameList(String ftpDirectory) {
		try {

			FTPFile[] ftpList = ftpClient.listFiles(ftpDirectory);
			for (FTPFile item : ftpList) {
				if (item.isFile()) {
					fileList.add(item.getName());
					 System.out.println(item.getName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getFileName(String f) {
		String fname = "";
		int i = f.lastIndexOf('.');

		if (i > 0 && i < f.length() - 1) {
			fname = f.substring(0, i);
		}
		return fname;
	}

	/**
	 * remoteFile 本地文件 localFile 远程文件
	 * 
	 * @throws FileNotFoundException
	 */
	public static synchronized void download11(String remoteFile, String localFile)
			throws FileNotFoundException {
		InputStream is = null;
		FileOutputStream os = null;
		ZipArchiveInputStream in = null;
		try {
			try {
				is =  ftpClient.retrieveFileStream(remoteFile);
	
				if (is == null) {
					System.out.println("remote ftp inputstream is null!");
				}
				
				in = new ZipArchiveInputStream(is);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
            
//			File fileout = new File(getfilepath);
			String outFileName = getFileName(localFile);

			os = new FileOutputStream(outFileName);

			byte[] bytes = new byte[1024 * 10];
			int c;
			while (true) {
				c = in.read(bytes);
				if (c == -1) {
					break;
				}
				os.write(bytes, 0, c);
			}
			// System.out.println("download success");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			throw new RuntimeException(ex);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static synchronized String pop() {
		if (fileList.size() > 0) {
			return fileList.remove(0);
		} else {
			return "ret";
		}
	}

	public static void getdownfilenamelist(List<String> fileobjects) {
		if (fileobjects.size() > 0) {
			for (String o : fileobjects) {
				String filenames = o;
				if (fileList.contains(filenames)) {
					fileList.remove(filenames);
				}
			}
		}
	}

	public static void filemain(String IP, int port, String username, String password, String getfilepaths,
			String paths, String Url, String u, String p, String sql) {

		getfilepath = getfilepaths;
		path = paths;

		getResource(Url, u, p, sql);

		open(IP, port, username, password);

		getFileNameList(getfilepaths);

		getdownfilenamelist(filenames);

		ExecutorService exe = Executors.newFixedThreadPool(50);

		for (int i = 0; i < 50; i++) {
			exe.execute(new ThreadClass());
		}
		exe.shutdown();
		while (true) {
			if (exe.isTerminated()) {
				// System.out.println("缁撴潫浜嗭紒");
				break;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static Connection getConn(String Url, String u, String p) {
		Connection conn = null;
		try {
			try {
				Class.forName("org.postgresql.Driver").newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
			String url = Url;
			try {
				conn = DriverManager.getConnection(url, u, p);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void getResource(String Url, String u, String p, String SQL) {
//		Connection conn = getConn(Url, u, p);
//		String sql = SQL;
//		Statement stmt = null;
//		ResultSet rs = null;
//
//		try {
//			stmt = conn.createStatement();
//			rs = stmt.executeQuery(sql);
//			while (rs.next()) {
//				filenames.add(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
	}

//	public static synchronized void download(String remoteFile, String localFile) throws FtpProtocolException {
//		InputStream is = null;
//		FileOutputStream os = null;
//		//ZipInputStream in = null;
//		try {
//
//			// ��ȡԶ�̻����ϵ��ļ�filename������TelnetInputStream�Ѹ��ļ����͵����ء�
//			is = (InputStream) ftpClient.getFileStream(remoteFile);
//			File file_in = new File(localFile);//
//			os = new FileOutputStream(file_in);
//            //in=new ZipInputStream(is);
//			byte[] bytes = new byte[1024*10];
//			int c;
//			while ((c = is.read(bytes)) != -1) {
//				os.write(bytes, 0, c);
//			}
//			System.out.println("���سɹ�");
//		} catch (IOException ex) {
//			System.out.println("����ʧ��");
//			ex.printStackTrace();
//			throw new RuntimeException(ex);
//		} finally {
//			try {
//				if (is != null) {
//					is.close();
//				}
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					if (os != null) {
//						os.close();
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	@SuppressWarnings("resource")
	public static synchronized void download(String remoteFile, String localFile)  {
		InputStream is = null;
		FileOutputStream os = null;
//		ZipInputStream in = null;
		ZipArchiveInputStream in = null;
		
		try {

			// 获取远程机器上的文件filename，借助TelnetInputStream把该文件传送到本地。
//			ftpClient.setFileType(FTP.DEFAULT_DATA_PORT);
			is = ftpClient.retrieveFileStream(remoteFile);
			in = new ZipArchiveInputStream(is);
			File file_in = new File(localFile);//		
    		os = new FileOutputStream(file_in);
    		
//    		ftpClient.retrieveFile(remoteFile, os);
//    		os.close();
    		
//			if(in == null)
//				{System.out.println("null");}
//			else
//				{System.out.println("not null");}

			
			byte[] bytes = new byte[1024*10];
			int c;
			while ((c = in.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			System.out.println("download success");
		} catch (IOException ex) {
			System.out.println("����ʧ��");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// FTP涓嬭�?
	public static void main(String[] args) {
		long dt = new Date().getTime();
		System.out.println(dt);
		filemain("192.168.0.88", 21, "dwx", "111111", "/chr/", "E:/CHR/",
				"jdbc:postgresql://192.168.0.23:5432/kettle", "postgres", "111111", "SELECT filename FROM file_info");
	}
	
	
}

class ThreadClass implements Runnable {

	@Override
	public void run() {
		while (true) {
			String ls = Ftpclientutil.pop();
			if (ls.equals("ret")) {
				Date dt = new Date();
				System.out.println(dt);
				break;
			}
			try {
				Ftpclientutil.download11(Ftpclientutil.getfilepath + ls, Ftpclientutil.path + ls);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 瑙ｅ帇缂�?
			// UncompressFileGZIP.doUncompressFile(Ftpclientutil.path + ls);
		}

	}
	
	  

}
