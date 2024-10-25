package com.yosekit.creditmanager.controller;

import com.yosekit.creditmanager.service.ApplicationService;
import com.yosekit.creditmanager.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    @Autowired
    private ContractService contractService;

    @GetMapping
    public String contracts(Model model) {
        var contractViewModels = contractService.getAllContracts();

        model
                .addAttribute("title", "Contracts Page")
                .addAttribute("contractViewModels", contractViewModels);
        return "contracts";
    }

    @PostMapping("/sign")
    public String sign(@RequestBody Map<String, Long> requestData) {
        Long id = requestData.get("id");

        contractService.signContract(id);

        return "redirect:/contracts";
    }
}
