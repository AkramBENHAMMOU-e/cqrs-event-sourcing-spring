package com.tp.eventsourcingcqrsaxon.event;

public record AccountCreditedEvent(String accountId, double amount, String currency) {
}