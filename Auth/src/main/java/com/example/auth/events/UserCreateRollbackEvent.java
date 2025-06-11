package com.example.auth.events;

import java.util.UUID;

public class UserCreateRollbackEvent {
    private UUID uuid;
    public UserCreateRollbackEvent() {}
    public UserCreateRollbackEvent(UUID uuid) { this.uuid = uuid; }
    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }
}