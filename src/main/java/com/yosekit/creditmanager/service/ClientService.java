package com.yosekit.creditmanager.service;

import com.yosekit.creditmanager.model.Address;
import com.yosekit.creditmanager.model.Client;
import com.yosekit.creditmanager.model.Employment;
import com.yosekit.creditmanager.model.Passport;
import com.yosekit.creditmanager.repository.ClientRepository;
import com.yosekit.creditmanager.request.CreateClientRequest;
import com.yosekit.creditmanager.viewmodel.ClientViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private List<ClientViewModel> mapList(List<Client> clients) {
        return clients.stream().map(client -> new ClientViewModel(
                client.getId(),
                client.getPassport(),
                client.getFullName(),
                client.getBirthdate(),
                client.getPhone(),
                client.getFamilyStatus(),
                client.getEmployment()
        )).toList();
    }

    public List<ClientViewModel> getAllClients() {
        return mapList(clientRepository.findAll());
    }

    public List<ClientViewModel> searchClients(String fullName, String passport, String phone) {
        var criteria = new HashMap<String, Object>();

        if (fullName != null) {
            criteria.put("fullName", fullName);
        }
        if (passport != null) {
            String[] passportValues = passport.split("\\s+");

            criteria.put("passport.series", passportValues[0]);
            criteria.put("passport.number", passportValues[1]);
        }
        if (phone != null) {
            criteria.put("phone", phone);
        }

        return mapList(clientRepository.findByColumns(criteria));
    }

    public Client createClient(CreateClientRequest clientRequest) {
        var client = new Client(
                clientRequest.getFullName(),
                clientRequest.getBirthdate(),
                clientRequest.getPhone(),
                Client.FamilyStatus.valueOf(
                        clientRequest.getFamilyStatus().toUpperCase())
        );

        var employment = new Employment(
                clientRequest.getEmployment().getPost(),
                clientRequest.getEmployment().getCompanyName(),
                clientRequest.getEmployment().getSalary(),
                clientRequest.getEmployment().getWorkPeriod()
        );

        client.setEmployment(employment);
        employment.setClient(client);

        var passport = new Passport(
                clientRequest.getPassport().getSeries(),
                clientRequest.getPassport().getNumber(),
                clientRequest.getPassport().getIssuanceDate(),
                clientRequest.getPassport().getSubdivisionCode(),
                clientRequest.getPassport().getIssued()
        );

        client.setPassport(passport);
        passport.setClient(client);

        var registrationAddress = new Address(
                clientRequest.getRegistrationAddress(),
                Address.AddressType.REGISTRATION);

        var residenceAddress = new Address(
                clientRequest.getResidenceAddress(),
                Address.AddressType.RESIDENCE);

        client.addAddress(registrationAddress);
        registrationAddress.setClient(client);

        client.addAddress(residenceAddress);
        residenceAddress.setClient(client);

        clientRepository.save(client);

        return client;
    }
}
