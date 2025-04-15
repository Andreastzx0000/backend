package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.model.Person;
import com.ass.citysparkapplication.model.Preference;
import com.ass.citysparkapplication.model.Tag;
import com.ass.citysparkapplication.repository.PreferenceRepository;
import com.ass.citysparkapplication.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PreferenceService {

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private TagRepository tagRepository;

    public List<Preference> getPreferencesByPersonId(Integer personId) {
        return preferenceRepository.findByPersonId(personId).stream()
                .filter(pref -> CommonConstants.STATUS_ACTIVE.equals(pref.getStatus()))
                .toList();
    }

    public void updatePreferences(Integer personId, List<Integer> tagIds) {
        // Inactivate all current preferences for the person
        List<Preference> existingPrefs = preferenceRepository.findByPersonId(personId);
        existingPrefs.forEach(pref -> {
            pref.setStatus(CommonConstants.STATUS_INACTIVE);
            preferenceRepository.save(pref);
        });

        // Add new preferences with status A
        List<Preference> newPrefs = new ArrayList<>();
        for (Integer tagId : tagIds) {
            Tag tag = tagRepository.findById(tagId).orElse(null);
            if (tag != null && CommonConstants.STATUS_ACTIVE.equals(tag.getStatus())) {
                Preference pref = new Preference();
                pref.setTag(tag);
                pref.setPerson(new Person(personId, null, null, null, null, null, null, null, null));
                pref.setStatus(CommonConstants.STATUS_ACTIVE);
                newPrefs.add(pref);
            }
        }

        preferenceRepository.saveAll(newPrefs);
    }
}
