package com.download.demo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;

/**
* @author 十玖八柒
* @date 2022/7
* @GitHub https://github.com/ooahz
* @desc 文件上传
*/
@RestController
public class UploadController {


    /**
     * 接收批量上传的文件
     *
     * @param files
     * @param request
     */
    @RequestMapping("uploadList")
    public void toUploadAll(MultipartFile[] files, HttpServletRequest request) {
//        可以从request中获取接收的文件
//        List<MultipartFile> files = ((StandardMultipartHttpServletRequest) request).getFiles("files");

        try {
            int i = 1;
            for (MultipartFile file : files) {
                String filePath = "E:\\upload";
                String fileName = i + "上传图片.png";
                File folder = new File(filePath);
                if (!folder.exists()) {
                    folder.mkdirs();
                }
                file.transferTo(new File(folder, fileName));
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 接收单个上传的文件
     *
     * @param file
     * @param request
     */
    @RequestMapping("upload")
    public void toUpload(MultipartFile file, HttpServletRequest request) {
//        可以从request中获取接收的文件
//        MultipartFile file = ((StandardMultipartHttpServletRequest) request).getFile("file");

        String filePath = "E:\\upload";
        String fileName = "上传图片.png";

        try {
            File folder = new File(filePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            file.transferTo(new File(folder, fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用hutool发送上传文件
     */
    @RequestMapping("huUpload")
    public void huUpload() {
        HashMap<String, Object> paramMap = new HashMap<>();
//        文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
        paramMap.put("file", FileUtil.file("E:\\ahzoo\\待上传图片.png"));
        String result = HttpUtil.post("http://127.0.0.1:8080/upload", paramMap);
    }

}
