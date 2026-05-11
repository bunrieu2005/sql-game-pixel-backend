package org.bunrieu.sqlgamepixel.service;

import org.bunrieu.sqlgamepixel.entity.User;
import org.bunrieu.sqlgamepixel.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Validate username: only a-z, A-Z, 0-9, underscore, 3-50 chars.
     */
    public String validateUsername(String username) {
        if (username == null || username.isBlank()) {
            return "Username không được để trống";
        }
        if (username.length() < 3 || username.length() > 50) {
            return "Username phải từ 3 đến 50 ký tự";
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            return "Username chỉ được chứa chữ cái, số và dấu gạch dưới";
        }
        return null;
    }

    /**
     * Validate password: min 4 chars.
     */
    public String validatePassword(String password) {
        if (password == null || password.isBlank()) {
            return "Mật khẩu không được để trống";
        }
        if (password.length() < 4) {
            return "Mật khẩu phải có ít nhất 4 ký tự";
        }
        return null;
    }

    /**
     * Register a new user. Returns null on success, error message on failure.
     */
    @Transactional
    public String register(String username, String password) {
        username = username == null ? null : username.trim();
        password = password == null ? null : password.trim();
        String err = validateUsername(username);
        if (err != null) return err;

        err = validatePassword(password);
        if (err != null) return err;

        if (userRepository.existsByUsername(username)) {
            return "Username đã tồn tại";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return null;
    }

    /**
     * Login. Returns null on success, error message on failure.
     */
    public String login(String username, String password) {
        System.out.println("DEBUG LOGIN - username: [" + username + "] length: " + (username != null ? username.length() : "null"));
        System.out.println("DEBUG LOGIN - password: [" + password + "]");

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return "Username hoặc mật khẩu không được để trống";
        }

        var found = userRepository.findByUsername(username);
        System.out.println("DEBUG LOGIN - found.isPresent(): " + found.isPresent());

        if (found.isPresent()) {
            User user = found.get();
            System.out.println("DEBUG LOGIN - DB username: [" + user.getUsername() + "]");
            System.out.println("DEBUG LOGIN - DB password: [" + user.getPassword() + "]");
            boolean match = user.getPassword().equals(password);
            System.out.println("DEBUG LOGIN - password match: " + match);
            if (match) return null;
            else return "Sai mật khẩu";
        } else {
            System.out.println("DEBUG LOGIN - user NOT found in DB");
            return "Username không tồn tại";
        }
    }
}
