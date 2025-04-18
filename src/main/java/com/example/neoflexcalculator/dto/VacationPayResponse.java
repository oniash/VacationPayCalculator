package com.example.neoflexcalculator.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationPayResponse {
    private double vacationPay;
    private int vacationDays;
    private LocalDate vacationStartDate;
    private LocalDate vacationEndDate;
}
