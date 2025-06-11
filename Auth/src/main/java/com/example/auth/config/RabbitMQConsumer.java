package com.example.auth.config;

import com.example.auth.dto.UserRegistrationStatusDTO;
import com.example.auth.service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RabbitMQConsumer {

    private final UserService userService;

    // Čuvamo status registracije po korisniku (UUID)
    private final Map<UUID, List<UserRegistrationStatusDTO>> userStatusMap = new ConcurrentHashMap<>();

    private final int EXPECTED_SERVICE_COUNT = 3;

    public RabbitMQConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "user-registration-status.auth")
    public void handleStatus(UserRegistrationStatusDTO event) {
        System.out.println("\n--------------STATUS--------------");
        System.out.println("AUTH MS primio status događaj: " + event);
        System.out.println("\n");

        UUID uuid = event.getUserUUID();

        // Dodaj status u mapu
        userStatusMap.putIfAbsent(uuid, new ArrayList<>());
        userStatusMap.get(uuid).add(event);

        // Ako su svi servisi odgovorili
        if (userStatusMap.get(uuid).size() == EXPECTED_SERVICE_COUNT) {
            List<UserRegistrationStatusDTO> statusi = userStatusMap.get(uuid);

            boolean sveUspesno = statusi.stream().allMatch(UserRegistrationStatusDTO::isSuccessStatus);

            if (sveUspesno) {
                System.out.println(" SAGA uspešna za korisnika: " + uuid);
            } else {
                System.out.println(" SAGA neuspešna za korisnika: " + uuid);
                System.out.println("Pokrenut rollback (izvršen od strane servisa)");
                userService.deleteUserByUUID(uuid);
            }

            // Očisti podatke za UUID da ne puni memoriju
            userStatusMap.remove(uuid);
        }
    }
}
