package com.caseStudy.user_service.service;


import com.caseStudy.user_service.client.OrganizationClient;
import com.caseStudy.user_service.dto.*;
import com.caseStudy.user_service.enums.UserStatus;
import com.caseStudy.user_service.exception.UserAlreadyExistsException;
import com.caseStudy.user_service.exception.UserNotFoundException;
import com.caseStudy.user_service.model.User;
import com.caseStudy.user_service.repository.UserRepository;
import com.caseStudy.user_service.util.NameNormalizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Transactional
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final OrganizationClient organizationClient;


    public UserService(UserRepository userRepository, OrganizationClient organizationClient) {
        this.userRepository = userRepository;
        this.organizationClient = organizationClient;
    }

    public UserDTO create(UserCreateRequestDTO userCreateRequestDTO)  {
        log.info("Creating user with email: {}", userCreateRequestDTO.email());
        if(userRepository.existsByEmailIgnoreCase(userCreateRequestDTO.email()))
            throw new UserAlreadyExistsException(userCreateRequestDTO.email());

        User u = new User();
        u.setEmail(userCreateRequestDTO.email());
        u.setFullName(userCreateRequestDTO.fullName());
        u.setStatus(UserStatus.PENDING);
        userRepository.save(u);
        log.info("User created successfully with id: {}", u.getId());
        return UserDTO.convertFromUser(u);
    }
    @Transactional(readOnly = true)
    public PageResponse<UserSearchItemDTO> searchByNormalizedName(String fullname, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> p;
        if (fullname == null || fullname.isBlank()) {
            p = userRepository.findAll(pageable);
        } else {
            String nq = NameNormalizer.normalize(fullname).trim();
            p = userRepository.searchByNormalizedName(nq, pageable);
        }

        return PageResponse.of(p.map(UserSearchItemDTO::from).getContent(),
                p.getNumber(), p.getSize(), p.getTotalElements(), p.getTotalPages());
    }
    @Transactional(readOnly = true)
    public UserDTO getByEmail(String email)  {
        log.debug("Fetching user by email: {}", email);
        User u = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.convertFromUser(u);
    }
    public UserDTO update(String email, UserUpdateRequestDTO req)  {
        log.debug("Updating user with email: {}", email);
        User u = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));


        if (req.fullname() != null && !req.fullname().isBlank()) u.setFullName(req.fullname());
        u.setStatus(req.status());
        userRepository.save(u);
        log.info("User updated successfully: {}", email);
        return UserDTO.convertFromUser(u);
    }

    public void delete(String email){
        log.debug("Updating user with email: {}", email);
        User u = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        userRepository.delete(u);
        log.info("User deleted successfully: {}", email);

    }
    @Transactional(readOnly = true)
    public UserDTO getByID(UUID id)  {
        log.debug("Fetching user by id: {}", id);
        User u = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return UserDTO.convertFromUser(u);
    }

    public PageResponse<OrganizationDTO> getUserOrganizations(UUID userId, Pageable pageable) {

        var sortParams = pageable.getSort().stream()
                .map(o -> o.getProperty() + "," + o.getDirection().name().toLowerCase())
                .toList();

        return organizationClient.listUserOrganizations(
                userId,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortParams.isEmpty() ? null : sortParams
        );
    }



}
