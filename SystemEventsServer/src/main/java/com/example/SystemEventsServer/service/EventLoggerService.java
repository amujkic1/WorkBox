package com.example.SystemEventsServer.service;

import com.example.systemeventsserver.grpc.EventLoggerGrpc;
import com.example.SystemEventsServer.model.Event;
import com.example.SystemEventsServer.repository.EventRepository;
import com.example.systemeventsserver.grpc.EventResponse;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

@GRpcService
public class EventLoggerService extends EventLoggerGrpc.EventLoggerImplBase {

    private final EventRepository eventRepository;

    public EventLoggerService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void logEvent(com.example.systemeventsserver.grpc.EventRequest request, StreamObserver<EventResponse> responseObserver) {
        Event event = new Event(
                request.getTimestamp(),
                request.getMicroserviceName(),
                request.getUser(),
                request.getActionType(),
                request.getResourceName(),
                request.getResponseType()
        );


        eventRepository.save(event);

        EventResponse response = EventResponse.newBuilder()
                .setSuccess(true)
                .setMessage("Event logged successfully.")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
