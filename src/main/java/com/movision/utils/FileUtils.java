package com.movision.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jianglz
 * @since 14/11/4.
 */
public class FileUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(FileUtils.class);

	public static void main(String[] args) {
		// String filename =
		// getPicName("http://139.196.189.100/upload/brand/img/613.jpg");
		// System.out.println(filename);
		try {
			List<String> list = readfileName("F:/Download_pic/img_100_100", "png");
			for (Object str : list) {
				System.out.println(str);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取指定文件夹下的文件
	 * 
	 * @param filepath
	 *            文件夹路径
	 * @param suffix
	 *            匹配的文件的后缀，如jpg
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String> readfile(String filepath, String suffix)
			throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(filepath);
			if (!file.isDirectory() && file.getName().endsWith("." + suffix)) {

				list.add(file.getAbsolutePath());
			} else if (file.isDirectory()) {
				System.out.println("文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()
							&& readfile.getName().endsWith("." + suffix)) {

						list.add(readfile.getAbsolutePath());

					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i], suffix);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取指定文件夹下的文件的名称的集合
	 * 
	 * @param filepath
	 * @param suffix	若不传，则不需要筛选指定后缀文件
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String> readfileName(String filepath, String suffix)
			throws FileNotFoundException, IOException {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(filepath);
			if (!file.isDirectory()) {
				if (StringUtils.isNotEmpty(suffix)) {
					if (file.getName().endsWith("." + suffix)) {
						list.add(file.getName());
					}
				} else {
					list.add(file.getName());
				}
				// System.out.println("文件");
				// System.out.println("path=" + file.getPath());
				// System.out.println("absolutepath=" + file.getAbsolutePath());
				// System.out.println("name=" + file.getName());

			} else if (file.isDirectory()) {
				System.out.println(filepath + "，是文件夹");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {

					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {

						if (StringUtils.isNotEmpty(suffix)) {
							if (readfile.getName().endsWith("." + suffix)) {
								list.add(readfile.getName());
							}
						} else {
							list.add(readfile.getName());
						}

					} else if (readfile.isDirectory()) {
						
						readfile(filepath + "\\" + filelist[i], suffix);
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return list;
	}

	/**
	 * 获取图片名称
	 * 
	 * @param url
	 *            类似:http://139.196.189.100/upload/brand/img/613.jpg
	 * 
	 * @return	613.jpg
	 */
	public static String getPicName(String url) {

		return url.substring(url.lastIndexOf("/") + 1);
	}

	/**
	 * 获取文件名
	 *
	 * @param filePathName
	 *            完整文件名
	 * @return fileName
	 */
	public static String getFileName(String filePathName) {

		int index = 0;
		for (int i = 0; i < filePathName.length(); i++) {
			if (System.getProperty("file.separator").equals(
					filePathName.substring(i, i + 1))) {
				index = i;
			}
		}

		return filePathName.substring(index + 1, filePathName.length());
	}

	/**
	 * 扫描指定格式文件
	 *
	 * @param tmpDir
	 *            临时目录
	 * @return log files
	 */
	public static LinkedList<String> getFiles(String tmpDir, final String suffix) {
		LinkedList<String> fileList = new LinkedList<String>();
		FileFilter filefilter = new FileFilter() {

			public boolean accept(File file) {
				// if the file extension is .txt return true, else false
				return file.getName().endsWith("." + suffix);
			}
		};
		File root = new File(tmpDir);
		File[] files = root.listFiles(filefilter);
		for (File file : files) {
			if (file.isFile()) {

				fileList.addLast(file.getAbsolutePath());// 存储文件的绝对路径

			}
		}
		return fileList;
	}

	/**
	 * 拷贝文件
	 *
	 * @param source
	 *            源文件
	 * @param target
	 *            目标文件
	 */
	public static void copyFile(File source, File target) throws IOException {
		InputStream fis = null;
		OutputStream fos = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(source));
			fos = new BufferedOutputStream(new FileOutputStream(target));
			byte[] buf = new byte[4096];
			int i;
			while ((i = fis.read(buf)) != -1) {
				fos.write(buf, 0, i);
			}
		} catch (Exception e) {
			logger.error("copy File fail : {}.error : {}",
					source.getAbsoluteFile(), e);

		} finally {
			if (fis != null) {
				fis.close();
			}
			if (fos != null) {
				fos.close();
			}
		}
	}

	/**
	 * 记录写入文件
	 *
	 * @param info
	 *            记录
	 * @param fileName
	 *            文件名
	 */
	public static void write2File(List<String> info, String fileName) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(fileName));
			for (String line : info) {
				logger.info("error record line:{}", line);
				out.write(line);
				out.newLine();
			}

			out.close();
		} catch (Exception e) {
			logger.error("write error:" + e);
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制整个文件夹内容
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static boolean copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp;
			for (String aFile : file) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + aFile);
				} else {
					temp = new File(oldPath + File.separator + aFile);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()));
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + aFile, newPath + "/" + aFile);
				}
			}

			return true;
		} catch (Exception e) {
			System.out.println("复制整个文件夹内容操作出错");
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * 递归删除目录下的所有文件及子目录下所有文件
	 * 
	 * @param dir
	 *            将要删除的文件目录
	 * @return boolean Returns "true" if all deletions were successful. If a
	 *         deletion fails, the method stops attempting to delete and returns
	 *         "false".
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (String aChildren : children) {
				boolean success = deleteDir(new File(dir, aChildren));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	public static String renameFile(String fileName) {

		int index = fileName.lastIndexOf(".");
		String body = UUIDGenerator.genShortUuid();// fileName.substring(0,
													// index);
		String postfix;
		String timer;

		if (index != -1) {
			timer = new Date().getTime() + "";
			postfix = fileName.substring(index);
		} else {
			timer = new Date().getTime() + "";
			postfix = "";
		}
		return body + timer + postfix;
	}
}
