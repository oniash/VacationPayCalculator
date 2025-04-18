package com.example.neoflexcalculator.controller;

import com.example.neoflexcalculator.dto.VacationPayResponse;
import com.example.neoflexcalculator.service.CalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/calculate")
public class VacationPayController {
    @Autowired
    private CalculatorService calculatorService;

    @GetMapping
    public String calculateVacationPay(@RequestParam(required = false) Integer vacationDays,
                                       @RequestParam(required = false) Double averageSalary,
                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate) {

        if (vacationDays == null || averageSalary == null) {
            return "Ошибка: Параметры vacationDays и averageSalary обязательны.";
        }

        VacationPayResponse result = calculatorService.calculateVacationPay(vacationDays,
                averageSalary, startDate);
        if (result != null) {
            if (startDate == null) {
                return "Отпускные: " + String.format("%.2f", result.getVacationPay()) + " за " + result.getVacationDays() + " дней";
            } else {
                return "Отпускные: " + String.format("%.2f", result.getVacationPay()) + " с " + result.getVacationStartDate() + " по " + result.getVacationEndDate();
            }
        }
        return "Один или несколько параметров заданы неверно";
    }
}
