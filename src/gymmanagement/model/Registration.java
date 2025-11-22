package gymmanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Registration implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String memberId;
    private Membership membership; // Đối tượng Gói tập (Monthly, Session, Unlimited)
    private LocalDate registrationDate;
    private double amountPaid;

    public Registration(String id, String memberId, Membership membership, LocalDate registrationDate, double amountPaid) {
        this.id = id;
        this.memberId = memberId;
        this.membership = membership;
        this.registrationDate = registrationDate;
        this.amountPaid = amountPaid;
    }

    // Getters
    public String getId() { return id; }
    public String getMemberId() { return memberId; }
    public Membership getMembership() { return membership; }
    public LocalDate getRegistrationDate() { return registrationDate; }
    public double getAmountPaid() { return amountPaid; }

    @Override
    public String toString() {
        return "Đăng ký [ID: " + id + ", Member: " + memberId + ", Gói: " + membership.toString() + ", Thanh toán: " + amountPaid + "]";
    }
}