package com.example.auth.messaging;

import com.example.auth.events.UserCreationFailedEvent;
import com.example.auth.repository.UserRepository;
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
        System.out.println("Rollback received for user: " + event.getUuid());

        var userOptional = userRepository.findByUuid(event.getUuid());

        if (userOptional.isPresent()) {
            System.out.println("Korisnik pronađen: " + userOptional.get().getEmail() + " uuid: " + event.getUuid());
            userRepository.deleteByUuid(event.getUuid());
            System.out.println("Korisnik obrisan!");
        } else {
            System.out.println("Korisnik sa UUID " + event.getUuid() + " nije pronađen u bazi!");
        }
    }
}