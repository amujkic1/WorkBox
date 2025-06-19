package com.example.demo.service;

import com.example.demo.dto.EmployeeStatus;
import com.example.demo.dto.PayrollDTO;
import com.example.demo.dto.RecordDTO;
import com.example.demo.dto.WorkingHours;
import com.example.demo.models.CheckInRecord;
import com.example.demo.models.EmployeeBenefit;
import com.example.demo.models.User;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.EmployeeBenefitRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utility.SalaryCalculator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
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
    private HttpServletRequest request;

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


    // Sinhroni poziv
    public List<User> getAllUsersFromAuthService() {
        String url = "http://api-gateway:8080/auth/users";

        // Dohvatanje tokena iz Authorization headera
        String jwtToken = null;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        HttpHeaders headers = new HttpHeaders();
        if (jwtToken != null && !jwtToken.isBlank()) {
            headers.set("Authorization", "Bearer " + jwtToken);
        }


        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<User[]> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                User[].class
        );

        User[] users = responseEntity.getBody();
        return users != null ? Arrays.asList(users) : Collections.emptyList();
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
                    userRecord.getUserUuid(),
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


    // Metoda za generisanje Employee payroll

    public List<PayrollDTO> generateEmployeesPayroll(Date startDate, Date endDate){
        List<PayrollDTO> payrollList = new ArrayList<>();
        SalaryCalculator salaryCalculator = new SalaryCalculator();
        Integer numberOfDaysBetweenDates = (int) ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());


        // Dohvatanje svih korisnika iz Auth servisa
        List<User> users = getAllUsersFromAuthService();
        List<EmployeeStatus> employeeStatusList = generateEmployeeStatusReport();
        List<WorkingHours> timesheetList = calculateWorkingHours(startDate, endDate);


        // Obračun plate za svakog usera
        for (User user : users) {

            EmployeeStatus userStatus = employeeStatusList.stream()
                    .filter(employeeStatus -> employeeStatus.getUuid().equals(user.getUserUUID()))
                    .findFirst()
                    .orElse(new EmployeeStatus());

            if (userStatus.getUuid() == null) {
                continue;
            }

            WorkingHours userWorkingHours = timesheetList.stream()
                    .filter(workingHours -> workingHours.getUuid().equals(user.getUserUUID()))
                    .findFirst()
                    .orElse(null);

            if (userWorkingHours == null) {
                continue;
            }


            PayrollDTO payrollDTO = new PayrollDTO();
            payrollDTO.setId(user.getUserId());
            payrollDTO.setUuid(user.getUserUUID());
            payrollDTO.setFirstName(user.getFirstName());
            payrollDTO.setLastName(user.getLastName());

            payrollDTO.setStatus(userStatus.getStatus());
            payrollDTO.setWorkingHours(userStatus.getNumberOfWorkingHours());
            payrollDTO.setBenefits(userStatus.getEmployeeBenefits());

            payrollDTO.setTotalWorkingHours(userWorkingHours.getTotalWorkingHours());
            payrollDTO.setTotalOvertimeHours(userWorkingHours.getTotalOvertimeHours());


            boolean[] benefitsTMP = new boolean[4];

            if (userStatus.getEmployeeBenefits() != null) {
                for (EmployeeBenefit benefit : userStatus.getEmployeeBenefits()) {
                    if ("Bonus".equalsIgnoreCase(benefit.getType())){
                        benefitsTMP[0] = true;
                    }

                    if ("Transport".equalsIgnoreCase(benefit.getType())){
                        benefitsTMP[1] = true;
                    }

                    if ("Meal".equalsIgnoreCase(benefit.getType())){
                        benefitsTMP[2] = true;
                    }

                    if ("Other".equalsIgnoreCase(benefit.getType())){
                        benefitsTMP[3] = true;
                    }
                }

            }

            payrollDTO.setSalary(salaryCalculator.calculateSalary(user.getHourlyRate(),
                    numberOfDaysBetweenDates,
                    userWorkingHours.getTotalWorkingHours(),
                    userWorkingHours.getTotalOvertimeHours(),
                    benefitsTMP[0],
                    benefitsTMP[1],
                    benefitsTMP[2],
                    benefitsTMP[3]));

            payrollList.add(payrollDTO);
        }

        return payrollList;
    }




    // Metoda za generisanje Timesheet report-a
    public List<WorkingHours> calculateWorkingHours(Date startDate, Date endDate) {
        List<CheckInRecord> checkInRecords = checkInRecordRepository.findByCheckInDateBetween(startDate, endDate);
        List<RecordDTO> response = getAllRecords();

        Map<Integer, WorkingHours> workingHoursMap = new HashMap<>();


        for (CheckInRecord record : checkInRecords) {
            Duration duration = Duration.between(record.getCheckInTime().toLocalTime(), record.getCheckOutTime().toLocalTime());
            Integer hoursWorked = (int) duration.toHours();
            Integer userId = record.getUser().getUserId();
            UUID userUUID = record.getUser().getUserUUID();


            if (workingHoursMap.containsKey(userId)) {
                WorkingHours existing = workingHoursMap.get(userId);
                existing.setTotalWorkingHours(existing.getTotalWorkingHours() + hoursWorked);
            } else {
                workingHoursMap.put(userId, new WorkingHours(userId,
                        userUUID,
                        record.getUser().getFirstName(),
                        record.getUser().getLastName(),
                        hoursWorked));
            }
        }


        // Izračunavanje prekovremenih sati
        Integer numberOfDaysBetweenDates = (int) ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
        //System.out.println("\n \n Broj dana je:");
        //System.out.println(numberOfDaysBetweenDates);

        for (WorkingHours workingHours : workingHoursMap.values()) {

            // Ovdje izmjeniti kod da radi sa UUID - regularni broj sati se treba dobiti prema UUID
            //System.out.println("Za radnika sa ID:"+workingHours.getUserId());
            //.filter(record -> record.getId().equals(workingHours.getUserId()))
            Integer regularUserWorkingHours  = response.stream()
                    .filter(record -> record.getUserUuid().equals(workingHours.getUuid()))
                    .map(RecordDTO::getWorkingHours)
                    .findFirst()
                    .orElse(null);
            Integer overtimeHours = Math.max(0, workingHours.getTotalWorkingHours() - (regularUserWorkingHours * numberOfDaysBetweenDates));
            //System.out.println("RegularUserWorkingHours:"+regularUserWorkingHours);
            //System.out.println("Number of days:"+numberOfDaysBetweenDates);
            //System.out.println("Razlika:"+(workingHours.getTotalWorkingHours() - (regularUserWorkingHours * numberOfDaysBetweenDates)));
            //System.out.println("\n");
            workingHours.setTotalOvertimeHours(overtimeHours);
        }

        return new ArrayList<WorkingHours>(workingHoursMap.values());
    }



}
