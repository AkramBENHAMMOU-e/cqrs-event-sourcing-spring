package com.tp.eventsourcingcqrsaxon.event;

import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

public record AccountActivatedEvent(String accountId, AccountStatus status) {
}
