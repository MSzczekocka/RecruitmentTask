package com.example.demo.service;

import com.example.demo.exceptions.*;
import com.example.demo.model.Client;
import com.example.demo.model.DVD;
import com.example.demo.repositories.ClientRepository;
import com.example.demo.repositories.DVDRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DVDService {

    private final DVDRepository dvdRepository;
    private final ClientRepository clientRepository;

    public List<DVD> getAllDVD() {
        List<DVD> allDVD = dvdRepository.findAll();
        allDVD.sort(Comparator.comparing(DVD::getTitle));
        return allDVD;
    }

    public DVD addDVD(DVD newDVD) {
        try {
            return dvdRepository.save(newDVD);
        } catch (Exception exception) {
            throw new DVDBadRequestException();
        }
    }

    public List<DVD> addAllDVD(List<DVD> allDVD) {
        try {
            return dvdRepository.saveAll(allDVD);
        } catch (Exception exception) {
            throw new DVDBadRequestException();
        }
    }

    public List<DVD> getAllAvailableDVD() {
        Predicate<DVD> byAvailable = dvd -> dvd.getIsAvailable().equals("yes");
        List<DVD> allAvailableDVD = dvdRepository.findAll().stream().filter(byAvailable).collect(Collectors.toList());
        allAvailableDVD.sort(Comparator.comparing(DVD::getTitle));
        return allAvailableDVD;
    }

    public DVD rentDVD(Long dvdId, Long clientId) {
        DVD changedDVD = null;
        Optional<DVD> dvd = dvdRepository.findById(dvdId);

        if (dvd.isEmpty()) {
            throw new DVDNotFoundException();
        } else {
            Optional<Client> client = clientRepository.findById(clientId);
            if (client.isEmpty()) {
                throw new ClientNotFoundexception();
            } else {
                if (dvd.get().getIsAvailable().equals("no")) {
                    throw new DVDNotAvailableException();
                } else {
                    changedDVD = dvd.get();
                    changedDVD.setIsAvailable("no");
                    changedDVD.setRentedBy(client.get().getId());
                    dvdRepository.save(changedDVD);
                }
            }
        }
        return changedDVD;
    }

    public DVD returnDVD(Long dvdId, Long clientId) {
        DVD changedDVD = null;
        Optional<DVD> dvd = dvdRepository.findById(dvdId);

        if (dvd.isEmpty()) {
            throw new DVDNotFoundException();
        } else {
            Optional<Client> client = clientRepository.findById(clientId);
            if (client.isEmpty()) {
                throw new ClientNotFoundexception();
            } else {
                if (dvd.get().getIsAvailable().equals("yes")) {
                    throw new DVDNotRentedException();
                } else {
                    changedDVD = dvd.get();
                    if (!client.get().getId().equals(changedDVD.getRentedBy())){
                        throw new DVDRentedByOtherClientException();
                    } else {
                        changedDVD.setIsAvailable("yes");
                        changedDVD.setRentedBy(0L);
                        dvdRepository.save(changedDVD);
                    }
                }
            }
        }
        return changedDVD;
    }
}
