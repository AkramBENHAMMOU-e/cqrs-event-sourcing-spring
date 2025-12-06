package com.tp.eventsourcingcqrsaxon.command.dto;

public record CreditAccountRequestDTO(String accountId, double amount, String currency) {
}
