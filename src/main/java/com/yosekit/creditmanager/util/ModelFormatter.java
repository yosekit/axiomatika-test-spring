package com.yosekit.creditmanager.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class ModelFormatter {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final DateTimeFormatter timestampFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final NumberFormat numberFormatter = NumberFormat.getInstance(new Locale("ru", "RU"));
    static {
        numberFormatter.setGroupingUsed(true);
        numberFormatter.setMaximumFractionDigits(0);
    }

    public static String formatMoneyAmount(BigDecimal amount) {
        return numberFormatter.format(amount) + " руб.";
    }

    public static String formatDate(LocalDate date) {
        var now = LocalDate.now();
        var period = Period.between(now, date);

        int years = period.getYears();
        int months = period.getMonths();

        var dateShortBuilder = new StringBuilder();

        if (years > 0) {
            dateShortBuilder.append(years).append(" г. ");
        }
        if (months > 0) {
            dateShortBuilder.append(months).append(" мес.");
        }

        String dateBase = date.format(dateFormatter);

        return dateShortBuilder.isEmpty()
                ? dateBase
                : dateShortBuilder.toString().trim() + " [" + dateBase + "]";
    }

    public static String formatTimestamp(LocalDateTime timestamp) {
        return timestamp.format(timestampFormatter);
    }

    public static String formatPhone(String phone) {
        // Убираем лишние символы, оставляем только цифры
        var phoneNumber = phone.replaceAll("\\D", "");

        // Проверяем длину номера и корректируем, если он начинается с 8
        if (phoneNumber.length() == 11 && phoneNumber.startsWith("8")) {
            phoneNumber = "7" + phoneNumber.substring(1);
        } else if (phoneNumber.length() == 10) {
            phoneNumber = "7" + phoneNumber;
        }

        return phoneNumber.replaceFirst("(\\d)(\\d{3})(\\d{3})(\\d{2})(\\d{2})", "+$1 ($2) $3-$4-$5");
    }

}
