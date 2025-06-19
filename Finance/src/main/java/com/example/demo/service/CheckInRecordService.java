package com.example.demo.service;

import com.example.demo.dto.RecordDTO;
import com.example.demo.dto.WorkingHours;
import com.example.demo.models.CheckInRecord;
import com.example.demo.repository.CheckInRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CheckInRecordService {

    @Autowired
    private CheckInRecordRepository checkInRecordRepository;

    //@Autowired
    //private ReportService reportService;


    public Optional<CheckInRecord> getCheckInRecordById(Integer id){
        return checkInRecordRepository.findById(id);
    }

    public List<CheckInRecord> getAllCheckInRecords(){
        return checkInRecordRepository.findAll();
    }

    public void insert(CheckInRecord checkInRecord){
        checkInRecordRepository.save(checkInRecord);
    }

    public CheckInRecord updateCheckOutTime(CheckInRecord updatedRecord) {
        return checkInRecordRepository.findById(updatedRecord.getCheckInRecordId())
                .map(existingRecord -> {
                    existingRecord.setCheckOutTime(updatedRecord.getCheckOutTime());
                    return checkInRecordRepository.save(existingRecord);
                })
                .orElseThrow(() -> new RuntimeException("Check-in record not found with id: " + updatedRecord.getCheckInRecordId()));
    }

    public List<CheckInRecord> getCheckInRecordsBetweenDates(Date startDate, Date endDate) {
        return checkInRecordRepository.findByCheckInDateBetween(startDate, endDate);
    }



    // Metoda za generisanje Timesheet report-a
    /*public List<WorkingHours> calculateWorkingHours(Date startDate, Date endDate) {
        List<CheckInRecord> checkInRecords = checkInRecordRepository.findByCheckInDateBetween(startDate, endDate);
        List<RecordDTO> response = reportService.getAllRecords();

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

     */

}
