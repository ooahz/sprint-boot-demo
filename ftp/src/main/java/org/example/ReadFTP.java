package org.example;


import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
/**
 * @author ahzoo
 * @create 2021/10/30
 * @desc 文件读取（暂未实现读取路径内子文件中文件功能）
 */
public class ReadFTP {
    /**
     * @param pathName     要读取文件所在ftp路径
     * @param fileName     要读取文件所在ftp的文件名
     * @param ftpClient    FTPClient对象
     */
    public void readFTP(String pathName, String fileName, FTPClient ftpClient) {

        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            System.out.println(pathName);
            ftpClient.changeWorkingDirectory(pathName);// 跳转到文件目录
            ftpClient.enterLocalPassiveMode(); //设置被动模式传输

            if (fileName != null && fileName != "") {
                //文件名不为空，读取指定文件
                inputStream = ftpClient.retrieveFileStream(fileName);
                reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String fileL;
                StringBuffer buffer = new StringBuffer();
                while(((fileL=reader.readLine()) != null)){
                    buffer.append(fileL);
                }
                System.out.println(fileName + ":" + buffer.toString());

            } else {
                FTPFile[] files = ftpClient.listFiles();//获取目录下文件集合
                //文件名为空，读取路径下所有文件（不包含文件夹）
                System.out.println(files);
                for (FTPFile file : files) {
                    System.out.println(file.getName());
                    inputStream = ftpClient.retrieveFileStream(file.getName());
                    System.out.println(inputStream);
                    reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                    String fileL = null;
                    StringBuffer buffer = new StringBuffer();

                    while(((fileL=reader.readLine()) != null)){
                        buffer.append(fileL + "\n");
                    }
                    System.out.println(file + ":" + buffer.toString());
                    //retrieveFileStream使用了流，需要释放一下，不然会返回空指针
                    ftpClient.completePendingCommand();
                }
            }

        } catch (Exception e) {
            System.out.println("读取失败");
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
            if(null != inputStream){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
