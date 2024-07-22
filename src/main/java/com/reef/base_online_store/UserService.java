package com.reef.base_online_store;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;

    public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
    }

    public User saveUser(User user) {
        String hashedPassword = securityConfig.passwordEncoder().encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            // Проверяем, соответствуют ли введенные учетные данные учетным данным пользователя в базе данных
            return securityConfig.passwordEncoder().matches(password, user.getPassword());
        }

        return false;
    }
}
