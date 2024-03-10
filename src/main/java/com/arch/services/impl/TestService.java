package com.arch.services.impl;

import com.arch.mappers.TestMapper;
import com.arch.models.dto.request.TestRequest;
import com.arch.models.dto.response.TestResponse;
import com.arch.models.entities.TestEntity;
import com.arch.repositories.TestRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TestService extends _AbstractService<UUID, TestRequest, TestResponse, TestEntity, TestRepository, TestMapper>{

}
