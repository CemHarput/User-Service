package com.caseStudy.user_service.controller;


import com.caseStudy.user_service.client.OrganizationClient;
import com.caseStudy.user_service.dto.*;
import com.caseStudy.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@Tag(name = "user-controller", description = "User CRUD, search, and cross-service organization listing")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    @Operation(
            summary = "Create a new user",
            description = "Creates a new user record. Email must be unique and fields must satisfy validation rules."
    )
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateRequestDTO request) {
        UserDTO created = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/search")
    @Operation(
            summary = "Search users",
            description = "Searches users by normalized name (case- and space-insensitive). Returns a paged list."
    )
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

    @GetMapping("/email/{email}")
    @Operation(
            summary = "Get user by email",
            description = "Returns a single user by email."
    )
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email)  {
        UserDTO user = userService.getByEmail(email);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{email}")
    @Operation(
            summary = "Update user by email",
            description = "Updates mutable fields of the user identified by email."
    )
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable String email,
            @Valid @RequestBody UserUpdateRequestDTO request
    )  {
        UserDTO updated = userService.update(email, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{email}")
    @Operation(
            summary = "Delete user by email",
            description = "Deletes the user identified by email."
    )
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        userService.delete(email);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by ID",
            description = "Returns a single user by UUID."
    )
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id)  {
        UserDTO user = userService.getByID(id);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{userId}/organizations")
    @Operation(
            summary = "List organizations that the user belongs to",
            description = "Returns a paged list of organizations for the given userId. Pagination uses standard Spring parameters: page, size, and sort."
    )
    public ResponseEntity<PageResponse<OrganizationDTO>> getUserOrganizations(
            @PathVariable UUID userId,
            @ParameterObject Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getUserOrganizations(userId, pageable));
    }




}
