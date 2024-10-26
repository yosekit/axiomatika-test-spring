package com.yosekit.creditmanager.controller;

import com.yosekit.creditmanager.request.CreateApplicationRequest;
import com.yosekit.creditmanager.request.CreateClientRequest;
import com.yosekit.creditmanager.request.CreateEmploymentRequest;
import com.yosekit.creditmanager.request.CreatePassportRequest;
import com.yosekit.creditmanager.service.ApplicationService;
import com.yosekit.creditmanager.service.ClientService;
import com.yosekit.creditmanager.service.ContractService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String applications(Model model) {
        var appViewModels = applicationService.getAllApplications();

        model
                .addAttribute("title", "Applications Page")
                .addAttribute("appViewModels", appViewModels);
        return "applications";
    }

    @GetMapping("/create")
    public String index(Model model) {
        var applicationRequest = new CreateApplicationRequest(
                new CreateClientRequest(
                        new CreateEmploymentRequest(),
                        new CreatePassportRequest()
                )
        );

        var clients = clientService.getAllClientsPlain();

        model
                .addAttribute("title", "Create Page")
                .addAttribute("application", applicationRequest)
                .addAttribute("existingClients", clients);
        return "create";
    }

    @PostMapping("/create")
    public String createApplication(
            @Valid @ModelAttribute("application") CreateApplicationRequest applicationRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {
        boolean isNewClient = applicationRequest.getExistingClientId() == null;

        // TODO validation not working
        if (isNewClient && bindingResult.hasErrors()) {
            return "create";
        }

        Long appId = applicationService.createClientWithApplication(applicationRequest);

        redirectAttributes
                .addFlashAttribute("appCreated", true)
                .addFlashAttribute("newAppId", appId);
        return "redirect:/applications/create";
    }

    @PostMapping("/approve")
    public String approveApplication(@RequestParam Long appId, RedirectAttributes redirectAttributes) {
        Long contractId = applicationService.createContractFromApplication(appId);

        redirectAttributes
                .addFlashAttribute("contractCreated", true)
                .addFlashAttribute("newContractId", contractId);
        return "redirect:/applications";
    }

    @PostMapping("/reject")
    public String rejectApplication(@RequestParam Long appId) {
        applicationService.rejectApplication(appId);

        return "redirect:/applications";
    }
}
