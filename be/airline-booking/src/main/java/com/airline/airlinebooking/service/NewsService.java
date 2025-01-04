package com.airline.airlinebooking.service;


import com.airline.airlinebooking.dto.NewsDto;
import com.airline.airlinebooking.model.News;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NewsService {
    News addNews(NewsDto newsDto, MultipartFile imageFile);
    News updateNews(Long id, NewsDto newsDto, MultipartFile imageFile);
    void deleteNews(Long id);
    List<News> getAllNews();
    News getNewsById(Long id);

    News getNewsByIdProfile(Long id);
    void incrementViews(Long id);
}
