package src.yugui.util;

import org.springframework.util.StringUtils;
import src.yugui.common.TimeTool;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @Description: 64base编码转图片辅助类
 * @Author: XiaoPanPan
 * @Date: 2019/9/26 15:26
 */
public class ConvertBase64ToImage {
    //base64字符串转化成图片保存到本地
    public static String GenerateImage(String imgStr) {   //对字节数组字符串进行Base64解码并生成图片
        System.out.println("imgStr--------->" + imgStr);
        if (imgStr == null) { //图像数据为空
            return null;
        }
        imgStr = imgStr.replaceFirst("data:image/png;base64,", "");//有前缀必须去掉，否则转换成图片会显示错误的图片
        System.out.println("imgStr--------->" + imgStr);
        BASE64Decoder decoder = new BASE64Decoder();
        //生成文件名
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            File root = new File("");
            String rootPath = root.getCanonicalPath();//本项目的根目录
            System.out.println("rootPath ------------------->" + rootPath);
            //新生成的图片
            String nowTime = TimeTool.getTime();
            String imgFilePath = rootPath + "\\imgs\\" + nowTime + ".jpg";
            System.out.println("imgFilePath ------------------->" + imgFilePath);
            OutputStream out = new FileOutputStream(imgFilePath);
            System.out.println("out ------------------->" + out);
            out.write(b);
            out.flush();
            out.close();
            return imgFilePath;
        } catch (Exception e) {
            return null;
        }
    }

    //将本地图片通过流取出返回给浏览器
    public static void getImg(String checkSignatureUrl, HttpServletResponse response) throws IOException {   //对字节数组字符串进行Base64解码并生成图片
        if (!StringUtils.isEmpty(checkSignatureUrl)) {
            File file = new File(checkSignatureUrl);
            FileInputStream fis;
            fis = new FileInputStream(file);

            long size = file.length();
            byte[] temp = new byte[(int) size];
            fis.read(temp, 0, (int) size);
            fis.close();
            byte[] data = temp;
            response.setContentType("image/png");
            OutputStream out = response.getOutputStream();
            out.write(data);
            out.flush();
            out.close();
        }
    }

}
