package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Tag;
import com.ass.citysparkapplication.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Optional<Tag> getTagById(@PathVariable Integer id) {
        return tagService.getTagById(id);
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.saveTag(tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable Integer id, @RequestBody Tag tag) {
        return tagService.updateTag(id, tag);
    }

    @DeleteMapping("/{id}")
    public void softDeleteTag(@PathVariable Integer id) {
        tagService.softDeleteTag(id);
    }
}
