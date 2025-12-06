package com.tp.eventsourcingcqrsaxon.command.dto;

public record AddNewAccountRequestDTO(double initialBalance, String currency) {
}
