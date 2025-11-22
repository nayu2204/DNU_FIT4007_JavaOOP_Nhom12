package gymmanagement.model;

import gymmanagement.exception.OutOfSessionsException;
import gymmanagement.exception.PackageExpiredException;

import java.time.LocalDate;

public class SessionPack extends Membership {
    private static final long serialVersionUID = 1L;
    private int totalSessions;
    private int usedSessions;

    public SessionPack(String id, String memberId, LocalDate startDate, LocalDate endDate, int totalSessions) {
        super(id, memberId, startDate, endDate);
        this.totalSessions = totalSessions;
        this.usedSessions = 0;
    }

    @Override
    public boolean isActive() {
        updateStatus();
        return this.status == PackageStatus.ACTIVE;
    }

    @Override
    public int getRemainingSessions() {
        return totalSessions - usedSessions;
    }

    @Override
    public void useSession() throws OutOfSessionsException, PackageExpiredException {
        if (!isActive()) {
            if (isDateExpired()) {
                throw new PackageExpiredException("Gói tập đã hết hạn vào ngày " + getEndDate());
            }
            if (getRemainingSessions() <= 0) {
                throw new OutOfSessionsException("Gói tập đã hết " + totalSessions + " buổi.");
            }
        }
        this.usedSessions++;
        updateStatus(); // Cập nhật lại trạng thái sau khi dùng
    }

    @Override
    public void updateStatus() {
        if (isDateExpired()) {
            this.status = PackageStatus.EXPIRED;
        } else if (usedSessions >= totalSessions) {
            this.status = PackageStatus.FULL;
        } else {
            this.status = PackageStatus.ACTIVE;
        }
    }

    @Override
    public String toString() {
        return "Gói Buổi [ID: " + id + ", Member: " + memberId + ", Còn lại: " + getRemainingSessions() + "/" + totalSessions + ", Hạn: " + endDate + ", Trạng thái: " + getStatus() + "]";
    }
}