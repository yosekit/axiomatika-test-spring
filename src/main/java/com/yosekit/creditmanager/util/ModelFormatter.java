package com.yosekit.creditmanager.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Locale;

public class ModelFormatter {

    public static String formatMoneyAmount(BigDecimal amount) {
        var numberFormat = NumberFormat.getInstance(new Locale("ru", "RU"));
        numberFormat.setGroupingUsed(true);

        return numberFormat.format(amount) + " руб.";
    }

    public static String formatDate(LocalDate date) {
        var now = LocalDate.now();
        var period = Period.between(now, date);

        int years = period.getYears();
        int months = period.getMonths();

        var result = new StringBuilder();

        if (years > 0) {
            result.append(years).append(" г. ");
        }
        if (months > 0) {
            result.append(months).append(" мес.");
        }

        return result.isEmpty() ? date.toString() : result.toString().trim();
    }

    public static String formatTimestamp(LocalDateTime timestamp) {
        return timestamp.toString();
    }

    public static String formatPhone(String phone) {
        // TODO
        return phone;
    }
}
