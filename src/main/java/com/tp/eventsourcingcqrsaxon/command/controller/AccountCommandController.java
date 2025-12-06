package com.tp.eventsourcingcqrsaxon.command.controller;

import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import com.tp.eventsourcingcqrsaxon.command.command.AddAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.CreditAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.DebitAccountCommand;
import com.tp.eventsourcingcqrsaxon.command.command.UpdateAccountStatusCommand;
import com.tp.eventsourcingcqrsaxon.command.dto.AddNewAccountRequestDTO;
import com.tp.eventsourcingcqrsaxon.command.dto.CreditAccountRequestDTO;
import com.tp.eventsourcingcqrsaxon.command.dto.DebitAccountRequestDTO;
import com.tp.eventsourcingcqrsaxon.command.dto.UpdateAccountStatusRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/add")
    public CompletableFuture<Object> addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        CompletableFuture<Object> response = commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return response;
    }

    @GetMapping("/events/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new DebitAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @PutMapping("/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountStatusRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(),
                request.status()
        ));
        return result;
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception) {
        return exception.getMessage();
    }
}
