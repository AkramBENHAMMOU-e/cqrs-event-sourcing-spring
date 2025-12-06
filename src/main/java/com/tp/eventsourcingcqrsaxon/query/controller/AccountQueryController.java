package com.tp.eventsourcingcqrsaxon.query.controller;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import com.tp.eventsourcingcqrsaxon.query.dto.AccountStatementResponseDTO;
import com.tp.eventsourcingcqrsaxon.query.entity.Account;
import com.tp.eventsourcingcqrsaxon.query.query.GetAccountStatementQuery;
import com.tp.eventsourcingcqrsaxon.query.query.GetAllAccountsQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/accounts")
@CrossOrigin("*")
public class AccountQueryController {
    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts(){
        CompletableFuture<List<Account>> result = queryGateway.query(
                new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class)
        );
        return result;
    }

    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId) {
        return queryGateway.query(
                new GetAccountStatementQuery(accountId),
                ResponseTypes.instanceOf(AccountStatementResponseDTO.class)
        );
    }

}
