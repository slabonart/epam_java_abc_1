package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class BankCardFactoryTest {

    private static final User TEST_USER = new User("John", "Doe", LocalDate.of(2020, 8, 1));

    BankCardFactory factory = new BankCardFactory();

    @Test
    void whenCreateBankCard_andTypeIsCredit_thenSuccess() {

        BankCard bankCard = factory.createBankCard(TEST_USER, BankCardType.CREDIT);

        assertNotNull(bankCard);
        assertTrue(bankCard instanceof CreditBankCard);
        CreditBankCard creditBankCard = (CreditBankCard) bankCard;

        assertEquals(0d, creditBankCard.getCreditLimit());
        assertEquals(TEST_USER, creditBankCard.getUser());
        assertEquals(36, creditBankCard.getNumber().length());

    }

    @Test
    void whenCreateBankCard_andTypeIsDebit_thenSuccess() {

        BankCard bankCard = factory.createBankCard(TEST_USER, BankCardType.DEBIT);

        assertNotNull(bankCard);
        assertTrue(bankCard instanceof DebitBankCard);
        DebitBankCard debitBankCard = (DebitBankCard) bankCard;

        assertEquals(0d, debitBankCard.getBalance());
        assertEquals(TEST_USER, debitBankCard.getUser());
        assertEquals(36, debitBankCard.getNumber().length());

    }

    @Test
    void whenCreateBankCard_andTypeIsNull_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createBankCard(TEST_USER, null));
        assertEquals("Card type cannot be null", exception.getMessage());
    }

    @Test
    void whenCreateBankCard_andUserIsNull_thenThrowException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createBankCard(null, BankCardType.CREDIT));
        assertEquals("Card type cannot be null", exception.getMessage());
    }

}