package com.yosekit.creditmanager.service;

import com.yosekit.creditmanager.model.Application;
import com.yosekit.creditmanager.model.Application.ApplicationStatus;
import com.yosekit.creditmanager.model.Contract;
import com.yosekit.creditmanager.model.Contract.ContractSignStatus;
import com.yosekit.creditmanager.repository.ApplicationRepository;
import com.yosekit.creditmanager.repository.ContractRepository;
import com.yosekit.creditmanager.service.temp.ContractValueGenerator;
import com.yosekit.creditmanager.viewmodel.ContractViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@Transactional
public class ContractService {

    @Autowired
    private ContractValueGenerator contractValueGenerator;

    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ApplicationRepository applicationRepository;

    public List<ContractViewModel> getAllContracts() {
        return contractRepository.findAll().stream()
                .map(contract -> new ContractViewModel(
                        contract.getId(),
                        contract.getAmount(),
                        contract.getTerm(),
                        contract.getCreatedAt(),
                        contract.getApplication().getClient(),
                        contract.getSignDate(),
                        contract.getSignStatus()
                )).toList();
    }


    public void createContract(Application application) {
        if (application.getContract() != null) {
            return;
        }

        var contract = new Contract();
        contract.setApplication(application);
        application.setContract(contract);

        // имитация принятия решения о контракте
        contractValueGenerator.generateValues(contract, application);

        contractRepository.save(contract);
    }

    public void signContract(Long id) {
        var contract = contractRepository.findById(id);

        contract.setSignStatus(ContractSignStatus.SIGNED);
        contract.setSignDate(LocalDate.now());

        contractRepository.save(contract);
    }


}
