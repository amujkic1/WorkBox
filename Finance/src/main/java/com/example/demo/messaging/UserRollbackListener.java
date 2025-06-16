package com.example.demo.messaging;

import com.example.demo.events.UserCreationFailedEvent;
import com.example.demo.repository.UserRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRollbackListener {

    private final UserRepository userRepository;

    public UserRollbackListener(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RabbitListener(queues = "auth.user.creation.failed.queue")
    @Transactional
    public void handleRollback(UserCreationFailedEvent event) {
        System.out.println("Finance rollback za UUID: " + event.getUuid());
        userRepository.findByUuid(event.getUuid()).ifPresent(user -> {
            userRepository.deleteByUuid(event.getUuid());
            System.out.println("Obrisan korisnik u finance servisu!");
        });
    }
}
