package com.example.neoflexcalculator.service;

import com.example.neoflexcalculator.dto.VacationPayResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp(){
        calculatorService = new CalculatorService();
    }

    @Test
    void testCalculateVacationPayWithoutDates() {
        VacationPayResponse response = calculatorService.calculateVacationPay(14, 100000.0, null);
        assertEquals(100000.0 / (12 * 29.3) * 14, response.getVacationPay());
    }

    @Test
    void testVacationDaysIsZero(){
        VacationPayResponse response = calculatorService.calculateVacationPay(0, 100000.0, null);
        assertNull(response);
    }

    @Test
    void testCalculateVacationPayWithHolidays() {
        LocalDate startDate = LocalDate.of(2025, 1, 1); // Праздничные дни
        VacationPayResponse response = calculatorService.calculateVacationPay(10, 100000.0, startDate);
        assertEquals(100000.0 / (12 * 29.3) * 2, response.getVacationPay());
    }

    @Test
    void testInvalidVacationDays() {
        VacationPayResponse response = calculatorService.calculateVacationPay(-5, 100000.0, null);
        assertNull(response);
    }
}