package gymmanagement.model;

import gymmanagement.exception.OutOfSessionsException;
import gymmanagement.exception.PackageExpiredException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Membership implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String memberId;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected PackageStatus status;

    public Membership(String id, String memberId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.memberId = memberId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = PackageStatus.ACTIVE;
        updateStatus(); // Cập nhật trạng thái ban đầu
    }

    // Phương thức trừu tượng
    /**
     * Kiểm tra xem gói còn hoạt động không (còn hạn VÀ còn lượt)
     */
    public abstract boolean isActive();

    /**
     * Lấy số buổi tập còn lại
     */
    public abstract int getRemainingSessions();

    /**
     * Sử dụng một buổi tập (điểm danh)
     */
    public abstract void useSession() throws OutOfSessionsException, PackageExpiredException;

    /**
     * Cập nhật trạng thái (ACTIVE, EXPIRED, FULL)
     * Được gọi mỗi khi load hoặc có thay đổi
     */
    public abstract void updateStatus();

    // Getters
    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public PackageStatus getStatus() {
        updateStatus(); // Luôn cập nhật trước khi trả về
        return status;
    }

    protected boolean isDateExpired() {
        return LocalDate.now().isAfter(endDate);
    }
}