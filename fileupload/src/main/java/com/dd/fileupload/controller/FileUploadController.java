package com.dd.fileupload.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Package: com.dd.fileupload.controller
 * @ClassName: FIleUploadController
 * @Author: 东方不败
 * @CreateTime: 2020-10-15 12:53
 * @Description:
 */
@Controller
@RequestMapping("/file")
public class FileUploadController {

    //文件的下载
    @RequestMapping("/download")
    public void fileDownload(HttpServletRequest request, HttpServletResponse response, String fileName) throws IOException {
        //根据相当路径获取绝对路径
        // ServletContext servletContext = request. getSession() . getServletContext() ;
        String realPath = request.getServletContext().getRealPath("/files/download"); //2.5规范中不能这么使用
        //获取文件名，根据文件名去指定的目录读取文件
        FileInputStream is = new FileInputStream(new File(realPath, fileName));
        //设置下载时响应头
        response.setHeader("content-disposition",
                "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        //使文件在网页中打开，右击鼠标选择下载
        //"inline;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
        //通过响应流响应即可
        ServletOutputStream outputStream = response.getOutputStream();
        //流的复制
        IOUtils.copy(is, outputStream);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(outputStream);
    }


    //文件上传
    @RequestMapping("/fileupload")
    public String fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {

        System.out.println("文件名：" + file.getOriginalFilename());
        System.out.println("文件类型：" + file.getContentType());
        System.out.println("文件的大小：" + file.getSize());
        //根据相对获取绝对路径
        String realPath = request.getSession().getServletContext().getRealPath("/files");

        //创建时间文件夹
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        File file2 = new File(realPath, format);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        //获取文件后缀
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        //动态获取文件类型
        String mimeType = request.getSession().getServletContext().getMimeType("." + extension);
        System.out.println("动态获取文件类型" + mimeType);

        //新的文件名前缀
        String newFileNamePrefix = UUID.randomUUID().toString().replace("-", "") +
                new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //新的文件名
        String newFileName = newFileNamePrefix + ". " + extension;

        //处理上传操作
        file.transferTo(new File(file2, newFileName));


        return "redirect:/index.jsp";
    }

}
