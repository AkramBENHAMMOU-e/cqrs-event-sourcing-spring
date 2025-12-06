package com.tp.eventsourcingcqrsaxon.command.aggregate;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import com.tp.eventsourcingcqrsaxon.command.command.AddAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.CreditAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.DebitAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.UpdateAccountStatusCommand;
import com.tp.eventsourcingcqrsaxon.enums.AccountStatus;
import com.tp.eventsourcingcqrsaxon.event.*;

@Slf4j
@Getter
@Setter
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId ;
    private double currentBalance;
    private String currency;
    private AccountStatus status;

    // Axon requires this
    public AccountAggregate() { }

    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("CreateAccount Command Received");
        if (command.getInitialBalance()<0) throw  new IllegalArgumentException("Balance negative exception");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                command.getCurrency(),
                AccountStatus.CREATED
        ));
        AggregateLifecycle.apply(new AccountActivatedEvent(
                command.getId(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        log.info("AccountCreatedEvent occurred");
        this.accountId =event.accountId();
        this.currentBalance = event.initialBalance();
        this.currency = event.currency();
        this.status = event.accountStatus();
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        log.info("AccountActivatedEvent occurred");
        this.accountId =event.accountId();
        this.status = event.status();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command){
        log.info("CreditAccountCommand Command Received");
        if (!this.getStatus().equals(AccountStatus.ACTIVATED)) throw new RuntimeException("The account " + accountId + " is not activated");
        if ( command.getAmount()<=0) throw new IllegalArgumentException("Amount negative exception");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        log.info("AccountCreditedEvent occurred");
        this.accountId = event.accountId();
        this.currentBalance = this.currentBalance + event.amount();
    }

    @CommandHandler
    public void handle(DebitAccountCommand command){
        log.info("DebitAccountCommand Command Received");
        if (!this.getStatus().equals(AccountStatus.ACTIVATED)) throw new RuntimeException("The account " + accountId + " is not activated");
        if (command.getAmount()<=0) throw new IllegalArgumentException("Amount negative exception");
        if (currentBalance<command.getAmount()) throw new IllegalArgumentException("Balance not sufficient exception");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        log.info("AccountDebitedEvent occurred");
        this.accountId = event.accountId();
        this.currentBalance = this.currentBalance - event.amount();
    }

    @CommandHandler
    public void handle(UpdateAccountStatusCommand command){
        log.info("UpdateAccountStatusCommand Command Received");
        if (command.getStatus() == status) throw new RuntimeException("This account " + command.getId() + " is already " + command.getStatus());
        AggregateLifecycle.apply(new AccountStatusUpdatedEvent(
                command.getId(),
                command.getStatus()
        ));
    }

    @EventSourcingHandler
    public void on(AccountStatusUpdatedEvent event){
        log.info("AccountStatusUpdatedEvent occurred");
        this.accountId = event.accountId();
        this.status = event.status();
    }

}
