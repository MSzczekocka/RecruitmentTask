package com.example.demo.controller;

import com.example.demo.model.Client;
import com.example.demo.model.DVD;
import com.example.demo.service.ClientService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/api/register")
    public ResponseEntity<Client> register(@RequestBody @Valid Client newClient) {
        clientService.addClient(newClient);
        return new ResponseEntity<>(newClient, HttpStatus.OK);
    }

    @PostMapping("/client/upload")
    public ResponseEntity<List<Client>> uploadData(@RequestParam("file") MultipartFile file) throws Exception {
        List<Client> clientList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Client newClient = new Client();
            newClient.setFirstName(record.getString("firstName"));
            newClient.setLastName(record.getString("lastName"));
            newClient.setDateOfBirth(record.getString("dateOfBirth"));
            clientList.add(newClient);
        });
        clientService.addAll(clientList);

        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping("/client/all")
    public ResponseEntity<List<Client>> getAllDVD() {
        List<Client> allClients = clientService.getAllClients();
        return new ResponseEntity<>(allClients, HttpStatus.OK);
    }





}
