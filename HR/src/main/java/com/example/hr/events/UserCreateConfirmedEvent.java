package com.example.hr.events;

import java.util.UUID;

public class UserCreateConfirmedEvent {
    private UUID uuid;
    public UserCreateConfirmedEvent() {}
    public UserCreateConfirmedEvent(UUID uuid) { this.uuid = uuid; }
    public UUID getUuid() { return uuid; }
    public void setUuid(UUID uuid) { this.uuid = uuid; }
}