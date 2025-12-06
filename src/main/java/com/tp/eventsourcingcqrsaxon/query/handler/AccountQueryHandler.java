package com.tp.eventsourcingcqrsaxon.query.handler;

import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import com.tp.eventsourcingcqrsaxon.query.dto.AccountStatementResponseDTO;
import com.tp.eventsourcingcqrsaxon.query.entity.Account;
import com.tp.eventsourcingcqrsaxon.query.entity.AccountOperation;
import com.tp.eventsourcingcqrsaxon.query.query.GetAccountStatementQuery;
import com.tp.eventsourcingcqrsaxon.query.query.GetAllAccountsQuery;
import com.tp.eventsourcingcqrsaxon.query.repository.AccountRepository;
import com.tp.eventsourcingcqrsaxon.query.repository.OperationRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }
}
