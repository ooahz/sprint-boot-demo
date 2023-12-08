package org.example;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.net.MalformedURLException;
/**
* @author ahzoo
* @create 2021/10/30
* @desc 连接FTP服务链接
*/

public class InitFTP {

    //ftp服务器IP
    private static final String host = "127.0.0.1";
    //ftp服务器端口号默认为21
    private static final Integer port = 21;
    //ftp登录账号
    private static final String username = "ahzoo";
    //ftp登录密码
    private static final String password = "123456";

    public void initFtpClient(FTPClient ftpClient) {
        ftpClient.setControlEncoding("utf-8"); //设置编码
        try {
            System.out.println("正在连接FTP服务器:" + host + ":" + port);
            ftpClient.connect(host, port); //连接ftp服务器
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if(!FTPReply.isPositiveCompletion(replyCode)){
                System.out.println("FTP服务器连接失败:" + host + ":" + port);
            }
            System.out.println("FTP服务器连接成功:" + host + ":" + port);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
