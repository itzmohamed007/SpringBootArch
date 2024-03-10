package com.arch.controllers;

import com.arch.models.dto.request.TestRequest;
import com.arch.models.dto.response.TestResponse;
import com.arch.services.impl.TestService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/test")
@AllArgsConstructor
public class TestController extends _Controller<UUID, TestRequest, TestResponse, TestService> {
    private final TestService service;

//    private final ResponseEntity<?> responseBuilder(Object body, String message) {
//
//        return null;
//    }
}
