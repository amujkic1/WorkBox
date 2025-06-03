package com.example.business.grpc;

import com.example.systemeventsserver.grpc.EventLoggerGrpc;
import com.example.systemeventsserver.grpc.EventRequest;
import com.example.systemeventsserver.grpc.EventResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class SystemEventsClient {
    private final EventLoggerGrpc.EventLoggerBlockingStub stub;

    public SystemEventsClient() {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 6565)
                .usePlaintext()
                .build();

        this.stub = EventLoggerGrpc.newBlockingStub(channel);
    }

    public void log(String user, String action, String resource, String responseCode) {
        EventRequest request = EventRequest.newBuilder()
                .setTimestamp(java.time.Instant.now().toString())
                .setMicroserviceName("business-service")
                .setUser(user)
                .setActionType(action)
                .setResourceName(resource)
                .setResponseType(responseCode)
                .build();

        EventResponse response = stub.logEvent(request);
        System.out.println("Logged: " + response.getSuccess());
    }
}
