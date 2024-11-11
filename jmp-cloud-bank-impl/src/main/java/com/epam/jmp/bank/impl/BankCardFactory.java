package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class BankCardFactory {

    private static final double INITIAL_VALUE = 0d;

    private final Map<BankCardType, TriFunction<String, User, Double, BankCard>> cardConstructors = new EnumMap<>(BankCardType.class);

    public BankCardFactory() {
        cardConstructors.put(BankCardType.DEBIT, DebitBankCard::new);
        cardConstructors.put(BankCardType.CREDIT, CreditBankCard::new);
    }

    public BankCard createBankCard(User user, BankCardType type) {

        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        if (type == null) {
            throw new IllegalArgumentException("Card type cannot be null");
        }

        var constructor = cardConstructors.get(type);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown card type: " + type);
        }

        return constructor.apply(createNumber(), user, INITIAL_VALUE);
    }

    private String createNumber() {
        return UUID.randomUUID().toString();
    }
}
