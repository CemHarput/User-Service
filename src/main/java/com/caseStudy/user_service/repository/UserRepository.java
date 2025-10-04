package com.caseStudy.user_service.repository;

import com.caseStudy.user_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {


    @Query("select u from User u where lower(u.email) = lower(:email)")
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);

    @Query("""
     select u from User u
     where (:q is null or u.normalizedName like concat('%', :q, '%'))
  """)
    Page<User> searchByNormalizedName(@Param("q") String normalizedLike, Pageable pageable);
}
