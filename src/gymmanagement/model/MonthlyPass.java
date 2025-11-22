package gymmanagement.model;

import gymmanagement.exception.OutOfSessionsException;
import gymmanagement.exception.PackageExpiredException;

import java.time.LocalDate;

public class MonthlyPass extends Membership {
    private static final long serialVersionUID = 1L;
    // Gói tháng thì không giới hạn số buổi trong thời hạn

    public MonthlyPass(String id, String memberId, LocalDate startDate, LocalDate endDate) {
        super(id, memberId, startDate, endDate);
    }

    @Override
    public boolean isActive() {
        updateStatus();
        return this.status == PackageStatus.ACTIVE;
    }

    @Override
    public int getRemainingSessions() {
        // Gói tháng không giới hạn số buổi, nhưng nếu hết hạn thì là 0
        return isActive() ? 9999 : 0; // Dùng số lớn
    }

    @Override
    public void useSession() throws PackageExpiredException {
        if (!isActive()) {
            throw new PackageExpiredException("Gói tập đã hết hạn vào ngày " + getEndDate());
        }
        // Gói tháng không cần trừ
    }

    @Override
    public void updateStatus() {
        if (isDateExpired()) {
            this.status = PackageStatus.EXPIRED;
        } else {
            this.status = PackageStatus.ACTIVE;
        }
    }

    @Override
    public String toString() {
        return "Gói Tháng [ID: " + id + ", Member: " + memberId + ", Hạn: " + endDate + ", Trạng thái: " + getStatus() + "]";
    }
}