package com.example.business.messaging;

import com.example.business.events.UserCreationFailedEvent;
import com.example.business.repository.UserRepository;
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
        System.out.println("Business rollback za UUID: " + event.getUuid());
        userRepository.findByUuid(event.getUuid()).ifPresent(user -> {
            userRepository.deleteByUuid(event.getUuid());
            System.out.println("Obrisan korisnik u business servisu!");
        });
    }
}
