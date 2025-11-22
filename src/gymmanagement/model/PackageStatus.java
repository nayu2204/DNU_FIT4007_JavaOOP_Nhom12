package gymmanagement.model;

public enum PackageStatus {
    ACTIVE,  // Còn hạn và còn lượt
    EXPIRED, // Hết hạn
    FULL     // Hết lượt (chỉ dùng cho SessionPack)
}