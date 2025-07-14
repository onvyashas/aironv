# Airline Management System <img src="https://custom-doodle.com/wp-content/uploads/doodle/pusheen-the-cat-flying-the-plane/pusheen-the-cat-flying-the-plane-doodle.gif" alt="Get in Touch Gif" width="50" height="50">

## Project Overview

The Airline Management System is a backend application designed to efficiently manage airline operations, including airplanes, flights, and passenger reservations. Developed using Spring Boot, the application exposes RESTful API endpoints to handle key functionalities such as flight management, airplane assignments, and reservation handling.

## Key Features

- **Airplane & Flight Management**: Seamlessly manage airplanes, flights, and associated reservations.
- **Relational Data Handling**:
  - Airplanes can be assigned by multiple flights.
  - Each flight is associated with an airplane.
  - Reservations are linked to specific flights.
- **Flight Search & Filtering**: Users can filter flights by departure location, arrival location, and date range.
- **Occupancy & Availability Checks**: Prevent reservations on fully booked flights and verify airplane availability before flight assignment.
- **Unique Reservation Codes**: Automatically generate unique reservation codes that remain unchanged even after updates.
- **Email Notifications**: Automated email notifications for reservation creation, flight updates, and email address changes.
- **Asynchronous Processing**: Non-blocking operations using the `@Async` annotation for background tasks.
- **DTO & Mapper Usage**: Efficient entity-to-DTO mapping via dedicated mappers (e.g., `FlightMapper`, `ReservationMapper`, `AirplaneMapper`).
- **Global Error Handling**: Custom error messages for specific failure scenarios, such as unavailable airplanes or fully booked flights.
- **Email Templates**: Template-based email notifications via JavaMailSender and Thymeleaf.

<p align="center">
  <img src="https://github.com/user-attachments/assets/b1e4f72a-79c3-4240-a1c1-094808935de1" width="280" />
  <img src="https://github.com/user-attachments/assets/522f966d-65e9-4d76-94cd-068fbf65f866" width="279" />
</p>

## Exception Handling

The application features global exception handling for a smooth user experience. Some exceptions include:

- `AirplaneNotAvailableException`: Thrown when an airplane is unavailable.
- `FlightFullyBookedException`: Raised when a flight is fully booked.
- `InvalidFlightTimeException`: Raised when a flight’s departure time is after the arrival time.

You can check out more by installing my code.

## Technologies Used

- **Backend**: Spring Boot, Java
- **Email Templates**: Thymeleaf, HTML, CSS
- **Database Operations**: JPA Criteria API, Hibernate
- **Email Notifications**: Java Mail API
- **API Documentation & Testing**: Swagger
- **Database**: H2 (in-memory)
- **Security**: Spring Security (open for all endpoints)
- **Other**: Asynchronous processing with `@Async`, Reflection API for dynamic operations

## Prerequisites

- **Java 21** must be installed to run this project.
- **Maven** should be installed for dependency management.

## Configuration & Setup

1. Clone the repository:
   ```sh
   git clone https://github.com/gunsugunaydin/Airline-Management-API.git
   cd airline-management-api
   ```
2. Configure mail credentials in `secret.properties`:
   ```properties
   mail.username=your_email@example.com
   mail.password=your_password
   ```
   Learn how to obtain email credentials here: https://youtu.be/Zi2IrgdDhnQ?si=PtkKChAueCmYKmXL
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Alternatively:  
   ```bash
    # Download the project as a ZIP file and extract it
    # Navigate to the extracted folder and open the terminal
    code .
    
    # After opening the project, follow the steps below:
    mvn clean install
    mvn spring-boot:run
    ```

## Usage and API Documentation

Explore the API documentation through Swagger at `http://localhost:8080/swagger-ui/index.html`.

![image](https://github.com/user-attachments/assets/45e0ab92-8d5a-4042-9fb6-1909742f7e45)

Swagger UI Screenshot

## API Endpoints

### Airplane API

- `GET /airplanes`: Retrieve all airplanes
- `GET /airplanes/{id}`: Retrieve details of a specific airplane
- `POST /airplanes`: Add a new airplane
- `PATCH /airplanes/{id}`: Update an airplane
- `DELETE /airplanes/{id}`: Delete an airplane

### Flight API

- `GET /flights`: Retrieve all flights
- `GET /flights/{id}`: Retrieve details of a specific flight
- `POST /flights`: Add a new flight
- `POST /flights/filter`: Search for flights based on parameters
- `PATCH /flights/{id}`: Update a flight
- `DELETE /flights/{id}`: Delete a flight

### Reservation API

- `GET /reservations`: Retrieve all reservations
- `GET /reservations/{id}`: Retrieve details of a specific reservation
- `POST /reservations`: Create a new reservation
- `PATCH /reservations/{id}`: Update a reservation

### Future Enhancements

- **Integration and Unit Tests**: The implementation of integration and unit tests could enhance the reliability of the application.

- **Message Translation**: For better internationalization support, application messages can be moved to a translation file, making it easier to adapt the project for different languages.

## Contact
Please feel free to explore the code and share your feedback. I am always open to suggestions.

<img src="https://media.tenor.com/QYEm7gBzkLQAAAAj/bubble-kittea-cute.gif" alt="Get in Touch Gif" width="50" height="50"> Get in Touch:

- **Email**: [gunsugunay98@gmail.com](mailto:gunsugunay98@gmail.com)
- **LinkedIn**: [linkedin.com/in/gunsugunaydin](https://www.linkedin.com/in/gunsugunaydin/)
