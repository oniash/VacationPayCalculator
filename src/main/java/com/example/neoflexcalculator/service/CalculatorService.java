package com.example.neoflexcalculator.service;

import com.example.neoflexcalculator.dto.VacationPayResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class CalculatorService {

    private static final double AVERAGE_DAYS_IN_MONTH = 29.3;

    private static final Map<Integer, List<Integer>> holidays = Map.of(
            1, List.of(1, 2, 3, 4, 5, 6, 7, 8),
            2, List.of(23),
            3, List.of(8),
            5, List.of(1, 8, 9),
            6, List.of(12),
            11, List.of(4),
            12, List.of(31));

    public VacationPayResponse calculateVacationPay(Integer days, Double salary, LocalDate date) {
        VacationPayResponse vacationPayResponse = new VacationPayResponse();
        if (isVacationDaysValid(days) && isSalaryValid(salary)) {
            vacationPayResponse.setVacationDays(days);
            if (isStartDateValid(date)) {
                vacationPayResponse.setVacationStartDate(date);
                vacationPayResponse.setVacationEndDate(date.plusDays(days - 1));
                vacationPayResponse.setVacationPay(salary / (12 * AVERAGE_DAYS_IN_MONTH) * getVacationDaysWithoutHolidays(days, date));
            } else {
                vacationPayResponse.setVacationPay(salary / (12 * AVERAGE_DAYS_IN_MONTH) * days);
            }
            return vacationPayResponse;
        }
        return null;
    }

    private Integer getVacationDaysWithoutHolidays(Integer days, LocalDate firstDay) {
        LocalDate lastDay = firstDay.plusDays(days - 1);
        int daysWithoutHolidays = 0;
        for (LocalDate i = firstDay; i.isBefore(lastDay) || i.isEqual(lastDay); i = i.plusDays(1)) {
            if (!isHoliday(i) && !isWeekend(i)) {
                daysWithoutHolidays++;
            }
        }
        return daysWithoutHolidays;
    }

    private boolean isHoliday(LocalDate day) {
        if (holidays.containsKey(day.getMonthValue())) {
            return holidays.get(day.getMonthValue()).contains(day.getDayOfMonth());
        }
        return false;
    }

    private boolean isWeekend(LocalDate day) {
        return day.getDayOfWeek().getValue() >= 6;
    }

    private boolean isVacationDaysValid(Integer vacationDays) {
        return (vacationDays != null && vacationDays > 0);
    }

    private boolean isSalaryValid(Double salary) {
        return (salary != null && salary > 0);
    }

    private boolean isStartDateValid(LocalDate startDate) {
        return startDate != null;
    }
}
