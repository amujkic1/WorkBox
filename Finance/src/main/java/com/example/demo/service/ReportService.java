package com.example.demo.service;

import com.example.demo.dto.RecordDTO;
import com.example.demo.models.CheckInRecord;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final RestTemplate restTemplate;
    private final CheckInRecordRepository checkInRecordRepository;
    private final UserRepository userRepository;

    @Autowired
    public ReportService(RestTemplate restTemplate, CheckInRecordRepository checkInRecordRepository, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.checkInRecordRepository = checkInRecordRepository;
        this.userRepository = userRepository;
    }

    // Sinhroni poziv
    public List<RecordDTO> getAllRecords() {
        String url = "http://hr/records";
        ResponseEntity<CollectionModel<EntityModel<RecordDTO>>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<CollectionModel<EntityModel<RecordDTO>>>() {});

        CollectionModel<EntityModel<RecordDTO>> collection = responseEntity.getBody();

        if (collection != null) {
            List<RecordDTO> recordDTOList = collection.getContent().stream()
                    .map(entityModel -> entityModel.getContent())
                    .collect(Collectors.toList());

            return recordDTOList;
        }

        return Collections.emptyList();
    }





}
