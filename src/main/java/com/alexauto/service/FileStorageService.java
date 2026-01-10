package com.alexauto.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    Map<String, String> storeFile(MultipartFile file);
}
