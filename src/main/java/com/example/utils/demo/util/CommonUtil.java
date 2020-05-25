package com.example.utils.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * 用于存放一些公共方法
 */
@Slf4j
public class CommonUtil {

    /**
     * 根据当前日期，打散目录
     * yyyy/MM/dd
     * @return
     */
    public static String[] getDateFolder() {
        String[] retVal = new String[3];

        LocalDate localDate = LocalDate.now();
        retVal[0] = localDate.getYear() + "";

        int month = localDate.getMonthValue();
        retVal[1] = month < 10 ? "0" + month : month + "";

        int day = localDate.getDayOfMonth();
        retVal[2] = day < 10 ? "0" + day : day + "";

        return retVal;
    }

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法的文件名称：" + fileName);
    }

    public static String getJarRootPath(Long accId) throws FileNotFoundException {
        StringBuffer pathBuffer = new StringBuffer();
        String path = ResourceUtils.getURL("classpath:").getPath();
        if (path == null || path.trim().isEmpty()) {
            log.info("根目录不存在，赋值初始路径");
            path = "/";
        }
        log.info("ResourceUtils.getURL(\"classpath:\").getPath() -> "+path);
        pathBuffer.append(path);
        pathBuffer.append("updownload/");
        pathBuffer.append(accId);
        //创建File时会自动处理前缀和jar包路径问题  => /root/tmp
        File rootFile = new File(pathBuffer.toString());
        if (!rootFile.exists() && !rootFile.isDirectory()) {
            boolean created = rootFile.mkdirs();
            if (!created) {
                log.error("路径: '" + rootFile.getAbsolutePath() + "'创建失败");
                throw new RuntimeException("路径: '" + rootFile.getAbsolutePath() + "'创建失败");
            }
        }
        log.info("上传或下载文件所在路径: "+rootFile.getAbsolutePath());
        //需要给绝对路径
        return rootFile.getAbsolutePath()+"/";
    }

}
