package com.example.utils.demo.util.fastdfs;

import com.github.tobato.fastdfs.domain.conn.FdfsWebServer;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FastDfsUtil {
    @Autowired
    private FastFileStorageClient fastFileStorageClient;
    @Autowired
    private FdfsWebServer fdfsWebServer;

    /**
     * 上传文件-MultipartFile
     * @param file
     * @return
     * @throws IOException
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(), file.getSize(), FilenameUtils.getExtension(file.getOriginalFilename()), null);
        String fullPath = storePath.getFullPath();
        getResAccessUrl(fullPath);
        return fullPath;

    }

    /**
     * 上传文件-File
     * @param file
     * @return
     */
    public String uploadFile(File file) {
        try {
            FileInputStream inputStream = new FileInputStream(file);
            StorePath storePath = fastFileStorageClient.uploadFile(inputStream, file.length(), FilenameUtils.getExtension(file.getName()), null);
            return storePath.getFullPath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 下载文件
     * @param filePath
     * @return
     */
    public byte[] downloadFile(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        byte[] bytes = fastFileStorageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        return bytes;
    }

    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public Boolean deleteFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return false;
        }
        try {
            StorePath storePath = StorePath.parseFromUrl(filePath);
            fastFileStorageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 封装文件完整URL地址
    public String getResAccessUrl(String path) {
        String url = fdfsWebServer.getWebServerUrl() + path;
        System.out.println("上传文件地址为：\n" + url);
        return url;
    }
}
