package com.example.hr.events;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class UserCreationFailedEvent implements Serializable {
    private UUID uuid;
    private String reason;

    public UserCreationFailedEvent() {}

    public UserCreationFailedEvent(UUID uuid, String reason) {
        this.uuid = uuid;
        this.reason = reason;
    }
}
