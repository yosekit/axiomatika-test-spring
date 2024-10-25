package com.yosekit.creditmanager.service.temp;

import com.yosekit.creditmanager.model.Application;
import com.yosekit.creditmanager.model.Contract;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ContractValueGenerator {
    
    public void generateValues(Contract contract, Application application) {
        var requiredAmount = application.getRequiredAmount();
        var requiredTerm = application.getRequiredTerm();
        contract.setAmount(this.generateAmount(requiredAmount));
        contract.setTerm(this.generateTerm(requiredTerm));

        var signStatus = this.generateSignStatus();
        contract.setSignStatus(signStatus);
        contract.setSignDate(this.generateSignDate(signStatus));
    }
    
    private LocalDate generateTerm(LocalDate requiredTerm) {
        // Генерируем случайное количество месяцев от 1 до 12
        int randomMonths = ThreadLocalRandom.current().nextInt(1, 13);

        // Добавляем случайное количество месяцев к переданной дате
        return requiredTerm.plusMonths(randomMonths);
    }

    private BigDecimal generateAmount(BigDecimal requiredAmount) {
        // Генерируем случайный процент от -20 до 20
        double randomPercentage = ThreadLocalRandom.current().nextDouble(-0.20, 0.21);

        // Вычисляем изменение исходной суммы
        BigDecimal percentageChange = requiredAmount
                .multiply(BigDecimal.valueOf(randomPercentage))
                .setScale(2, RoundingMode.HALF_UP); // Округляем до двух знаков после запятой

        // Добавляем изменение к исходной сумме
        BigDecimal randomAmount = requiredAmount.add(percentageChange);

        return randomAmount.setScale(2, RoundingMode.HALF_UP);
    }
    

    private Contract.ContractSignStatus generateSignStatus() {
        return ThreadLocalRandom.current().nextBoolean()
                ? Contract.ContractSignStatus.SIGNED
                : Contract.ContractSignStatus.UNSIGNED;
    }

    private LocalDate generateSignDate(Contract.ContractSignStatus status) {
        if (status == Contract.ContractSignStatus.SIGNED) {
            long randomDays = ThreadLocalRandom.current().nextLong(0, 11);
            return LocalDate.now().plusDays(randomDays);
        }

        return null;
    }
}
