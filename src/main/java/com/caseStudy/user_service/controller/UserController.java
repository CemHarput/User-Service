package com.caseStudy.user_service.controller;


import com.caseStudy.user_service.dto.*;
import com.caseStudy.user_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequestDTO request) {
        UserDTO created = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<UserSearchItemDTO>> searchUsers(
            @RequestParam(required = false)
            String q,

            @RequestParam(defaultValue = "0")
            @PositiveOrZero
            int page,

            @RequestParam(defaultValue = "10")
            @Min(1) @Max(100)
            int size
    ) {
        PageResponse<UserSearchItemDTO> response = userService.searchByNormalizedName(q, page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)  {
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{email}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdateRequestDTO request
    )  {
        UserDTO updated = userService.update(email, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.delete(email);
        return ResponseEntity.noContent().build();
    }


}
