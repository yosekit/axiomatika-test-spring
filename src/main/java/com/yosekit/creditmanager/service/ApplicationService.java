package com.yosekit.creditmanager.service;

import com.yosekit.creditmanager.model.*;
import com.yosekit.creditmanager.model.Application.ApplicationStatus;
import com.yosekit.creditmanager.repository.ApplicationRepository;
import com.yosekit.creditmanager.repository.ClientRepository;
import com.yosekit.creditmanager.request.CreateApplicationRequest;
import com.yosekit.creditmanager.viewmodel.ApplicationViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;
    @Autowired
    private ContractService contractService;


    public List<ApplicationViewModel> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(app -> new ApplicationViewModel(
                        app.getId(),
                        app.getRequiredAmount(),
                        app.getRequiredTerm(),
                        app.getCreatedAt(),
                        app.getClient(),
                        app.getStatus()
                )).toList();
    }


    public void createClientWithApplication(CreateApplicationRequest applicationRequest) {
        var client = clientService.createClient(applicationRequest.getClient());

        var application = new Application(
                applicationRequest.getRequiredAmount(),
                applicationRequest.getRequiredTerm()
        );

        application.setClient(client);
        client.addApplication(application);

        applicationRepository.save(application);
    }

    public void createContractFromApplication(Long appId) {
        var application = applicationRepository.findById(appId);

        if (application.getContract() != null) {
            return;
        }

        this.approveApplication(application);

        contractService.createContract(application);
    }


    public void rejectApplication(Application application) {
        application.setStatus(ApplicationStatus.REJECTED);
        applicationRepository.save(application);
    }
    public void rejectApplication(Long appId) {
        var application = applicationRepository.findById(appId);

        this.rejectApplication(application);
    }

    public void approveApplication(Application application) {
        application.setStatus(ApplicationStatus.APPROVED);
        applicationRepository.save(application);
    }
    public void approveApplication(Long appId) {
        var application = applicationRepository.findById(appId);

        this.approveApplication(application);
    }

}
