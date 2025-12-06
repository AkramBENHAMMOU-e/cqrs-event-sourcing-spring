package com.tp.eventsourcingcqrsaxon.event;

import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

public record AccountCreatedEvent(String accountId, double initialBalance, String currency, AccountStatus accountStatus) {
}
