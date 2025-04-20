package com.example.neoflexcalculator.controller;
import com.example.neoflexcalculator.dto.VacationPayResponse;
import com.example.neoflexcalculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class VacationPayControllerTest {

    @Mock
    private CalculatorService calculatorService;

    @InjectMocks
    private VacationPayController vacationPayController;

    @Test
    void calculateVacationPay_WithoutDate_ReturnsCorrectString() {
        // Arrange
        int days = 10;
        double salary = 1000.0;
        VacationPayResponse mockResponse = new VacationPayResponse();
        mockResponse.setVacationPay(284.39);
        mockResponse.setVacationDays(days);

        when(calculatorService.calculateVacationPay(days, salary, null))
                .thenReturn(mockResponse);

        String result = vacationPayController.calculateVacationPay(days, salary, null);

        assertEquals("Отпускные: 284.39 за 10 дней", result);
    }

    @Test
    void calculateVacationPay_WithDates_ReturnsCorrectString() {

        int days = 14;
        double salary = 1500.0;
        LocalDate startDate = LocalDate.of(2025, 6, 1);
        LocalDate endDate = LocalDate.of(2025, 6, 14);

        VacationPayResponse mockResponse = new VacationPayResponse();
        mockResponse.setVacationPay(1200.0);
        mockResponse.setVacationDays(days);
        mockResponse.setVacationStartDate(startDate);
        mockResponse.setVacationEndDate(endDate);

        when(calculatorService.calculateVacationPay(days, salary, startDate))
                .thenReturn(mockResponse);

        String result = vacationPayController.calculateVacationPay(days, salary, startDate);

        assertEquals("Отпускные: 1200.00 с 2025-06-01 по 2025-06-14", result);
        verify(calculatorService).calculateVacationPay(days, salary, startDate);
    }

    @Test
    void calculateVacationPay_MissingRequiredParameters_ReturnsErrorMessage() {
        assertEquals("Ошибка: Параметры vacationDays и averageSalary обязательны",
                vacationPayController.calculateVacationPay(null, 1000.0, null));

        assertEquals("Ошибка: Параметры vacationDays и averageSalary обязательны",
                vacationPayController.calculateVacationPay(10, null, null));
    }

    @Test
    void calculateVacationPay_ZeroDays_ReturnsErrorMessage() {
        when(calculatorService.calculateVacationPay(0, 1000.0, null))
                .thenReturn(null);

        String result = vacationPayController.calculateVacationPay(0, 1000.0, null);

        assertEquals("Ошибка: один или несколько параметров заданы неверно", result);
        verify(calculatorService).calculateVacationPay(0, 1000.0, null);
    }

    @Test
    void calculateVacationPay_ZeroSalary_ReturnsErrorMessage() {
        when(calculatorService.calculateVacationPay(10, 0.0, null))
                .thenReturn(null);

        String result = vacationPayController.calculateVacationPay(10, 0.0, null);

        assertEquals("Ошибка: один или несколько параметров заданы неверно", result);
    }

}