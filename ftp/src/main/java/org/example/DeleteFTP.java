package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
/**
* @author ahzoo
* @create 2021/10/30
* @desc 文件删除 （暂未实现删除路径内子文件夹功能）
*/
public class DeleteFTP {
    /**
     * @param pathName     要删除文件/目录所在ftp路径
     * @param fileName     要删除文件所在ftp的文件名
     * @param ftpClient    FTPClient对象
     */
    public void deleteFTP(String pathName, String fileName, FTPClient ftpClient) {

        try {
            ftpClient.changeWorkingDirectory(pathName);// 跳转到文件目录
            ftpClient.enterLocalPassiveMode(); //设置被动模式传输
            if (fileName != null && fileName != "") {
                //文件名不为空，删除指定文件
                ftpClient.deleteFile(pathName + File.separator + fileName);
                System.out.println("删除成功");
            } else {
                //文件名为空，删除路径下所有文件
                System.out.println("正在删除");
                //删除文件
                FTPFile[] files = ftpClient.listFiles();//获取目录下文件集合
                for (FTPFile file : files) {

                    if (file.isFile()) {
                        //判断为文件，直接删除
                        ftpClient.deleteFile(pathName + File.separator + file.getName());
                        System.out.println(file + "：已完成删除操作");
                    }
                    if (file.isDirectory()) {
                        /*有点问题，建议使用线程优化

                        //判断是文件夹，递归删除子文件夹内文件
                        deleteFTP(pathName + File.separator + file.getName(), null, ftpClient);
                        */
                    }
                }
                //删除文件夹
                ftpClient.removeDirectory(pathName);
                System.out.println("删除操作完成");
            }
        } catch (Exception e) {
            System.out.println("删除失败");
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
        }

    }
}
