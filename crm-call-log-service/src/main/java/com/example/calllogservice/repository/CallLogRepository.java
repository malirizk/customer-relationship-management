package com.example.calllogservice.repository;

import org.example.common.model.CallLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CallLogRepository extends CrudRepository<CallLog, UUID> {
}
