package org.bunrieu.sqlgamepixel.controller;

import org.bunrieu.sqlgamepixel.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public static class RegisterRequest {
        private String username;
        private String password;

        public RegisterRequest() {} // Required for JSON deserialization

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest() {} // Required for JSON deserialization

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private boolean success;
        private String message;
        private String username;
        public AuthResponse(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        public AuthResponse(boolean success, String message, String username) {
            this.success = success;
            this.message = message;
            this.username = username;
        }
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
        public String getUsername() { return username; }
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest req) {
        String error = userService.register(req.getUsername(), req.getPassword());
        if (error != null) {
            // Trả về 200 với success=false để frontend không coi là lỗi kết nối
            return ResponseEntity.ok(new AuthResponse(false, error));
        }
        return ResponseEntity.ok(new AuthResponse(true, "Đăng ký thành công!", req.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        System.out.println("CONTROLLER LOGIN - username: [" + req.getUsername() + "] password: [" + req.getPassword() + "]");
        String error = userService.login(req.getUsername(), req.getPassword());
        System.out.println("CONTROLLER LOGIN - error: [" + error + "]");
        if (error != null) {
            return ResponseEntity.ok(new AuthResponse(false, error));
        }
        return ResponseEntity.ok(new AuthResponse(true, "Đăng nhập thành công!", req.getUsername()));
    }
}
