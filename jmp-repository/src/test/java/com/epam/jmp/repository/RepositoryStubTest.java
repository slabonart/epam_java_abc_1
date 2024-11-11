package com.epam.jmp.repository;

import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RepositoryStubTest {

    private static final User TEST_USER = new User("John", "Doe", LocalDate.of(2020, 8, 1));

    private static final Subscription TEST_SUBSCRIPTION = new Subscription("0001", LocalDate.now());

    RepositoryStub repository = new RepositoryStub();

    @Test
    void whenAddUser_thenSuccess() {

        repository.addUser(TEST_USER);

        assertEquals(1, repository.getAllUsers().size());
    }

    @Test
    void whenAddSubscription_thenSuccess() {

        repository.addSubscription(TEST_SUBSCRIPTION);

        assertEquals(1, repository.getAllSubscriptions().size());
    }

}