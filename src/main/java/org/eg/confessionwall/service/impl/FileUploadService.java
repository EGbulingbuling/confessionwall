package org.eg.confessionwall.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.eg.confessionwall.utils.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Slf4j
public class FileUploadService {
    /**
     * @desc 上传文件到ecs
     */
    public String uploadOnEcsCache(MultipartFile uploadFile) throws IOException {
        return UploadUtil.uploadSimpleFile(uploadFile,"img/cache/");
    }

    public String uploadOnEcs(MultipartFile mf) throws IOException {
        return UploadUtil.uploadSimpleFile(mf,"img/");
    }
}

