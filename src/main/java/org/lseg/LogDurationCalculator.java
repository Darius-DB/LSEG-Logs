package org.lseg;

import java.io.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class LogDurationCalculator {

    public static void main(String[] args) {

        String csvFile = "src/main/resources/logs.log";
        Map<String, String> startTimes = new HashMap<>();
        Map<String, String> endTimes = new HashMap<>();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length < 4) continue;

                String timestamp = parts[0].trim();
                String status = parts[2].trim().toUpperCase();
                String pid = parts[3].trim();

                if (status.equals("START")) {
                    startTimes.put(pid, timestamp);
                } else if (status.equals("END")) {
                    endTimes.put(pid, timestamp);
                }
            }

            System.out.println("PID | Duration (seconds) | Status");
            System.out.println("-------------------------------------");

            for (String pid : startTimes.keySet()) {
                if (endTimes.containsKey(pid)) {
                    try {
                        LocalTime start = LocalTime.parse(startTimes.get(pid), timeFormatter);
                        LocalTime end = LocalTime.parse(endTimes.get(pid), timeFormatter);
                        long duration = Duration.between(start, end).getSeconds();

                        String status = "OK";
                        if (duration > 600) {
                            status = "ERROR: took longer than 10 minutes";
//                            System.err.printf("ERROR: Job %s took %d seconds%n", pid, duration);
                        } else if (duration > 300) {
                            status = "WARNING: took longer than 5 minutes";
//                            System.out.printf("WARNING: Job %s took %d seconds%n", pid, duration);
                        }

                        System.out.printf("%s | %d seconds | %s%n", pid, duration, status);

                    } catch (Exception e) {
                        System.err.println("Failed to parse time for PID " + pid + ": " + e.getMessage());
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}
