package com.tp.eventsourcingcqrsaxon.query.repository;

import com.tp.eventsourcingcqrsaxon.query.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}