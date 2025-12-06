package com.tp.eventsourcingcqrsaxon.query.repository;

import com.tp.eventsourcingcqrsaxon.query.entity.AccountOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OperationRepository extends JpaRepository<AccountOperation,Long> {
    List<AccountOperation> findByAccountId(String id);
}