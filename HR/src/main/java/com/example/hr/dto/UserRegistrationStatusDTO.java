package com.example.hr.dto;

import java.util.UUID;

public class UserRegistrationStatusDTO {
    private UUID userUUID;
    private String service;
    private boolean successStatus;

    public UserRegistrationStatusDTO(UUID userUUID, String service, boolean successStatus) {
        this.userUUID = userUUID;
        this.service = service;
        this.successStatus = successStatus;
    }


    public UUID getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public boolean isSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(boolean successStatus) {
        this.successStatus = successStatus;
    }


    @Override
    public String toString() {
        return "UserRegistrationStatusDTO{" +
                "userUUID=" + userUUID +
                ", service='" + service + '\'' +
                ", successStatus=" + successStatus +
                '}';
    }
}
