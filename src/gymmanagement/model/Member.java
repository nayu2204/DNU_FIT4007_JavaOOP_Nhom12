package gymmanagement.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    // Có thể thêm các thuộc tính riêng cho hội viên
    // ví dụ: danh sách các đăng ký
    private List<String> registrationIds; // Lưu ID của các gói đã đăng ký

    public Member(String id, String name, String phone, String email) {
        super(id, name, phone, email);
        this.registrationIds = new ArrayList<>();
    }

    public List<String> getRegistrationIds() {
        return registrationIds;
    }

    public void addRegistrationId(String regId) {
        this.registrationIds.add(regId);
    }

    @Override
    public String toString() {
        return "Hội viên [" + super.toString() + "]";
    }
}