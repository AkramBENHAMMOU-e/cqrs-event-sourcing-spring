package com.tp.eventsourcingcqrsaxon.query.dto;

import com.tp.eventsourcingcqrsaxon.query.entity.Account;
import com.tp.eventsourcingcqrsaxon.query.entity.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<AccountOperation> operations) {
}
