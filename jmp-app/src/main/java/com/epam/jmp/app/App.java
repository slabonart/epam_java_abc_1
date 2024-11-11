package com.epam.jmp.app;

import com.epam.jmp.bank.Bank;
import com.epam.jmp.bank.impl.BankCardFactory;
import com.epam.jmp.bank.impl.BankImpl;
import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.Subscription;
import com.epam.jmp.dto.User;
import com.epam.jmp.repository.RepositoryStub;
import com.epam.jmp.service.Service;
import com.epam.jmp.service.impl.ServiceImpl;
import com.epam.jmp.service.impl.SubscriptionNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class App {

    private static RepositoryStub repository = new RepositoryStub();
    private static Service service = new ServiceImpl(repository);
    private static BankCardFactory bankCardFactory = new BankCardFactory();
    private static Bank bank = new BankImpl(bankCardFactory);

    private static List<BankCard> debitCards = new ArrayList<>();
    private static List<BankCard> creditCards = new ArrayList<>();

    private static final List<User> TEST_USERS = List.of(
            new User("John", "Travolta", LocalDate.of(1954, 2, 18)),
            new User("Brad", "Pitt", LocalDate.of(1963, 12, 18)),
            new User("Julia", "Smith", LocalDate.of(2010, 10, 2))
    );

    public static void main(String[] args) {
        runStepOne();
        runStepTwo();
        runStepThree();
        runStepFour();
        runStepFive();
        runStepSix();
        runStepSeven();
        runStepEight();

        System.out.println("\nThank you!");
    }

    private static void runStepOne() {
        System.out.println("\nSTEP 1 - initializing repository users - testing addUser()");

        TEST_USERS.stream().forEach(repository::addUser);
        List<User> existingUsers = service.getAllUsers();

        System.out.println("Created users: ");
        existingUsers.stream().forEach(
                user -> System.out.println("\t" + user)
        );
    }

    private static void runStepTwo() {
        System.out.println("\nSTEP 2 - testing Service isPayableUser(User) method");
        List<User> existingUsers = service.getAllUsers();
        existingUsers.stream().forEach(
                user -> System.out.println("\t" + user.getName() + " " + user.getSurname() + ": " + Service.isPayableUser(user))
        );
    }

    private static void runStepThree() {
        System.out.println("\nSTEP 3 - testing Service getAverageUsersAge() method");
        System.out.println("\t Average age: " + service.getAverageUsersAge());
    }

    private static void runStepFour() {
        System.out.println("\nSTEP 4 - testing Bank createBankCard() method");

        List<User> existingUsers = service.getAllUsers();
        debitCards.addAll(existingUsers.stream()
                .map(user -> bank.createBankCard(user, BankCardType.DEBIT))
                .collect(Collectors.toList()));
        creditCards.addAll(existingUsers.stream()
                .map(user -> bank.createBankCard(user, BankCardType.CREDIT))
                .collect(Collectors.toList()));
        System.out.println("Created DEBIT cards:");
        debitCards.stream().forEach(System.out::println);
        System.out.println("Created CREDIT cards:");
        creditCards.stream().forEach(System.out::println);
    }

    private static void runStepFive() {
        System.out.println("\nSTEP 5 - testing Service subscribe() method");
        creditCards.stream().forEach(service::subscribe);

        List<Subscription> allSubscriptions = service.getAllSubscriptionsByCondition(Objects::nonNull);
        allSubscriptions.stream()
                .forEach(System.out::println);
    }

    private static void runStepSix() {
        System.out.println("\nSTEP 6 - testing Service getSubscriptionByBankCardNumber(String cardNumber) method");
        creditCards.stream().forEach(
                creditCard -> System.out.print("\n\tCard number: " + creditCard.getNumber() + "\tsubscription: " + service.getSubscriptionByBankCardNumber(creditCard.getNumber()))
        );

    }

    private static void runStepSeven() {
        System.out.println("\n\nSTEP 7 - testing Service getAllSubscriptionsByCondition(Predicate<Subscription>) method");
        System.out.println("Condition - card number contains \"11\" sequence");

        Predicate<Subscription> predicate = subscription -> subscription.getBankcard().contains("11");

        service.getAllSubscriptionsByCondition(predicate).stream()
                .forEach(System.out::println);
    }

    private static void runStepEight() {
        System.out.println("\nSTEP 8 - testing Service SubscriptionNotFoundException");
        System.out.println("Condition - card number is \"invalid\" sequence");

        try {
            service.getSubscriptionByBankCardNumber("invalid");
        } catch (SubscriptionNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

}