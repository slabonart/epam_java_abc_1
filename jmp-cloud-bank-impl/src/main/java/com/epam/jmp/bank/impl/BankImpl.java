package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.User;
import com.epam.jmp.bank.Bank;


public class BankImpl implements Bank {

    private final BankCardFactory bankCardFactory;

    public BankImpl(BankCardFactory bankCardFactory) {
        this.bankCardFactory = bankCardFactory;
    }

    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {
        return bankCardFactory.createBankCard(user, cardType);
    }

}
