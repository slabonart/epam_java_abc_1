package com.epam.jmp.repository;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class RepositoryStub {

    private final Map<String, Subscription> subscriptions = new HashMap<>();

    private final Set<User> users = new HashSet<>();

    public void addSubscription(Subscription subscription) {
        subscriptions.put(subscription.getBankcard(), subscription);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getAllUsers() {
        return users.stream()
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptions.values().stream()
                .collect(Collectors.toUnmodifiableList());
    }
}
