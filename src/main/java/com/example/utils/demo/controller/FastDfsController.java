package com.example.utils.demo.controller;

import com.example.utils.demo.util.fastdfs.FastDfsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;

@Api(tags = "fastDfs功能模块")
@Slf4j
@RestController
@RequestMapping("/fastDfs")
public class FastDfsController {
    @Autowired
    private FastDfsUtil fastDfsUtil;

    /**
     * 上传文件
     * @param file
     * @throws IOException
     */
    @ApiOperation(value="上传文件", notes="测试FastDFS文件上传", hidden = false)
    @RequestMapping(value = "/uploadFile",headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public void uploadFile(MultipartFile file) throws IOException {
        String s = fastDfsUtil.uploadFile(file);
        String resAccessUrl = fastDfsUtil.getResAccessUrl(s);
        log.info("resAccessUrl: {}",resAccessUrl);
    }

    /**
     * 下载文件
     * @param filePath
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="下载文件", notes="测试FastDFS文件下载", hidden = false)
    @GetMapping("/download")
    public void downloadFile(String filePath, HttpServletResponse response) throws IOException {
        byte[] bytes = fastDfsUtil.downloadFile(filePath);
        String fileName = "哈哈.jpg";
        //// 告诉浏览器用什么软件可以打开此文件
        //response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setContentType("application/force-download");// 设置强制下载不打开
        //方式一
        // fileName=new String(fileName.getBytes(), "ISO8859-1")
        //方式二
        fileName = URLEncoder.encode(fileName, "utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        IOUtils.write(bytes, response.getOutputStream());
    }

    /**
     * 流媒体的方式播放视频，只能从头看到尾，不能手动点击重新看已经看过的内容
     * @param filePath
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="播放接口", notes="流媒体的方式播放视频", hidden = false)
    @GetMapping("/play")
    public void streamMedia(String filePath, HttpServletResponse response) throws IOException {
        byte[] bytes = fastDfsUtil.downloadFile(filePath);
        IOUtils.copy(new ByteArrayInputStream(bytes), response.getOutputStream());
        response.flushBuffer();
    }

    @ApiOperation(value="删除文件", notes="测试FastDFS文件删除", hidden = false)
    @GetMapping("/delete")
    public void deleteFile(String filePath) {
        Boolean result = fastDfsUtil.deleteFile(filePath);
    }
}
