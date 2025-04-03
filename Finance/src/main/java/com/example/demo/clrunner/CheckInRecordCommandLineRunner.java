package com.example.demo.clrunner;

import com.example.demo.controller.CheckInRecordController;
import com.example.demo.models.CheckInRecord;
import com.example.demo.models.User;
import com.example.demo.repository.CheckInRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CheckInRecordService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;

@Component
@Order(2)
public class CheckInRecordCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CheckInRecordService checkInRecordService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User user = userService.getAllUsers().getFirst();
        CheckInRecord checkInRecord = new CheckInRecord(new Date(), new Time(123), new Time(123), user);
        checkInRecordService.insert(checkInRecord);

    }
}
