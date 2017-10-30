package test;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.junit.Test;

/**
 *
 * @author zhuangyuhao
 * @time   2016年10月27日 上午11:24:17
 *
 */
public class LocaDownloadImagelTest extends BaseSpringContext{

    @Test
    public static void main(String[] args) {

        String urlString = "http://139.196.189.100/home/app/upload/brand/img/613.jpg";
//		String filename = "613.jpg";
//		String savePath = "E:/test/test.LocaDownloadImagelTest";

        ArrayList<String> list = new ArrayList<>();
        list.add(urlString);
        System.out.println("开始");
        downloadPicture(list);
        System.out.println("结束");
    }

    private static  void downloadPicture(ArrayList<String> urlList) {
        URL url = null;
        int imageNumber = 0;

        for (String urlString : urlList) {
            try {
                url = new URL(urlString);
                System.out.println("url="+url);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                String imageName = imageNumber + ".jpg";
                FileOutputStream fileOutputStream = new FileOutputStream(new File(imageName));

                byte[] buffer = new byte[1024];
                int length;

                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }

                dataInputStream.close();
                fileOutputStream.close();
                imageNumber++;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
