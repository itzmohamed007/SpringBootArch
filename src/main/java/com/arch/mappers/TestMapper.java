package com.arch.mappers;

import com.arch.models.dto.request.TestRequest;
import com.arch.models.dto.response.TestResponse;
import com.arch.models.entities.TestEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface TestMapper extends _Mapper<UUID, TestRequest, TestResponse, TestEntity> {

}