package com.movision.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片工具类
 * 
 * @author zhuangyuhao
 * @time 2016年10月27日 下午4:32:15
 *
 */
public class ImgUtil {

    private static final Logger log = LoggerFactory.getLogger(ImgUtil.class);

    public static void main(String[] args) {

        /*String path1 = "F:/Download_pic/img/bluesky.jpg";
        String path2 = "F:/Download_pic/img/rabbit.jpg";
        ArrayList<String> list = new ArrayList<>();
        list.add(path1);
        list.add(path2);

        String saveDirectory = "F:/Download_pic/img_100_100";
        System.out.println("开始");
        compressImgList(list, saveDirectory, 100, 100);
        System.out.println("结束");*/
        
    	try {
			downloadImg("F:/Download_pic", "//139.196.189.100/MEAJ72b51466766446436.jpg", "no_exist_file.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }

    /**
     * 压缩图片到指定路径文件夹 若该路径中图片已经存在，则会覆盖
     * 
     * @param pathList
     *            String集合
     * @param saveDirectory
     * @param w
     * @param h
     */
    public static void compressImgList(ArrayList<String> pathList, String saveDirectory, int w, int h) {
        // 判断压缩后图片存储路径是否存在，不存在则新建
        validationDir(saveDirectory);

        List<String> existFileList = new ArrayList<String>();
        try {
            existFileList = FileUtils.readfileName(saveDirectory, "jpg");
            for (String string : existFileList) {
                System.out.println(string);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 遍历图片集合
        for (String imgPath : pathList) {
            // 获取图片文件名
            String filename = FileUtils.getPicName(imgPath);
            log.info("filename=" + filename);

            // 压缩后的图片的url
            String compress_file_path = saveDirectory + System.getProperty("file.separator") + filename;
            log.info("压缩后的图片的url=" + compress_file_path);

            // 判断该文件夹下是否有同名的图片，若有则不处理，若没有则进行处理
            if (CollectionUtils.isEmpty(existFileList) || !existFileList.contains(filename)) {
                log.info("压缩了这张图片，filename=" + filename);
                ImgCompressUtil.ImgCompress(imgPath, compress_file_path, w, h);
                existFileList.add(filename);
            } else {
                log.info("该图片已存在，不需要压缩，filename=" + filename);
            }
        }

    }

    /**
     * 下载图片并且压缩到指定路径
     * 
     * @param urlList
     *            图片集合
     * @param saveDirectory
     *            保存的路径，例如：F:/Download_pic
     */
    public static void downloadPicture(ArrayList<String> urlList, String saveDirectory, int w, int h) throws Exception {
        URL url = null;
        // 遍历图片集合
        for (String urlString : urlList) {
            try {
                // 获取图片文件名
                String filename = FileUtils.getPicName(urlString);
                log.info("filename=" + filename);
                // 1 下载图片
                // 需要先创建临时文件夹
                String filePath = downloadImg(saveDirectory, urlString, filename);

                // 2 压缩图片
                String compress_dir_path = saveDirectory + System.getProperty("file.separator") + "afterCompress";
                log.info("compress_dir_path=" + compress_dir_path);
                validationDir(compress_dir_path);
                // 压缩后的图片的url
                String compress_file_path = compress_dir_path + System.getProperty("file.separator") + filename;
                log.info("压缩后的图片的url=" + compress_file_path);
                ImgCompressUtil.ImgCompress(filePath, compress_file_path, w, h);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 删除临时文件夹和图片
    }

    /**
     * 下载图片
     * 
     * @param saveDirectory
     *            下载后保存的文件夹路径，临时文件夹
     * @param urlString
     *            下载的url
     * @param filename
     *            图片名称
     * @return 下载后保存的图片路径
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static String downloadImg(String saveDirectory, String urlString, String filename) throws Exception {
        URL url;
        // 1 下载图片
        if (!StringUtils.startsWith(urlString, "http")) {
            urlString = "http:" + urlString;
        }

        url = new URL(urlString);
//        log.info("url=" + url);
        InputStream is = url.openStream(); //若url链接不存在，即图片不存在，会抛出异常 FileNotFoundException
        
        DataInputStream dataInputStream = new DataInputStream(is);

        validationDir(saveDirectory);
        String filePath = saveDirectory + "/" + filename;
        File downloadFile = new File(filePath);
        FileOutputStream fileOutputStream = new FileOutputStream(downloadFile);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = dataInputStream.read(buffer)) > -1) {
            fileOutputStream.write(buffer, 0, length);
        }

        dataInputStream.close();
        fileOutputStream.close();
        return filePath;
    }

    /**
     * 校验是否存在指定路径的文件夹， 若不存在，则新建该文件夹
     * 
     * @param filepath
     */
    public static void validationDir(String filepath) {
        File dir = new File(filepath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
            log.info("mk dir susscess dirName = " + filepath);
        }
    }

}
