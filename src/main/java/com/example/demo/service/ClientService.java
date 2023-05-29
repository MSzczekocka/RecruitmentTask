package com.example.demo.service;


import com.example.demo.exceptions.ClientBadRequestException;
import com.example.demo.exceptions.ClientInDBException;
import com.example.demo.exceptions.DVDBadRequestException;
import com.example.demo.model.Client;
import com.example.demo.model.DVD;
import com.example.demo.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> getAllClients() {
        List<Client> allClients = clientRepository.findAll();
        allClients.sort(Comparator.comparing(Client::getFirstName));
        return allClients;
    }

    public Client addClient(Client newClient) {
        List<Client> allClients = clientRepository.findAll();
        boolean isInDB = false;

        for (Client client : allClients) {
            if (newClient.getFirstName().equals(client.getFirstName()) && newClient.getLastName().equals(client.getLastName())
                    && newClient.getDateOfBirth().equals(client.getDateOfBirth())){
                isInDB = true;
            }
        }

        if (isInDB) {
            throw new ClientInDBException();
        } else {
            try {
                return clientRepository.save(newClient);
            } catch (Exception exception) {
                throw new ClientBadRequestException();
            }
        }

    }

    public List<Client> addAll(List<Client> clientList) {
        try {
            return clientRepository.saveAll(clientList);
        } catch (Exception exception) {
            throw new DVDBadRequestException();
        }
    }
}
