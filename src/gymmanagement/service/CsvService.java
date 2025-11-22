package gymmanagement.service;

import gymmanagement.model.Member;
import gymmanagement.model.Trainer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvService {

    // Dấu phẩy dùng để ngăn cách
    private static final String CSV_DELIMITER = ",";
    // Ký tự xuống dòng
    private static final String CSV_NEW_LINE = "\n";

    // --- Xử lý cho MEMBER ---

    /**
     * Ghi danh sách Member ra file CSV
     */
    public void saveMembers(List<Member> members, String filename) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw)) {

            // Ghi header (tiêu đề cột)
            bw.write("id,name,phone,email" + CSV_NEW_LINE);

            // Ghi từng hội viên
            for (Member m : members) {
                String line = m.getId() + CSV_DELIMITER +
                        m.getName() + CSV_DELIMITER +
                        m.getPhone() + CSV_DELIMITER +
                        m.getEmail();
                bw.write(line + CSV_NEW_LINE);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file CSV (Members): " + e.getMessage());
        }
    }

    /**
     * Đọc danh sách Member từ file CSV
     */
    public List<Member> loadMembers(String filename) {
        List<Member> members = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return members; // Trả về ds rỗng nếu file chưa có
        }

        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine(); // Đọc dòng header (bỏ qua)

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length >= 4) {
                    Member member = new Member(parts[0], parts[1], parts[2], parts[3]);
                    members.add(member);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file CSV (Members): " + e.getMessage());
        }
        return members;
    }

    // --- Xử lý cho TRAINER ---

    /**
     * Ghi danh sách Trainer ra file CSV
     */
    public void saveTrainers(List<Trainer> trainers, String filename) {
        try (FileWriter fw = new FileWriter(filename);
             BufferedWriter bw = new BufferedWriter(fw)) {

            bw.write("id,name,phone,email,specialty" + CSV_NEW_LINE);

            for (Trainer t : trainers) {
                String line = t.getId() + CSV_DELIMITER +
                        t.getName() + CSV_DELIMITER +
                        t.getPhone() + CSV_DELIMITER +
                        t.getEmail() + CSV_DELIMITER +
                        t.getSpecialty();
                bw.write(line + CSV_NEW_LINE);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file CSV (Trainers): " + e.getMessage());
        }
    }

    /**
     * Đọc danh sách Trainer từ file CSV
     */
    public List<Trainer> loadTrainers(String filename) {
        List<Trainer> trainers = new ArrayList<>();
        File file = new File(filename);
        if (!file.exists()) {
            return trainers;
        }

        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {

            String line = br.readLine(); // Bỏ qua header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length >= 5) {
                    Trainer trainer = new Trainer(parts[0], parts[1], parts[2], parts[3], parts[4]);
                    trainers.add(trainer);
                }
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file CSV (Trainers): " + e.getMessage());
        }
        return trainers;
    }
}