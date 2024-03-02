#Business Requirement
I want to board a train from London to France. The train ticket will cost $20.
Create API where you can submit a purchase for a ticket. Details included in the receipt are:
From, To, User , price paid.
User should include first and last name, email address
The user is allocated a seat in the train. Assume the train has only 2 sections, section A and section B.
An API that shows the details of the receipt for the user
An API that lets you view the users and seat they are allocated by the requested section
An API to remove a user from the train
An API to modify a user's seat

#What's achieved
1. Able to book the ticket if seats are available
2. Able to concel the confirm ticket
3. Ticket will not confimed if seats are not available
4. Get the ticket details based on the PNR 

#StaticData

//Only 3 stations are added so far
stationRepository.save(new Station("SBC", "Bangalore"));
			stationRepository.save(new Station("MUM", "Mumbai"));
			stationRepository.save(new Station("PNE", "Pune"));

//Fares from src to dest
			trainFareRepository.save(new TrainFare(10112, "SBC", "PNE", CoachType.NON_AC.ordinal(),500));
			trainFareRepository.save(new TrainFare(10112,"SBC", "PNE", CoachType.AC.ordinal(),1000));
			trainFareRepository.save(new TrainFare(10112, "SBC", "MUM", CoachType.NON_AC.ordinal(),700));
			trainFareRepository.save(new TrainFare(10112,"SBC", "MUM", CoachType.AC.ordinal(),1200));

//Available coaches for specific train i.e. 10112
			Set<Coach> coaches = new HashSet<>();
			coaches.add(new Coach("C1", CoachType.NON_AC.ordinal(),4));
			coaches.add(new Coach("C2", CoachType.AC.ordinal(),2));
			trainRepository.save(new Train(10112, "Udyan Express",coaches ));


//Users available currently
			userRepository.save(new User(1,"Vishal", "vishal123@gmail.com"));
			userRepository.save(new User(2,"John", "john123@gmail.com"));
			userRepository.save(new User(3,"Manjunath", "drManju123@gmail.com"));
   
//One sample booking is already present in the DB where pnr will be PNR_1
			bookingsRepository.save(new Bookings(1, LocalDateTime.now().plusDays(1l),LocalDateTime.now(),null,1,10112,"SBC", "MUM",1500,0,"C2",1));


   
#Scope for enhancement
1. Parallel transaction is something we can achieve using synchronization
2. One user can book a ticket for N passengers
3. Controllers test got stuck due to junit configuration...
4. Can add more UT's & IT's
5. User can book the ticket with waiting states
   
