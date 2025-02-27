package com.airline.airlinebooking.service.impl;

import com.airline.airlinebooking.dto.NewsDto;
import com.airline.airlinebooking.exception.ResourceNotFoundException;
import com.airline.airlinebooking.model.Admin;
import com.airline.airlinebooking.model.News;
import com.airline.airlinebooking.repository.AdminRepository;
import com.airline.airlinebooking.repository.NewsRepository;
import com.airline.airlinebooking.service.NewsService;
import com.airline.airlinebooking.utils.ImageUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ImageUpload imageUpload;

    @Override
    public News addNews(NewsDto newsDto, MultipartFile imageFile) {
        News news = new News();
        news.setTitle(newsDto.getTitle());
        news.setDescription(newsDto.getDescription());
        news.setContent(newsDto.getContent());
        LocalDateTime now = LocalDateTime.now();
        news.setCreatedAt(now);
        news.setUpdatedAt(now);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Lấy email từ token

        // Tìm Admin theo email
        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Admin not found with email: " + email));

        // Gán author cho news
        news.setAuthor(admin);
        news.setStatus(newsDto.getStatus());
        news.setCategory(newsDto.getCategory());
        news.setViews(0);

        if (imageFile != null && !imageFile.isEmpty()) {
            if (!imageUpload.checkExisted(imageFile)) {
                imageUpload.uploadImage(imageFile);
            }
            news.setImage(imageFile.getOriginalFilename());
        }


        return newsRepository.save(news);
    }

    @Override
    public News updateNews(Long id, NewsDto newsDto, MultipartFile imageFile) {
        News existingNews = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with ID: " + id));

        existingNews.setTitle(newsDto.getTitle());
        existingNews.setDescription(newsDto.getDescription());
        existingNews.setContent(newsDto.getContent());
        existingNews.setUpdatedAt(LocalDateTime.now());
        existingNews.setStatus(newsDto.getStatus());
        existingNews.setCategory(newsDto.getCategory());

        if (imageFile != null && !imageFile.isEmpty()) {
            if (!imageUpload.checkExisted(imageFile)) {
                imageUpload.uploadImage(imageFile);
            }
            existingNews.setImage(imageFile.getOriginalFilename());
        }
        return newsRepository.save(existingNews);
    }

    @Override
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new ResourceNotFoundException("News not found with ID: " + id);
        }
        newsRepository.deleteById(id);
    }

    @Override
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    @Override
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with ID: " + id));
    }

    @Override
    public News getNewsByIdProfile(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found with ID: " + id));
    }

    @Override
    public void incrementViews(Long id) {
        newsRepository.incrementViews(id);
    }

}
