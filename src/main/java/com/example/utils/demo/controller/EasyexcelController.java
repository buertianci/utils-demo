package com.example.utils.demo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.utils.demo.entity.DemoData;
import com.example.utils.demo.util.easyexcel.DemoDataListener;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Api(tags = "Easyexcel功能模块")
@Slf4j
@RestController
@RequestMapping("/easyexcel")
public class EasyexcelController {

    /**
     * 上传文件
     * @param file
     * @throws IOException
     */
    @ApiOperation(value="上传文件", notes="测试文件easyexcel进行解析", hidden = false)
    @RequestMapping(value = "/upload_file",headers="content-type=multipart/form-data", method = RequestMethod.POST)
    public void uploadFile(MultipartFile file) throws IOException {
        ExcelReader excelReader = EasyExcel.read(file.getInputStream(), DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @throws IOException
     */
    @ApiOperation(value="下载文件", notes="测试文件easyexcel进行下载", hidden = false)
    @GetMapping("/download_file")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OutputStream out = null;
        try {
            //这里构造数据
            List<DemoData> demoDataList = this.data();
            String fileName = URLEncoder.encode("测试", "UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            out = response.getOutputStream();
            EasyExcel.write(out, DemoData.class).sheet("模版").doWrite(demoDataList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
