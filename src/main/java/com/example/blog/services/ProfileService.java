package com.example.blog.services;

import com.example.blog.domain.entities.Profile;
import com.example.blog.domain.entities.User;
import com.example.blog.domain.repositories.ProfileRepository;
import com.example.blog.domain.repositories.UserRepository;
import com.example.blog.rest.dtos.ProfileDTO;
import com.example.blog.services.exceptions.ProfileNotFoundException;
import com.example.blog.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<Profile> getAllProfiles(){
        return profileRepository.findAll();
    }

    public Profile getProfileById(Long id) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if (profile.isEmpty()) throw new ProfileNotFoundException("Profile not found.");
        return profile.get();
    }

    public Profile createProfile(ProfileDTO profileDTO) throws UserNotFoundException {
        Profile profile = new Profile();

        User user = userService.getUserById((long) profileDTO.userId());

        profile.setUser(user);
        profile.setName(profileDTO.name());
        profile.setBiography(profileDTO.biography());

        return profileRepository.save(profile);
    }

    public void deleteProfile(Long id) throws ProfileNotFoundException {
        Optional<Profile> profileTemp = profileRepository.findById(id);
        if (profileTemp.isEmpty()) throw new ProfileNotFoundException("Profile with informed id not found");

        profileRepository.delete(profileTemp.get());
    }

}