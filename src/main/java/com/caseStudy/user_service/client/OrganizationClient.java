package com.caseStudy.user_service.client;


import com.caseStudy.user_service.dto.OrganizationDTO;
import com.caseStudy.user_service.dto.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@FeignClient(
        name = "organization-service",
        url  = "${clients.organization-service.base-url}",
        path = "/api/organizations"
)
public interface OrganizationClient {

    @GetMapping("/members/{userId}")
    PageResponse<OrganizationDTO> listUserOrganizations(
            @PathVariable("userId") UUID userId,
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "sort", required = false) List<String> sort
    );
}
