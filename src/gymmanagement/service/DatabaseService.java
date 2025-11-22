package gymmanagement.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    // Helper method chung để lưu danh sách bất kỳ
    @SuppressWarnings("rawtypes")
    public void saveData(List data, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.err.println("Lỗi khi lưu tệp " + filename + ": " + e.getMessage());
        }
    }

    // Helper method chung để đọc danh sách bất kỳ
    @SuppressWarnings("unchecked")
    public <T> List<T> loadData(String filename, Class<T> type) {
        File file = new File(filename);
        if (!file.exists()) {
            return new ArrayList<>(); // Trả về danh sách rỗng nếu tệp không tồn tại
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<T>) ois.readObject();
        } catch (EOFException e) {
            // Tệp rỗng, đây là trường hợp bình thường khi mới bắt đầu
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Lỗi khi đọc tệp " + filename + ": " + e.getMessage());
            return new ArrayList<>(); // Trả về ds rỗng nếu lỗi
        }
    }
}