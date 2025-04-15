package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Image;
import com.ass.citysparkapplication.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @GetMapping
    public List<Image> getAllImages() {
        return imageService.getAllImages();
    }

    @GetMapping("/{id}")
    public Optional<Image> getImageById(@PathVariable Integer id) {
        return imageService.getImageById(id);
    }

    @GetMapping("/event/{eventId}")
    public List<Image> getImagesByEventId(@PathVariable Integer eventId) {
        return imageService.getImagesByEventId(eventId);
    }

    @PostMapping
    public Image createImage(@RequestBody Image image) {
        return imageService.saveImage(image);
    }

    @PutMapping("/{id}")
    public Image updateImage(@PathVariable Integer id, @RequestBody Image updatedImage) {
        return imageService.updateImage(id, updatedImage);
    }

    @DeleteMapping("/{id}")
    public void softDeleteImage(@PathVariable Integer id) {
        imageService.softDeleteImage(id);
    }
}
