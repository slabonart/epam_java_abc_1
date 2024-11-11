package com.epam.jmp.service.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.repository.RepositoryStub;
import com.epam.jmp.service.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class ServiceImpl implements Service {

    private RepositoryStub repository;

    public ServiceImpl(RepositoryStub repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe(BankCard bankCard) {
        repository.addSubscription(
                new Subscription(bankCard.getNumber(), LocalDate.now())
        );
    }

    @Override
    public Subscription getSubscriptionByBankCardNumber(String cardNumber) {
        return repository.getAllSubscriptions().stream()
                .filter(subscription -> cardNumber.equals(subscription.getBankcard()))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException(cardNumber));
    }

    @Override
    public List<User> getAllUsers() {
        return repository.getAllUsers();
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate) {
        return repository.getAllSubscriptions().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }
}
