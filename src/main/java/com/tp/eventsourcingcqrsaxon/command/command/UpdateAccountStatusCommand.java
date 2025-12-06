package com.tp.eventsourcingcqrsaxon.command.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;

@Getter @Setter @AllArgsConstructor
public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
