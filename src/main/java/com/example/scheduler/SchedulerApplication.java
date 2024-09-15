package com.example.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Date;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class SchedulerApplication implements SchedulingConfigurer {

    private int userCount = 0;
    private double totalSales = 0.0;

    @Autowired
    private Executor poolScheduler;

    public static void main(String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }

    @Bean
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(10);
    }

    @Bean(name = "poolScheduler")
    public Executor poolScheduler() {
        return Executors.newScheduledThreadPool(5);
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(poolScheduler);
    }

    @Scheduled(fixedRate = 5000)
    public void logCurrentTime() {
        System.out.println("Current time: " + new Date() + " on thread " + Thread.currentThread().getName());
    }

    @Scheduled(cron = "0 * * * * ?")
    public void checkDiskSpace() {
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        double freeSpacePercent = (double) freeSpace / totalSpace * 100;
        System.out.printf("Disk space check: %.2f%% free on thread %s%n", freeSpacePercent, Thread.currentThread().getName());
    }

    @Scheduled(fixedRate = 10000)
    public void simulateUserRegistration() {
        userCount++;
        System.out.println("New user registered. Total users: " + userCount + " on thread " + Thread.currentThread().getName());
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void generateDailySalesReport() {
        double dailySales = new Random().nextDouble() * 1000;
        totalSales += dailySales;
        System.out.printf("Daily sales: $%.2f, Total sales: $%.2f on thread %s%n", dailySales, totalSales, Thread.currentThread().getName());
    }

    @Scheduled(cron = "0 0 2 * * ?")
    public void performDailyBackup() {
        try {
            String sourceDir = "src/main/resources/data";
            String backupDir = "src/main/resources/backup";
            Files.createDirectories(Paths.get(backupDir));
            Files.walk(Paths.get(sourceDir))
                    .forEach(source -> {
                        try {
                            Files.copy(source, Paths.get(backupDir, source.getFileName().toString()));
                        } catch (Exception e) {
                            System.out.println("Error during backup: " + e.getMessage());
                        }
                    });
            System.out.println("Daily backup completed successfully on thread " + Thread.currentThread().getName());
        } catch (Exception e) {
            System.out.println("Failed to perform daily backup: " + e.getMessage());
        }
    }

    @Scheduled(fixedDelayString = "${fixedDelay.in.milliseconds}")
    public void scheduleTaskWithFixedDelay() {
        System.out.println("Fixed delay task executed at " + new Date() + " on thread " + Thread.currentThread().getName());
    }
}