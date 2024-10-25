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

import java.util.Map;

@Controller
@RequestMapping("/applications")
public class ApplicationController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationController.class);

    @Autowired
    private ApplicationService applicationService;

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
        var application = new CreateApplicationRequest(
                new CreateClientRequest(
                        new CreateEmploymentRequest(),
                        new CreatePassportRequest()
                )
        );

        model
                .addAttribute("title", "Create Page")
                .addAttribute("application", application);
        return "create";
    }

    @PostMapping("/create")
    public String createApplication(
            @Valid @ModelAttribute CreateApplicationRequest applicationRequest,
            BindingResult bindingResult) {

//        if (bindingResult.hasErrors()) {
//            return "create";
//        }

        applicationService.createClientWithApplication(applicationRequest);

        return "redirect:/applications";
    }

    @PostMapping("/approve")
    public String approveApplication(@RequestBody Map<String, Long> requestData) {
        Long appId = requestData.get("id");

        applicationService.createContractFromApplication(appId);

        return "redirect:/contracts";
    }

    @PostMapping("/reject")
    public String rejectApplication(@RequestBody Map<String, Long> requestData) {
        Long appId = requestData.get("id");

        applicationService.rejectApplication(appId);

        return "redirect:/applications";
    }
}
