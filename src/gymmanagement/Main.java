package gymmanagement;

public class Main {

    /**
     * Đây là phương thức chính (main) để khởi chạy toàn bộ ứng dụng.
     */
    public static void main(String[] args) {
        // 1. Tạo một đối tượng điều khiển (Controller) của phòng gym
        GymController app = new GymController();

        // 2. Tải tất cả dữ liệu từ file .dat (nếu có)
        app.loadAllData();

        // 3. Chạy vòng lặp menu chính của ứng dụng
        app.run();

        // 4. Khi vòng lặp kết thúc (người dùng chọn 0), lưu tất cả dữ liệu
        app.saveAllData();
    }
}