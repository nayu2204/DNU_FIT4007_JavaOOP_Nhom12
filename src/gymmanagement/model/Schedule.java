package gymmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String trainerId;
    private String memberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Schedule(String id, String trainerId, String memberId, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.trainerId = trainerId;
        this.memberId = memberId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters
    public String getId() { return id; }
    public String getTrainerId() { return trainerId; }
    public String getMemberId() { return memberId; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime() { return endTime; }

    @Override
    public String toString() {
        return "Lịch tập [ID: " + id + ", PT: " + trainerId + ", Member: " + memberId + ", Từ: " + startTime + " Đến: " + endTime + "]";
    }
}