package com.epam.jmp.service;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Predicate;

public interface Service {

    long ADULT_YEARS = 18;

    void subscribe(BankCard bankCard);

    Subscription getSubscriptionByBankCardNumber(String cardNumber);

    List<User> getAllUsers();

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> predicate);

    default double getAverageUsersAge() {
        return getAllUsers().stream()
                .map(User::getBirthday)
                .map(birthDay -> Period.between(birthDay, LocalDate.now()))
                .mapToInt(Period::getYears)
                .average()
                .orElse(0);
    }

    static boolean isPayableUser(User user) {
        return !user.getBirthday().isAfter(LocalDate.now().minus(ADULT_YEARS, ChronoUnit.YEARS));
    }
}
