package com.yosekit.creditmanager.controller;

import com.yosekit.creditmanager.service.ClientService;
import com.yosekit.creditmanager.viewmodel.ClientViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String clients(Model model) {
        var clientViewModels = clientService.getAllClients();

        model
                .addAttribute("title", "Clients Page")
                .addAttribute("clientViewModels", clientViewModels);
        return "clients";
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClientViewModel>> search(
            @RequestParam(required = false) String fullName,
            @RequestParam(required = false) String passport,
            @RequestParam(required = false) String phone) {

        var clientViewModels = clientService.searchClients(fullName, passport, phone);

        return new ResponseEntity<>(clientViewModels, HttpStatus.OK);
    }
}
