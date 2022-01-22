package com.lxl.servicevido.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface VideoService {
    String addVideo(MultipartFile file);

    void romveVideo(String id);

    void removeVideoByCourseId(List<String> id);
}
