package com.epam.jmp.service;

import com.epam.jmp.dto.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ServiceTest {

    @Test
    void whenIsPayable_andUserIsTooYoung_thenReturnFalse() {

        User user = new User("John", "Doe", LocalDate.now().minusYears(18).plusDays(1));

        assertFalse(Service.isPayableUser(user));
    }

    @Test
    void whenIsPayable_andUserIsAdult_thenReturnFalse() {

        User user = new User("John", "Doe", LocalDate.now().minusYears(18));

        assertTrue(Service.isPayableUser(user));
    }

}