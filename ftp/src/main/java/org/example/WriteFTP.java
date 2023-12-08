package org.example;

import org.apache.commons.net.ftp.FTPClient;

import java.io.*;
/**
* @author ahzoo
* @create 2021/10/30
* @desc 文本写入
*/
public class WriteFTP {
    /**
     * @param pathName 文本写入到ftp服务器的路径
     * @param fileName 文本写入到ftp服务器的名称
     * @param contentText 要写入的文本数据
     **/
    public void writeFile(String pathName, String fileName, String contentText, FTPClient ftpClient){
        InputStream inputStream = null;
        try{
            System.out.println("开始写入操作");
            inputStream = new ByteArrayInputStream(contentText.getBytes());//将文本数据转换为输入流，通过getBytes()方法避免中文乱码

            ftpClient.enterLocalPassiveMode(); //设置被动模式传输
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);//以二进制文件形式输入
            ftpClient.makeDirectory(pathName);//在ftp服务器创建目标路径
            ftpClient.changeWorkingDirectory(pathName);//切换到目标路径
            ftpClient.enterLocalPassiveMode();//开启端口
            ftpClient.storeFile(fileName, inputStream);//开始写入，inputStream表示数据源。

            System.out.println("文本写入操作完成");
        }catch (Exception e) {
            System.out.println("文本写入失败");
            e.printStackTrace();
        }finally{
            //关闭连接
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
