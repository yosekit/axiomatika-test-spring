package com.yosekit.creditmanager.controller;

import com.yosekit.creditmanager.service.ContractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/contracts")
public class ContractController {

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

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
    public String sign(@RequestParam Long contractId) {
        contractService.signContract(contractId);

        return "redirect:/contracts";
    }
}
