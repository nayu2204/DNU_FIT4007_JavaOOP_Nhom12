package gymmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Checkin implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String memberId;
    private String registrationId; // ID của gói tập được dùng để checkin
    private LocalDateTime checkinTime;

    public Checkin(String id, String memberId, String registrationId, LocalDateTime checkinTime) {
        this.id = id;
        this.memberId = memberId;
        this.registrationId = registrationId;
        this.checkinTime = checkinTime;
    }

    // Getters
    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public String getRegistrationId() { return registrationId; }
    public LocalDateTime getCheckinTime() { return checkinTime; }

    @Override
    public String toString() {
        return "Checkin [ID: " + id + ", Member: " + memberId + ", Time: " + checkinTime + "]";
    }
}