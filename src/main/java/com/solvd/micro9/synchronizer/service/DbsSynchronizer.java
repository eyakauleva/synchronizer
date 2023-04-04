//package com.solvd.micro9.synchronizer.service;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import com.google.gson.Gson;
//import com.solvd.micro9.tickets.domain.aggregate.Event;
//import com.solvd.micro9.tickets.domain.aggregate.Ticket;
//import com.solvd.micro9.tickets.domain.es.Es;
//import com.solvd.micro9.tickets.domain.exception.ResourceDoesNotExistException;
//import com.solvd.micro9.tickets.persistence.snapshot.CheckRepo;
//import com.solvd.micro9.tickets.persistence.snapshot.EventRepository;
//import com.solvd.micro9.tickets.persistence.snapshot.TicketRepository;
//import com.solvd.micro9.tickets.service.impl.EsTicketCommandHandlerImpl;
//import lombok.RequiredArgsConstructor;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//import reactor.util.function.Tuple2;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class DbsSynchronizer<T extends Es> {
//
//    private final EventRepository eventRepository;
//    private final TicketRepository ticketRepository;
//    private final CheckRepo checkRepo; //TODO remove
//    private final Gson gson = new Gson();
//    private final ReactiveMongoTemplate reactiveMongoTemplate;
//
//    @SneakyThrows
//    public void sync(T eventStore) {
//        switch (eventStore.getType()) {
//            case EVENT_CREATED: {
//                ObjectMapper mapper = new ObjectMapper();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//                mapper.registerModule(new JavaTimeModule());
//                mapper.setDateFormat(dateFormat);
//                Event event = mapper.readValue(eventStore.getPayload(), Event.class);
//
//                //Event event = gson.fromJson(eventStore.getPayload(), Event.class);
//                event.setId(eventStore.getEntityId());
//                eventRepository.save(event).subscribe();
//                break;
//            }
//            case TICKET_CREATED: {
//                Ticket ticket = gson.fromJson(eventStore.getPayload(), Ticket.class);
//                ticket.setId(eventStore.getEntityId());
//                ticketRepository.save(ticket).subscribe();
//                break;
//            }
//            case TICKET_UPDATED: { //TODO
//                if (EsTicketCommandHandlerImpl.TICKET_USER_ID_TO_NULL_PAYLOAD.equals(eventStore.getPayload())) {
////                    List<Ticket> tickets = new ArrayList<>();
////                    final boolean[] isStreamCompleted = {false};
//                    Ticket ticket1 = new Ticket("4b7bf1cf-4d1b-4023-b4e7-1d014e5ace91", null, "5da3efb1-1575-45ff-85fa-6431756a716b", 1, BigDecimal.valueOf(134.99));
////
////                    ticketRepository.findAll()
////                            .filter(ticket -> eventStore.getEntityId().equals(ticket.getId()))
////                            .doOnNext(ticket -> {
////                                //ticket.setUserId(null);
////                                log.info("res: " + ticket);
////                                tickets.add(new Ticket(
////                                        ticket.getId(),
////                                        null,
////                                        ticket.getEventId(),
////                                        ticket.getQuantity(),
////                                        ticket.getPrice()
////                                ));
////                            })
////                            .doOnComplete(() -> isStreamCompleted[0] = true)
////                            //.flatMap(ticket -> ticketRepository.save(ticket1).subscribe())
////                            .subscribe();
////
////                    while (!isStreamCompleted[0]) {
////                    } //TODO is there a better way to do?
//////
////                    //ticketRepository.save(ticket1).block();
////
////                    ticketRepository.saveAll(tickets).subscribe();
//
//
//                    checkRepo.findById(eventStore.getEntityId())
//                            .map(c -> {
//                                ticket1.setId(c.getId());
//                                ticket1.setEventId(c.getEventId());
//                                return c;
//                                //and so on!
//                            })
//                            .zipWith(ticketRepository.save(ticket1))
//                            .map(res -> {
//                                log.info("res");
//                                log.info("- : " + res.getT2());
//                                return res.getT2();
//                            })
//                            .subscribe();
//
//
////                    return ticketRepository.findById(eventStore.getEntityId())
////                            .flatMap(c -> {
////                                Ticket ticket2 = new Ticket("id", null, "5da3efb1-1575-45ff-85fa-6431756a716b", 3, BigDecimal.valueOf(134.99));
////                                ticket1.setId(c.getId());
////                                ticket1.setEventId(c.getEventId());
////                                return Mono.zip(
////                                        ticketRepository.save(ticket2),
////                                        (savedObject1) -> {
////                                            MyObject combinedObject = new MyObject();
////                                            combinedObject.setName(savedObject1.getName() + " combined");
////                                            combinedObject.setDescription(savedObject1.getDescription() + " and " + savedObject2.getDescription());
////                                            return combinedObject;
////                                        }
////                                );
////                            })
////                            .map(combinedObject -> myObjectRepository.save(combinedObject))
////                            .flatMap(Function.identity());
//
//
//
//                } else {
//                    Ticket ticketChanges = gson.fromJson(eventStore.getPayload(), Ticket.class);
//                    //TODO
//                }
//                break;
//            }
//        }
//    }
//
//    public void sync(List<T> esList) {
//        esList.forEach(this::sync);
//    }
//
//}
