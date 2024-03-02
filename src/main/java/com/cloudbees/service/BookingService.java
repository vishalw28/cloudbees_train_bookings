package com.cloudbees.service;

import com.cloudbees.exception.BookingException;
import com.cloudbees.model.BookingRequest;
import com.cloudbees.model.BookingResponse;
import com.cloudbees.model.BookingStatus;
import com.cloudbees.model.Bookings;
import com.cloudbees.model.Coach;
import com.cloudbees.model.CoachType;
import com.cloudbees.model.Train;
import com.cloudbees.model.TrainFare;
import com.cloudbees.model.User;
import com.cloudbees.repository.BookingsRepository;
import com.cloudbees.repository.CoachRepository;
import com.cloudbees.repository.TrainFareRepository;
import com.cloudbees.repository.TrainRepository;
import com.cloudbees.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainFareRepository trainFareRepository;

    @Autowired
    private CoachRepository coachRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private UserRepository userRepository;

//    Predicate<Train> coachPredicate = train -> train.


    public BookingResponse book(BookingRequest bookingRequest) {
        //Do validation

        Optional<User> userOptional = userRepository.findById(bookingRequest.userId());
        if (userOptional.isEmpty()) {
            throw new BookingException(String.format("User: {} does not exists", bookingRequest.userId()));
        }
        Optional<Train> trainOptional = trainRepository.findById(bookingRequest.trainNo());
        if (!trainOptional.isPresent()) {
            throw new BookingException(String.format("Provided Train Number: {} does not exists", bookingRequest.trainNo()));
        }


        List<Bookings> bookings = bookingsRepository.findByTrainNoAndJourneyDateTimeAndBookingStatus(
            bookingRequest.trainNo(), bookingRequest.journeyDateTime(), BookingStatus.CONFIRMED.ordinal());

        Map<String, Long> coachwiseSeatCount = bookings.stream().collect(Collectors.groupingBy(bookings1 -> bookings1.getCoachNo(), Collectors.counting()));


        Optional<Coach> availableCoaches = trainOptional.get().getCoaches().stream()
            .filter(coach -> CoachType.valueOf(bookingRequest.coachType()) == CoachType.values()[coach.getType()])
            .filter(coach -> !coachwiseSeatCount.containsKey(coach.getCoachNo()) || coachwiseSeatCount.get(coach.getCoachNo()) < coach.getSeats())
            .findFirst();

        Bookings.BookingsBuilder builder = Bookings.builder()
            .src(bookingRequest.src())
            .dest(bookingRequest.dest())
            .journeyDateTime(bookingRequest.journeyDateTime())
            .trainNo(bookingRequest.trainNo());
        if (availableCoaches.isPresent()) {
            //Allocate the non-assigned seat number
            log.info("Seats are available");
            Optional<TrainFare> fare = trainFareRepository.findByTrainNoAndSrcAndDestAndCoachType(bookingRequest.trainNo(), bookingRequest.src(), bookingRequest.dest(), CoachType.valueOf(bookingRequest.coachType()).ordinal());

            Coach allocatedCoach = availableCoaches.get();
            builder.coachNo(allocatedCoach.getCoachNo())
                .amount(fare.get().getFare())
                .bookingStatus(BookingStatus.CONFIRMED.ordinal());
            //Few seats are available
            Set<Integer> sSeatNos = bookings.stream().filter(bookings1 -> bookings1.getCoachNo().equals(allocatedCoach.getCoachNo()))
                .map(bookings1 -> bookings1.getSeatNo()).collect(Collectors.toSet());
            Optional<Integer> nextSeat = IntStream.range(1, allocatedCoach.getSeats() + 1).boxed().filter(e ->
                !sSeatNos.contains(e)).findFirst();
            builder.seatNo(nextSeat.get());
        } else {
            builder.bookingStatus(BookingStatus.NOT_CONFIRMED.ordinal());
            log.info("Seats are not available");
        }
        Bookings ticket = bookingsRepository.save(builder.build());
        return bookingResponse(userOptional.get(), ticket);
    }

    public BookingResponse cancel(String pnr) {
        String[] ids = pnr.split("_");
        Integer bookingId = null;
        if (ids.length != 2 || (bookingId = Integer.valueOf(ids[1])) == null) {
            throw new BookingException(String.format("Invalid PNR: {}", pnr));
        }
        Optional<Bookings> bookings = bookingsRepository.findById(bookingId);
        if (bookings.isPresent()) {
            Optional<User> user = userRepository.findById(bookings.get().getUserId());
            if (bookings.get().getBookingStatus() == BookingStatus.CANCELLED.ordinal()) {
//                bookings.get().
                return bookingResponse(user.get(), bookings.get(), " Already cancelled...");
            }
            bookings.get().setBookingStatus(BookingStatus.CANCELLED.ordinal());
            bookings.get().setModifiedDateTime(LocalDateTime.now());

            Bookings updated = bookingsRepository.save(bookings.get());
            return bookingResponse(user.get(), updated);
        } else {
            throw new BookingException(String.format("PNR : {} does not exists. Enter valid PNR number", pnr));
        }
    }

    private BookingResponse bookingResponse(User user, Bookings ticket) {
        return bookingResponse(user, ticket, null);
    }

    private BookingResponse bookingResponse(User user, Bookings ticket, String msg) {
        String status = msg == null ?
            BookingStatus.values()[ticket.getBookingStatus()].toString() :
            BookingStatus.values()[ticket.getBookingStatus()].toString() + ": " + msg;
        return new BookingResponse("PNR_" + ticket.getId(), user, ticket.getCoachNo(), ticket.getSeatNo(),
            status,
            ticket.getJourneyDateTime(),
            ticket.getBookingDateTime(),
            ticket.getModifiedDateTime());
    }

    public BookingResponse bookingDetails(String pnr) {
        String[] ids = pnr.split("_");
        Integer bookingId = null;
        if (ids.length != 2 || (bookingId = Integer.valueOf(ids[1])) == null) {
            throw new BookingException(String.format("Invalid PNR: {}", pnr));
        }
        Optional<Bookings> bookings = bookingsRepository.findById(bookingId);
        if (bookings.isPresent()) {
            Optional<User> user = userRepository.findById(bookings.get().getUserId());
            Bookings updated = bookingsRepository.save(bookings.get());
            return bookingResponse(user.get(), updated);
        } else {
            throw new BookingException(String.format("PNR : {} does not exists. Enter valid PNR number", pnr));
        }
    }
}
