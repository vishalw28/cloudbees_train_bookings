package com.cloudbees;

import com.cloudbees.model.Bookings;
import com.cloudbees.model.Coach;
import com.cloudbees.model.CoachType;
import com.cloudbees.model.Station;
import com.cloudbees.model.Train;
import com.cloudbees.model.TrainFare;
import com.cloudbees.model.User;
import com.cloudbees.repository.BookingsRepository;
import com.cloudbees.repository.CoachRepository;
import com.cloudbees.repository.StationRepository;
import com.cloudbees.repository.TrainFareRepository;
import com.cloudbees.repository.TrainRepository;
import com.cloudbees.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableJpaRepositories
public class TrainBookingApplication {

    public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TrainBookingApplication.class, args);
		Object dataSource  = context.getBean("dataSource");
		System.out.println(dataSource);

		/*Bks b1 = new Bks(1, 4);
		Bks b2 = new Bks(1, 2);

		Bks b3 = new Bks(2, 2);
		Bks b4 = new Bks(1, 3);



		List<Bks> list = Arrays.asList(b1,b2,b3,b4);
		Map<Integer, Long> map = list.stream().collect(Collectors.groupingBy(Bks::coachNo,Collectors.counting()));



		Bks1 b11 = new Bks1(1, 4);
		Bks1 b12 = new Bks1(1, 2);
		Bks1 b13 = new Bks1(1, 5);
		Bks1 b14 = new Bks1(2, 5);


		List<Bks1> list1 = Arrays.asList(b11,b12,b13,b14);

		Map<Integer, Long> map1 = list1.stream().collect(Collectors.groupingBy(v1-> v1.getCoachNo(),Collectors.counting()));
			//Collectors.toMap(Bks::coachNo,Bks::sNo, Collectors.counting()));
		System.out.println(map1);*/

		/*int cSize = 4;
		int aSeats = 1;
		List<Integer> sSet = Arrays.asList(1,2);
		int i=0;
		Optional<Integer> nextSeat = IntStream.range(1,cSize).boxed().filter(e ->
			!sSet.contains(e)).findFirst();
		System.out.println("Available Seat = " + nextSeat.get());*/
    }

	@Bean
	CommandLineRunner commandLineRunner(CoachRepository coachRepository, TrainRepository trainRepository,
										UserRepository userRepository, StationRepository stationRepository,
										TrainFareRepository trainFareRepository, BookingsRepository bookingsRepository){

		//Assumptions
		return args -> {
			//Only 3 stations, where Udyan is start from Bangalore & goes to Mumbai via Pune.
			stationRepository.save(new Station("SBC", "Bangalore"));
			stationRepository.save(new Station("MUM", "Mumbai"));
			stationRepository.save(new Station("PNE", "Pune"));

			//coachRepository.save(new Coach("C1",CoachType.AC.ordinal(), 2));
			//coachRepository.save(new Coach("C2",CoachType.NON_AC.ordinal(), 4));

			trainFareRepository.save(new TrainFare(10112, "SBC", "PNE", CoachType.NON_AC.ordinal(),500));
			trainFareRepository.save(new TrainFare(10112,"SBC", "PNE", CoachType.AC.ordinal(),1000));
			trainFareRepository.save(new TrainFare(10112, "SBC", "MUM", CoachType.NON_AC.ordinal(),700));
			trainFareRepository.save(new TrainFare(10112,"SBC", "MUM", CoachType.AC.ordinal(),1200));


			Set<Coach> coaches = new HashSet<>();
			coaches.add(new Coach("C1", CoachType.NON_AC.ordinal(),4));
			coaches.add(new Coach("C2", CoachType.AC.ordinal(),2));
			trainRepository.save(new Train(10112, "Udyan Express",coaches ));



			userRepository.save(new User(1,"Vishal", "vishal123@gmail.com"));
			userRepository.save(new User(2,"John", "john123@gmail.com"));
			userRepository.save(new User(3,"Manjunath", "drManju123@gmail.com"));

			bookingsRepository.save(new Bookings(1, LocalDateTime.now().plusDays(1l),LocalDateTime.now(),null,1,10112,"SBC", "MUM",1500,0,"C2",1));

		};
	}

}
