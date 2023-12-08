package org.example;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author ahzoo
 * @create 2021/10/30
 * @desc 文件上传
 */
public class UploadFTP {
    /**
     * @param pathName 文件上传到ftp服务器的路径
     * @param fileName 文件上传到ftp服务器的名称
     * @param originPath 要上传文件所在的路径（绝对路径）
     **/
    public void uploadFile(String pathName, String fileName, String originPath, FTPClient ftpClient){
        InputStream inputStream = null;
        try{
            System.out.println("文件传输中");
            inputStream = new FileInputStream(new File(originPath));//将文本数据转换为输入流

            ftpClient.enterLocalPassiveMode(); //设置被动模式传输
            ftpClient.setFileType(ftpClient.BINARY_FILE_TYPE);//以二进制文件形式输入
            ftpClient.makeDirectory(pathName);//在ftp服务器创建目标路径
            ftpClient.changeWorkingDirectory(pathName);//切换到目标路径
            ftpClient.enterLocalPassiveMode();//开启端口
            ftpClient.storeFile(fileName, inputStream);//开始上传，inputStream表示数据源。
            //ftpClient.storeFile(new String(fileName.getBytes("UTF-8"),"ISO-8859-1") inputStream);
            System.out.println("文件上传操作完成");
        }catch (Exception e) {
            System.out.println("文件上传失败");
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
