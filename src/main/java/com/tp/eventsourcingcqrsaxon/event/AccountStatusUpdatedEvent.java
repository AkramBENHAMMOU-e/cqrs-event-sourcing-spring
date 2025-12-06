package com.tp.eventsourcingcqrsaxon.event;

import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

public record AccountStatusUpdatedEvent(String accountId, AccountStatus status) {
}
