package com.dh.clinica.service;

import com.dh.clinica.persistence.model.AppUser;
import com.dh.clinica.persistence.model.AppUserRole;
import com.dh.clinica.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void run(ApplicationArguments args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode("password");
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        String hashedPassword2 = passwordEncoder2.encode("password2");
        userRepository.save(new AppUser("Zoe", "zoe", "zoe@gmail.com", hashedPassword, AppUserRole.ADMIN));
        userRepository.save(new AppUser("Profe", "peter", "profe@gmail.com", hashedPassword2, AppUserRole.USER));
    }
}
