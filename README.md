# Spring Boot Scheduler Example

This project demonstrates various scheduling techniques using Spring Boot's `@Scheduled` annotation and custom configuration.

## Project Structure

```
├── src
│   └── main
│       ├── java
│       │   └── com
│       │       └── example
│       │           └── scheduler
│       │               └── SchedulerApplication.java
│       └── resources
│           ├── application.properties
│           ├── data (create this directory)
│           └── backup (will be created by the application)
└── pom.xml
```

## Features

- Custom thread pool configuration for scheduled tasks
- Various scheduling techniques:
  - Fixed rate execution
  - Cron-based scheduling
  - Fixed delay execution with externalized configuration
- Simulated tasks:
  - Logging current time
  - Checking disk space
  - User registration simulation
  - Daily sales report generation
  - Daily backup process

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher

## Setup and Running

1. Clone the repository:
   ```
   git clone https://github.com/Chandrashekharwagh/spring-boot-scheduler-example.git
   cd spring-boot-scheduler-example
   ```

2. Create the `data` directory:
   ```
   mkdir -p src/main/resources/data
   ```

3. Build the project:
   ```
   mvn clean install
   ```

4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Configuration

The main configuration is in `src/main/resources/application.properties`:

```properties
logging.level.org.springframework=INFO
logging.level.com.example=DEBUG
server.port=8080
fixedDelay.in.milliseconds=6000
```

You can modify the `fixedDelay.in.milliseconds` value to change the delay for the fixed delay task.

## Main Application Class

The `SchedulerApplication` class (`src/main/java/com/example/scheduler/SchedulerApplication.java`) contains all the scheduled tasks and configuration. It demonstrates:

- Custom thread pool configuration
- Implementation of `SchedulingConfigurer`
- Various `@Scheduled` tasks with different scheduling strategies

## Scheduled Tasks

1. `logCurrentTime()`: Runs every 5 seconds, logging the current time.
2. `checkDiskSpace()`: Runs every minute, checking and reporting available disk space.
3. `simulateUserRegistration()`: Runs every 10 seconds, simulating a new user registration.
4. `generateDailySalesReport()`: Runs at the start of every hour, simulating daily sales reporting.
5. `performDailyBackup()`: Runs at 2 AM every day, simulating a daily backup process.
6. `scheduleTaskWithFixedDelay()`: Runs with a fixed delay specified in `application.properties`.

## Customization

You can modify the scheduling parameters, add new tasks, or adjust the thread pool size in the `SchedulerApplication` class.

## Notes

- The disk space check and backup tasks interact with the file system. Ensure your application has the necessary permissions.
- The sales report uses random numbers for demonstration. In a real application, you'd typically fetch this data from a database or service.
- The backup task is a simple file copy operation. In a production environment, you'd want a more robust backup solution.

## Contributing

Feel free to fork this project and submit pull requests with improvements or additional examples of Spring Boot scheduling techniques.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details.
