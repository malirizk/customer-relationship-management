package org.example.callreportservice.repository;

import jakarta.persistence.QueryHint;
import org.example.common.model.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static org.hibernate.jpa.HibernateHints.HINT_READ_ONLY;

@Repository
public interface CallReportRepository extends JpaRepository<CallLog, UUID> {

    @QueryHints(value = {
            @QueryHint(name = HINT_READ_ONLY, value = "true")
    })
    @Query(value = """
            SELECT COUNT(*) AS total_calls, 
                  SUM(CASE WHEN result = 'ANSWERED_CALL' THEN 1 ELSE 0 END) AS successful_calls, 
                  SUM(CASE WHEN result = 'NOT_ANSWERED_CALL' THEN 1 ELSE 0 END) AS incomplete_calls, 
                  SUM(CASE WHEN result = 'UNMATCHED_CALL' THEN 1 ELSE 0 END) AS unmatched_calls, 
                  SUM(duration) AS total_duration, 
                  MAX(call_end) AS latest_sync_date
              FROM call_log 
              WHERE customer_id = :customerId""", nativeQuery = true)
    List<Object[]> getCustomerCallsReport(@Param("customerId") UUID customerId);
}
