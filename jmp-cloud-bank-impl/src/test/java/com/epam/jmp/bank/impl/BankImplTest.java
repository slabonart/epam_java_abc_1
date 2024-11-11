package com.epam.jmp.bank.impl;

import com.epam.jmp.dto.BankCard;
import com.epam.jmp.dto.BankCardType;
import com.epam.jmp.dto.CreditBankCard;
import com.epam.jmp.dto.DebitBankCard;
import com.epam.jmp.dto.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankImplTest {

    private static final User TEST_USER = new User("John", "Doe", LocalDate.of(2020, 8, 1));

    @InjectMocks
    private BankImpl bank;

    @Mock
    private BankCardFactory bankCardFactory;

    @Test
    void whenCreateCreditBankCard_thenCreateCardOfProperType() {

        when(bankCardFactory.createBankCard(any(), any())).thenReturn(new CreditBankCard("", TEST_USER, 0d));

        BankCard bankCard = bank.createBankCard(TEST_USER, BankCardType.CREDIT);

        verify(bankCardFactory, times(1)).createBankCard(any(), any());


    }

    @Test
    void whenCreateDebitBankCard_thenCreateCardOfProperType() {

        when(bankCardFactory.createBankCard(any(), any())).thenReturn(new DebitBankCard("", TEST_USER, 0d));

        BankCard bankCard = bank.createBankCard(TEST_USER, BankCardType.DEBIT);

        verify(bankCardFactory, times(1)).createBankCard(any(), any());
    }

    @Test
    void whenCreateDebitBankCard_andFactoryThrowsException_thenException() {

        when(bankCardFactory.createBankCard(any(), any())).thenThrow(new IllegalArgumentException("test message"));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> bank.createBankCard(null, BankCardType.DEBIT));

        verify(bankCardFactory, times(1)).createBankCard(any(), any());
        assertEquals("test message", exception.getMessage());
    }


}