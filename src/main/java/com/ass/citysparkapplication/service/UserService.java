package com.ass.citysparkapplication.service;

import com.ass.citysparkapplication.constant.CommonConstants;
import com.ass.citysparkapplication.dto.UserProfileResponse;
import com.ass.citysparkapplication.dto.UserProfileUpdateRequest;
import com.ass.citysparkapplication.model.Image;
import com.ass.citysparkapplication.model.Person;
import com.ass.citysparkapplication.model.User;
import com.ass.citysparkapplication.repository.PersonRepository;
import com.ass.citysparkapplication.repository.PreferenceRepository;
import com.ass.citysparkapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PreferenceRepository preferenceRepository;

    @Autowired
    private PreferenceService preferenceService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void updateUserProfile(Integer userId, UserProfileUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Person person = personRepository.findByUserId(userId);
        if (person == null) {
            person = new Person();
            person.setUser(user);
        }

        if (request.getContactNo() != null)
            person.setContactNo(request.getContactNo());

        if (request.getDescription() != null)
            person.setDescription(request.getDescription());

        if (request.getBirthday() != null)
            person.setBirthday(request.getBirthday());

        if (request.getImageId() != null)
            person.setImage(new Image(request.getImageId(), null, null, null, null, CommonConstants.STATUS_ACTIVE));

        personRepository.save(person);

        // Update Preferences
        if (request.getPreferences() != null) {
            preferenceService.updatePreferences(person.getId(), request.getPreferences());
        }
    }

    public UserProfileResponse getUserProfile(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Person person = personRepository.findByUserId(userId);
        if (person == null) throw new RuntimeException("User profile not found");

        List<String> tagNames = preferenceRepository.findByPersonId(person.getId()).stream()
                .filter(pref -> CommonConstants.STATUS_ACTIVE.equals(pref.getStatus()))
                .map(pref -> pref.getTag().getName())
                .toList();

        return UserProfileResponse.builder()
                .email(user.getEmail())
                .contactNo(person.getContactNo())
                .description(person.getDescription())
                .birthday(person.getBirthday())
                .imageId(person.getImage() != null ? person.getImage().getId() : null)
                .preferences(tagNames)
                .build();
    }


}
