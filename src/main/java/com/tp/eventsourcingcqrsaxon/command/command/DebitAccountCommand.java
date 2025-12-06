package com.tp.eventsourcingcqrsaxon.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter @Setter @AllArgsConstructor
public class DebitAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String currency;
}
