package com.epam.jmp.service.impl;


import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.repository.RepositoryStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceImplTest {

    private static final User TEST_USER = new User(
            "John", "Doe", LocalDate.of(2000, 1, 15)
    );

    @InjectMocks
    ServiceImpl service;

    @Mock
    RepositoryStub subscriptionRepositoryStub;

    @Test
    void whenSubscribe_thenCallRepositoryMethod() {

        service.subscribe(new DebitBankCard("0001", TEST_USER, 1000));

        verify(subscriptionRepositoryStub, times(1)).addSubscription(any());
    }

    @Test
    void whenGetSubscriptionByBankCardNumber_thenReturnExistingSubscription() {
        when(subscriptionRepositoryStub.getAllSubscriptions()).thenReturn(getTestSubscriptions());
        Subscription result = service.getSubscriptionByBankCardNumber("0002");

        assertEquals("0002", result.getBankcard());
    }

    @Test
    void whenGetSubscriptionByNotExistingBankCardNumber_thenThrowException() {

        when(subscriptionRepositoryStub.getAllSubscriptions()).thenReturn(getTestSubscriptions());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.getSubscriptionByBankCardNumber("0003"));

        assertTrue(exception instanceof SubscriptionNotFoundException);
        assertEquals("No subscription found for card number: 0003", exception.getMessage());
    }

    @Test
    void whenCalculateAverageAgeForEmptyList_thenReturnZero() {

        when(subscriptionRepositoryStub.getAllUsers()).thenReturn(Collections.EMPTY_LIST);

        double averageUsersAge = service.getAverageUsersAge();

        assertEquals(0, averageUsersAge);
    }

    @Test
    void whenCalculateAverageAgeForOneUser_thenReturnZero() {

        List<User> users = List.of(
                createUserWithAge(10),
                createUserWithAge(63)
        );

        when(subscriptionRepositoryStub.getAllUsers()).thenReturn(users);

        double averageUsersAge = service.getAverageUsersAge();

        assertEquals(36.5, averageUsersAge);
    }

    @Test
    void whenGetAllSubscriptionsForThisYear_thenReturnProperResult() {

        Predicate<Subscription> thisYearSubscriptions = subscription -> LocalDate.now().getYear() == subscription.getStartDate().getYear();

        when(subscriptionRepositoryStub.getAllSubscriptions()).thenReturn(getTestSubscriptions());

        List<Subscription> result = service.getAllSubscriptionsByCondition(thisYearSubscriptions);

        assertEquals(1, result.size());
        assertEquals("0001", result.get(0).getBankcard());

    }

    private User createUserWithAge(int age) {
        return new User("John", "Doe", LocalDate.now().minusYears(age));
    }

    private List<Subscription> getTestSubscriptions() {
        return List.of(
                new Subscription("0001", LocalDate.now()),
                new Subscription("0002", LocalDate.now().minusYears(1))
        );
    }

}