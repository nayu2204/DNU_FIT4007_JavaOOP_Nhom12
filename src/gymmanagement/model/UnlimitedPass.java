package gymmanagement.model;

import gymmanagement.exception.OutOfSessionsException;
import gymmanagement.exception.PackageExpiredException;

import java.time.LocalDate;

public class UnlimitedPass extends Membership {
    private static final long serialVersionUID = 1L;

    public UnlimitedPass(String id, String memberId, LocalDate startDate, LocalDate endDate) {
        super(id, memberId, startDate, endDate);
    }

    @Override
    public boolean isActive() {
        updateStatus();
        return this.status == PackageStatus.ACTIVE;
    }

    @Override
    public int getRemainingSessions() {
        return isActive() ? Integer.MAX_VALUE : 0;
    }

    @Override
    public void useSession() throws OutOfSessionsException, PackageExpiredException {
        if (!isActive()) {
            throw new PackageExpiredException("Gói tập đã hết hạn vào ngày " + getEndDate());
        }
        // Không cần làm gì
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
        return "Gói Không Giới Hạn [ID: " + id + ", Member: " + memberId + ", Hạn: " + endDate + ", Trạng thái: " + getStatus() + "]";
    }
}