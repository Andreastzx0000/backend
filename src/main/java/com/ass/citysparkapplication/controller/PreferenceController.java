package com.ass.citysparkapplication.controller;

import com.ass.citysparkapplication.model.Preference;
import com.ass.citysparkapplication.service.PreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceController {

    @Autowired
    private PreferenceService preferenceService;

    @GetMapping("/{personId}")
    public List<Preference> getPreferencesByPerson(@PathVariable Integer personId) {
        return preferenceService.getPreferencesByPersonId(personId);
    }

    @PutMapping("/{personId}")
    public void updatePreferences(@PathVariable Integer personId, @RequestBody List<Integer> tagIds) {
        preferenceService.updatePreferences(personId, tagIds);
    }
}
