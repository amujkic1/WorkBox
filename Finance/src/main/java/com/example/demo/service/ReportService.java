package com.example.demo.service;

import com.example.demo.dto.EmployeeStatus;
import com.example.demo.dto.RecordDTO;
import com.example.demo.models.EmployeeBenefit;
import com.example.demo.models.User;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.EmployeeBenefitRepository;
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
    private final EmployeeBenefitService employeeBenefitService;
    private final UserService userService;
    private final EmployeeBenefitRepository employeeBenefitRepository;

    @Autowired
    public ReportService(RestTemplate restTemplate, CheckInRecordRepository checkInRecordRepository, UserRepository userRepository, EmployeeBenefitService employeeBenefitService, UserService userService, EmployeeBenefitRepository employeeBenefitRepository) {
        this.restTemplate = restTemplate;
        this.checkInRecordRepository = checkInRecordRepository;
        this.userRepository = userRepository;
        this.employeeBenefitService = employeeBenefitService;
        this.userService = userService;
        this.employeeBenefitRepository = employeeBenefitRepository;
    }

    // Sinhroni poziv za dohvatanje broja radnih sati
    public List<RecordDTO> getAllRecords() {
        String url = "http://api-gateway:8080/hr/records";
        //String url = "http://hr/records";
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



    // Metoda za generisanje Employee status report
    public List<EmployeeStatus> generateEmployeeStatusReport() {
        Map<Integer, EmployeeStatus> employeeStatusMap = new HashMap<>();

        List<User> users = userService.getAllUsers();
        List<RecordDTO> records = getAllRecords();


        for (User user : users) {
            Integer userId = user.getUserId();
            UUID targetUUID = user.getUserUUID();

            List<EmployeeBenefit> benefits = employeeBenefitRepository.findByUser_UserId(userId);
            RecordDTO userRecord = records.stream()
                    .filter(record -> targetUUID.equals(record.getUserUuid())) // Ispravljeno poređenje
                    .findFirst()
                    .orElse(new RecordDTO());

            // Kreiraj EmployeeStatus objekat
            EmployeeStatus status = new EmployeeStatus(
                    userId,
                    user.getFirstName(),
                    user.getLastName(),
                    userRecord.getStatus(),
                    userRecord.getEmploymentDate(),
                    userRecord.getWorkingHours(),
                    benefits
            );

            // Stavi ga u mapu (userId kao ključ)
            employeeStatusMap.put(userId, status);
        }

        // Vrati listu svih statusa
        return new ArrayList<>(employeeStatusMap.values());
    }



}
