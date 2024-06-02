package com.example.calllogservice.mapper;

import org.example.common.dto.CallLogRequest;
import org.example.common.dto.CallLogResponse;
import org.example.common.model.CallLog;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface CallLogMapper {

    CallLog toCallLog(CallLogRequest callLogRequest);

    CallLogResponse toCallLogResponse(CallLog callLog);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateCallLogFromCallLogRequest(CallLogRequest callLogRequest,
                                         @MappingTarget CallLog callLog);
}
