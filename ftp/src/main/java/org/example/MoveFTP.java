package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
/**
* @author ahzoo
* @create 2021/10/30
* @desc 文件移动/重命名
*/
public class MoveFTP {
    /**
     * @param pathName     要移动文件所在ftp路径
     * @param fileName     要移动文件所在ftp的文件名
     * @param movePath     文件移动后的路径
     * @param moveName     文件移动后的文件名（与源文件一致时实现只移动不重命名，不一致则实现了移动+重命名）
     * @param ftpClient    FTPClient对象
     */
    public void moveFTP(String pathName, String fileName, String movePath, String moveName, FTPClient ftpClient) {
        try {
            ftpClient.enterLocalPassiveMode(); //设置被动模式传输
            if (!ftpClient.changeWorkingDirectory(movePath)) {
                //跳转到目标路径失败时创建目标目录
                ftpClient.makeDirectory(movePath);
            }
            if (moveName != null && moveName != "") {
                ftpClient.changeWorkingDirectory(pathName);// 跳转回需要进行操作的目录
                //移动后的文件名不为空，移动目标文件
                //ftpClient.rename(旧文件名, 新路径)
                ftpClient.rename(fileName, movePath + File.separator + moveName);
                System.out.println("文件移动操作已完成：" + movePath + File.separator + moveName);
            } else {
                //移动后的文件名为空，移动目标路径所有文件

                ftpClient.changeWorkingDirectory(pathName);// 跳转回需要进行操作的目录
                FTPFile[] files = ftpClient.listFiles();//获取目录下文件集合

                for (FTPFile file : files) {
                    ftpClient.rename(file.getName(), movePath + File.separator + file.getName());
                    System.out.println("移动操作完成：" + file.getName());
                }
            }

        } catch (Exception e) {
            System.out.println("移动失败");
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


