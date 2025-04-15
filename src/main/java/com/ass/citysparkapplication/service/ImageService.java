package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.model.Image;
import com.ass.citysparkapplication.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll().stream()
                .filter(img -> CommonConstants.STATUS_ACTIVE.equals(img.getStatus()))
                .toList();
    }

    public Optional<Image> getImageById(Integer id) {
        return imageRepository.findById(id)
                .filter(img -> CommonConstants.STATUS_ACTIVE.equals(img.getStatus()));
    }

    public List<Image> getImagesByEventId(Integer eventId) {
        return imageRepository.findByEventId(eventId).stream()
                .filter(img -> CommonConstants.STATUS_ACTIVE.equals(img.getStatus()))
                .toList();
    }

    public Image saveImage(Image image) {
        if (image.getStatus() == null) {
            image.setStatus(CommonConstants.STATUS_ACTIVE);
        }
        return imageRepository.save(image);
    }

    public Image updateImage(Integer id, Image updatedImage) {
        return imageRepository.findById(id).map(img -> {
            img.setImagePath(updatedImage.getImagePath());
            img.setEvent(updatedImage.getEvent());
            img.setStatus(CommonConstants.STATUS_ACTIVE);
            return imageRepository.save(img);
        }).orElseThrow(() -> new RuntimeException("Image not found"));
    }

    public void softDeleteImage(Integer id) {
        imageRepository.findById(id).ifPresent(img -> {
            img.setStatus(CommonConstants.STATUS_INACTIVE);
            imageRepository.save(img);
        });
    }
}
