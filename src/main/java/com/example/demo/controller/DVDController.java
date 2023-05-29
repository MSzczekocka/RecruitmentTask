package com.example.demo.controller;


import com.example.demo.model.DVD;
import com.example.demo.service.DVDService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dvd")
public class DVDController {

    private final DVDService dvdService;

    @GetMapping("/all")
    public ResponseEntity<List<DVD>> getAllDVD() {
        List<DVD> allDVD = dvdService.getAllDVD();
        return new ResponseEntity<>(allDVD, HttpStatus.OK);
    }

    @GetMapping("/all/available")
    public ResponseEntity<List<DVD>> getAllAvailableDVD() {
        List<DVD> allDVD = dvdService.getAllAvailableDVD();
        return new ResponseEntity<>(allDVD, HttpStatus.OK);
    }

    @PostMapping("/upload")
    public ResponseEntity<List<DVD>> uploadData(@RequestParam("file")MultipartFile file) throws Exception {
        List<DVD> dvdList = new ArrayList<>();
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            DVD newDVD = new DVD();
            newDVD.setTitle(record.getString("title"));
            newDVD.setGenre(record.getString("genre"));
            newDVD.setReleaseDate(record.getString("releaseDate"));
            newDVD.setPrice(Double.parseDouble(record.getString("price")));
            newDVD.setIsAvailable(record.getString("isAvailable"));
            newDVD.setRentedBy(Long.parseLong(record.getString("rentedBy")));
            dvdList.add(newDVD);
        });
        dvdService.addAllDVD(dvdList);

        return new ResponseEntity<>(dvdList, HttpStatus.OK);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public HttpStatus rentDVD(@PathVariable final Long id, Long clientId) {
        dvdService.rentDVD(id, clientId);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping(value = "/return/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public HttpStatus returnADVD(@PathVariable final Long id, Long clientId) {
        dvdService.returnDVD(id, clientId);
        return HttpStatus.NO_CONTENT;
    }


}
