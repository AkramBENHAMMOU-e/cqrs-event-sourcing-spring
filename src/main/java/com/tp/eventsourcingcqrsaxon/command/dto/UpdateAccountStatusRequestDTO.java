package com.tp.eventsourcingcqrsaxon.command.dto;

import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {
}