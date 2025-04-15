package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.model.Tag;
import com.ass.citysparkapplication.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findByStatus(CommonConstants.STATUS_ACTIVE); // Only active
    }

    public Optional<Tag> getTagById(Integer id) {
        return tagRepository.findById(id)
                .filter(tag -> CommonConstants.STATUS_ACTIVE.equals(tag.getStatus())); // Only active
    }

    public Tag saveTag(Tag tag) {
        if (tag.getStatus() == null) tag.setStatus(CommonConstants.STATUS_ACTIVE);
        return tagRepository.save(tag);
    }

    public Tag updateTag(Integer id, Tag updatedTag) {
        return tagRepository.findById(id).map(tag -> {
            tag.setName(updatedTag.getName());
            tag.setStatus(CommonConstants.STATUS_ACTIVE); // Ensure it's still active
            return tagRepository.save(tag);
        }).orElseThrow(() -> new RuntimeException("Tag not found"));
    }

    public void softDeleteTag(Integer id) {
        tagRepository.findById(id).ifPresent(tag -> {
            tag.setStatus(CommonConstants.STATUS_INACTIVE);
            tagRepository.save(tag);
        });
    }
}
