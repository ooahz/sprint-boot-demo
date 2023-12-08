package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
/**
* @author ahzoo
* @create 2021/10/30
* @desc 文件下载（暂未实现下载路径内子文件中文件功能）
*/
public class DownloadFTP {
    /**
     * @param pathName     要下载文件所在ftp路径
     * @param fileName     要下载文件所在ftp的文件名
     * @param downloadPath 文件下载后保存的路径
     * @param ftpClient    FTPClient对象
     */
    public void downLoadFTP(String pathName, String fileName, String downloadPath, FTPClient ftpClient) {

        OutputStream outputStream = null;
        try {
            ftpClient.changeWorkingDirectory(pathName);// 跳转到文件目录
            ftpClient.enterLocalPassiveMode(); //设置被动模式传输

            if (fileName != null && fileName != "") {
                //文件名不为空，下载指定文件
                File filePath = new File(downloadPath);
                if (!filePath.exists()) {
                    filePath.mkdir();//目录不存在，创建目录
                }
                outputStream = new FileOutputStream(new File(downloadPath + File.separator + fileName));
                ftpClient.retrieveFile(fileName, outputStream);
                System.out.println("下载操作完成");
            } else {
                FTPFile[] files = ftpClient.listFiles();//获取目录下文件集合
                //文件名为空，下载路径下所有文件（不包含文件夹）
                for (FTPFile file : files) {
                    File filePath = new File(downloadPath);
                    if (!filePath.exists()) {
                        filePath.mkdir();//目录不存在，创建目录
                    }
                    File downloadFile = new File(downloadPath + File.separator + file.getName());
                    outputStream = new FileOutputStream(downloadFile);
                    ftpClient.retrieveFile(file.getName(), outputStream);

                    System.out.println("下载操作完成：" + downloadFile);
                }
            }

        } catch (Exception e) {
            System.out.println("下载失败");
            e.printStackTrace();
        } finally {
            //关闭连接
            if(ftpClient.isConnected()){
                try{
                    ftpClient.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
            if(null != outputStream){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
