# LogDurationCalculator

A simple Java utility that reads a log file and calculates the duration between `START` and `END` events for different processes, identified by their process IDs (PIDs). It flags warnings or errors based on the duration thresholds.

---

## 📄 Description

`LogDurationCalculator` processes a CSV-style log file (`logs.log`) with entries that look like this:

```
12:00:01,INFO,START,1234
12:07:15,INFO,END,1234
```

The tool calculates how long each job (identified by its PID) took between the `START` and `END` timestamps, and classifies them as:

- ✅ **OK** — if the job duration is 5 minutes or less.
- ⚠️ **WARNING** — if the job duration is more than 5 minutes but less than or equal to 10 minutes.
- ❌ **ERROR** — if the job duration exceeds 10 minutes.

---

## 📂 Log Format

The expected log format is CSV with at least 4 columns:

```
<timestamp>,<log-level>,<status>,<pid>
```

- `timestamp`: In `HH:mm:ss` format.
- `status`: Either `START` or `END`.
- `pid`: Unique identifier for the process.

---

## ✅ Example Output

```
PID | Duration (seconds) | Status
-------------------------------------
1234 | 434 seconds | OK
5678 | 623 seconds | WARNING: took longer than 5 minutes
9101 | 841 seconds | ERROR: took longer than 10 minutes
```

---

## 🚀 How to Run

1. Make sure you have Java installed (Java 8+).
2. Place your `logs.log` file in `src/main/resources/`.
3. Compile and run:

```bash
javac -d out src/org/lseg/LogDurationCalculator.java
java -cp out org.lseg.LogDurationCalculator
```

---

## 🛠 Configuration

- **Log file location:** Hardcoded as `src/main/resources/logs.log`.
- **Time format:** Must be `HH:mm:ss`.
- **Thresholds:**
  - 5 minutes (300 seconds): warning.
  - 10 minutes (600 seconds): error.

---

## 📦 Dependencies

Lombok

---

