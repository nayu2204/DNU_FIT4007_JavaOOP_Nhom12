package gymmanagement.model;

import java.io.Serializable;
import java.util.List;

public class Trainer extends Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String specialty; // Chuyên môn
    private List<String> availableSlots; // Danh sách giờ rảnh, ví dụ: "MON_09_10"

    public Trainer(String id, String name, String phone, String email, String specialty) {
        super(id, name, phone, email);
        this.specialty = specialty;
    }

    // Getters and Setters
    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }
    public List<String> getAvailableSlots() { return availableSlots; }
    public void setAvailableSlots(List<String> availableSlots) { this.availableSlots = availableSlots; }

    @Override
    public String toString() {
        return "Huấn luyện viên [" + super.toString() + ", Chuyên môn: " + specialty + "]";
    }
}