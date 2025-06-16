package com.example.business.events;

import java.io.Serializable;
import java.util.UUID;

public class UserCreationFailedEvent implements Serializable {
    private UUID uuid;
    private String reason;

    public UserCreationFailedEvent() {}

    public UserCreationFailedEvent(UUID uuid, String reason) {
        this.uuid = uuid;
        this.reason = reason;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
