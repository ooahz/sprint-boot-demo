package org.example;

import org.junit.Test;
import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.Scanner;

/**
* @author ahzoo
* @create 2021/10/30
* @desc ftp测试类
*/
public class AppTest {
    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        InitFTP initFTP = new InitFTP();
        System.out.println("退出请输入exit，输入其他字符继续操作");
        while (!"exit".equals(new Scanner(System.in).next())) {
            initFTP.initFtpClient(ftpClient);
            System.out.println("请输入操作（上传、下载、删除、移动、读取、写入）：");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            if ("上传".equals(next)) {
                UploadFTP uploadFTP = new UploadFTP();
                String fileName = "ahzoo.txt";
                String pathName = "\\monitor_system";
                String originFilename = "E:\\myTest\\abc.md";
                uploadFTP.uploadFile(pathName, fileName, originFilename, ftpClient);
            } else if ("下载".equals(next)) {
                DownloadFTP downloadFTP = new DownloadFTP();
//            String fileName = "ahzoo.md";
                String fileName = null;
                String pathName = "\\a";
                String downloadPath = "E:\\ahzoo";
                downloadFTP.downLoadFTP(pathName, fileName, downloadPath, ftpClient);
            } else if ("删除".equals(next)) {
                DeleteFTP deleteFTP = new DeleteFTP();
//            String fileName = "ahzoo.md";
                String fileName = null;
                String pathName = "\\test";
                deleteFTP.deleteFTP(pathName, fileName, ftpClient);
            } else if ("移动".equals(next)) {
                MoveFTP moveFTP = new MoveFTP();
                String fileName = "ahzoo.txt";
                String pathName = "\\ftpPath";
                String moveName = "move.txt";
                String movePath = "\\ouo";
                moveFTP.moveFTP(pathName, fileName, movePath, moveName, ftpClient);
            } else if ("读取".equals(next)) {
                ReadFTP readFTP = new ReadFTP();
//                String fileName = "123.txt";
                String fileName = null;
                String pathName = "\\a";
                readFTP.readFTP(pathName, fileName, ftpClient);
            } else if ("写入".equals(next)) {
                WriteFTP writeFTP = new WriteFTP();
                String fileName = "测试.txt";
                String pathName = "\\ftpPath";
                String contentText = "十玖八柒654321";
                writeFTP.writeFile(pathName, fileName, contentText, ftpClient);
            } else {
                System.out.println("输入有误");
                //关闭连接
                if (ftpClient.isConnected()) {
                    try {
                        ftpClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
            System.out.println("退出请输入exit，输入其他字符继续操作");
        }

    }
}
