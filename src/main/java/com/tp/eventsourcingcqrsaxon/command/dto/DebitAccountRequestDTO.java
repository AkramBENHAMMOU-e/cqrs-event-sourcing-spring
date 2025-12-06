package com.tp.eventsourcingcqrsaxon.command.dto;

public record DebitAccountRequestDTO(String accountId, double amount, String currency) {
}