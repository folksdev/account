package com.folksdev.account.dto;

import com.folksdev.account.model.Account;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class AccountDtoConverter {

    private final CustomerDtoConverter customerDtoConverter;
    private final TransactionDtoConverter transactionDtoConverter;

    public AccountDtoConverter(CustomerDtoConverter customerDtoConverter,
                               TransactionDtoConverter transactionDtoConverter) {
        this.customerDtoConverter = customerDtoConverter;
        this.transactionDtoConverter = transactionDtoConverter;
    }

    public AccountDto convert(Account from) {
        return new AccountDto(from.getId(),
                from.getBalance(),
                from.getCreationDate(),
                customerDtoConverter.convertToAccountCustomer(from.getCustomer()),
                Objects.requireNonNull(from.getTransaction())
                        .stream()
                        .map(transactionDtoConverter::convert)
                        .collect(Collectors.toSet()));
    }
}
