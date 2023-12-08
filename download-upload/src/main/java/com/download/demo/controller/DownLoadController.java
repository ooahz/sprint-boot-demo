package com.download.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
/**
* @author 十玖八柒
* @date 2022/7
* @GitHub https://github.com/ooahz
* @desc 文件下载
*/
@RestController
public class DownLoadController {

    /**
     * 下载文件到浏览器
     * @param response
     */
    @RequestMapping("download")
    public void toDownload(HttpServletResponse response){
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
//            下载地址
            String downloadUrl = "https://ahzoo.cn/img/avatar.jpg";
//            获取文件后缀名（如果可以获取到的话，不能获取到的话就直接在下面写死吧）
            String suffix = downloadUrl.substring(downloadUrl.lastIndexOf(".") + 1).toLowerCase();
//            文件名
            String fileName = "头像." + suffix;
            String finalFileName = null;
            finalFileName = URLEncoder.encode(fileName, "UTF8");
            URL url = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            /**
             * 对下载请求进行一些设置
             */
//            设置超时时间
            connection.setConnectTimeout(30000);
//            设置UA，防止403错误
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36");
            connection.setDoOutput(false);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.setInstanceFollowRedirects(true);
//            获取下载文件输入流
            inputStream = connection.getInputStream();
            /**
             * 输出文件到浏览器
             */
            int length = 0;
//            设置下载的响应头
           response.setContentType("application/x-download");
            response.setHeader("Content-Disposition", "attachment;filename=" + finalFileName);
            response.setHeader("Cache-Control", "no-cache");
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }

    /**
     * hutool下载文件到本地
     */
    @RequestMapping("hudownload")
    public void huDownload(){
        String downloadUrl = "https://ahzoo.cn/img/avatar.jpg";

//      将文件下载后保存在E盘，返回结果为下载文件大小
       long size = HttpUtil.downloadFile(downloadUrl, FileUtil.file("E:/"));
       System.out.println("文件大小: " + size);
    }
}
