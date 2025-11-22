
package gymmanagement;
import java.io.File;
import gymmanagement.service.CsvService;
import gymmanagement.exception.MemberNotFoundException;
import gymmanagement.exception.OutOfSessionsException;
import gymmanagement.exception.PackageExpiredException;
import gymmanagement.exception.TrainerNotFoundException;
import gymmanagement.model.*;
import gymmanagement.service.DatabaseService;
import gymmanagement.util.DateUtils;
import gymmanagement.util.InputValidator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class GymController {

    // Files
    private static final String DATA_DIR = "data";
    private static final String MEMBERS_FILE = DATA_DIR + "/members.csv";
    private static final String TRAINERS_FILE = DATA_DIR + "/trainers.csv";
    private static final String REGISTRATIONS_FILE = DATA_DIR + "/registrations.dat";
    private static final String SCHEDULES_FILE = DATA_DIR + "/schedules.dat";
    private static final String CHECKINS_FILE = DATA_DIR + "/checkins.dat";
    // Data lists
    private List<Member> members;
    private List<Trainer> trainers;
    private List<Registration> registrations;
    private List<Schedule> schedules;
    private List<Checkin> checkins;

    // Services
    private final DatabaseService dbService;
    private final CsvService csvService;
    private final Scanner scanner;

    public GymController() {
        this.dbService = new DatabaseService();
        this.csvService = new CsvService();
        this.scanner = new Scanner(System.in);
        this.members = new ArrayList<>();
        this.trainers = new ArrayList<>();
        this.registrations = new ArrayList<>();
        this.schedules = new ArrayList<>();
        this.checkins = new ArrayList<>();
    }


    public void run() {
        boolean running = true;
        while (running) {
            showMainMenu();
            int choice = InputValidator.getInt(scanner, "Nhập lựa chọn của bạn: ");
            switch (choice) {
                case 1:
                    manageMembers();
                    break;
                case 2:
                    manageTrainers();
                    break;
                case 3:
                    manageRegistrations();
                    break;
                case 4:
                    manageScheduling();
                    break;
                case 5:
                    doCheckIn();
                    break;
                case 6:
                    runReports();
                    break;
                case 0:
                    running = false;
                    System.out.println("Đang lưu dữ liệu... Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n--- HỆ THỐNG QUẢN LÝ PHÒNG GYM ---");
        System.out.println("1. Quản lý Hội viên");
        System.out.println("2. Quản lý Huấn luyện viên (PT)");
        System.out.println("3. Quản lý Gói tập (Đăng ký/Gia hạn)");
        System.out.println("4. Quản lý Lịch tập (PT)");
        System.out.println("5. Điểm danh (Check-in)");
        System.out.println("6. Báo cáo & Thống kê");
        System.out.println("0. Thoát và Lưu");
    }


    public void loadAllData() {
        System.out.println("Đang tải dữ liệu...");
        members = csvService.loadMembers(MEMBERS_FILE);
        trainers = csvService.loadTrainers(TRAINERS_FILE);
        registrations = dbService.loadData(REGISTRATIONS_FILE, Registration.class);
        schedules = dbService.loadData(SCHEDULES_FILE, Schedule.class);
        checkins = dbService.loadData(CHECKINS_FILE, Checkin.class);
        System.out.println("Tải dữ liệu thành công!");
    }

    public void saveAllData() {
        System.out.println("Đang lưu dữ liệu...");
        new File(DATA_DIR).mkdirs();
        csvService.saveMembers(members, MEMBERS_FILE);
        csvService.saveTrainers(trainers, TRAINERS_FILE);
        dbService.saveData(registrations, REGISTRATIONS_FILE);
        dbService.saveData(schedules, SCHEDULES_FILE);
        dbService.saveData(checkins, CHECKINS_FILE);
        System.out.println("Lưu dữ liệu thành công!");
    }

    // ---------------------------------------------
    // HELPER METHODS (TÌM KIẾM)
    // ---------------------------------------------

    private Member findMember() {
        String phone = InputValidator.getString(scanner, "Nhập SĐT hội viên để tìm: ");
        return members.stream()
                .filter(m -> m.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    private Member findMemberById(String id) throws MemberNotFoundException {
        return members.stream()
                .filter(m -> m.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new MemberNotFoundException("Không tìm thấy hội viên ID: " + id));
    }

    private Trainer findTrainer() {
        System.out.println("Danh sách PT hiện có:");
        trainers.forEach(System.out::println);
        String phone = InputValidator.getString(scanner, "Nhập SĐT của PT để tìm: ");
        return trainers.stream()
                .filter(t -> t.getPhone().equals(phone))
                .findFirst()
                .orElse(null);
    }

    private Trainer findTrainerById(String id) throws TrainerNotFoundException {
        return trainers.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new TrainerNotFoundException("Không tìm thấy HLV ID: " + id));
    }

    // ---------------------------------------------
    // FEATURE IMPLEMENTATION (PRIVATE)
    // ---------------------------------------------

    private void manageMembers() {
        System.out.println("--- Quản lý Hội viên ---");
        System.out.println("1. Thêm hội viên mới");
        System.out.println("2. Tìm kiếm hội viên");
        System.out.println("3. Xóa hội viên");
        int choice = InputValidator.getInt(scanner, "Lựa chọn: ");

        if (choice == 1) {
            addMember();
        } else if (choice == 2) {
            Member m = findMember();
            if (m == null) System.out.println("Không tìm thấy hội viên.");
            else System.out.println("Tìm thấy: " + m);
        } //... (Tự hoàn thiện Sửa, Xóa)
    }

    private void addMember() {
        System.out.println("--- Thêm hội viên mới ---");
        String name = InputValidator.getString(scanner, "Nhập tên: ");
        String phone = InputValidator.getString(scanner, "Nhập SĐT: ");
        String email = InputValidator.getEmail(scanner, "Nhập email: ");
        String id = "M-" + UUID.randomUUID().toString().substring(0, 8); // Tạo ID

        Member newMember = new Member(id, name, phone, email);
        members.add(newMember);
        System.out.println("Thêm hội viên thành công: " + newMember);
    }

    private void manageTrainers() {
        System.out.println("--- Quản lý Huấn luyện viên ---");
        System.out.println("1. Thêm HLV mới");
        System.out.println("2. Tìm kiếm HLV");
        int choice = InputValidator.getInt(scanner, "Lựa chọn: ");
        if (choice == 1) {
            addTrainer();
        } //...
    }

    private void addTrainer() {
        System.out.println("--- Thêm HLV mới ---");
        String name = InputValidator.getString(scanner, "Nhập tên HLV: ");
        String phone = InputValidator.getString(scanner, "Nhập SĐT HLV: ");
        String email = InputValidator.getEmail(scanner, "Nhập email HLV: ");
        String specialty = InputValidator.getString(scanner, "Nhập chuyên môn: ");
        String id = "T-" + UUID.randomUUID().toString().substring(0, 8); // Tạo ID

        Trainer newTrainer = new Trainer(id, name, phone, email, specialty);
        trainers.add(newTrainer);
        System.out.println("Thêm HLV thành công: " + newTrainer);
    }


    private void manageRegistrations() {
        System.out.println("--- Đăng ký Gói tập ---");
        Member member = findMember();
        if (member == null) {
            System.out.println("Không tìm thấy hội viên!");
            return;
        }

        System.out.println("Chọn loại gói tập:");
        System.out.println("1. Gói theo tháng (30 ngày, 500k)");
        System.out.println("2. Gói theo buổi (12 buổi, 60 ngày, 1200k)");
        System.out.println("3. Gói không giới hạn (365 ngày, 5000k)");
        System.out.println("9. (Test) Gói 1 buổi, hết hạn 1 ngày (100k)"); // Thêm để test
        int choice = InputValidator.getInt(scanner, "Lựa chọn: ");

        String regId = "R-" + UUID.randomUUID().toString().substring(0, 8);
        String memId = "MEM-" + UUID.randomUUID().toString().substring(0, 8);
        LocalDate startDate = LocalDate.now();
        Membership membership = null;
        double amount = 0;

        switch (choice) {
            case 1:
                membership = new MonthlyPass(memId, member.getId(), startDate, startDate.plusDays(30));
                amount = 500000;
                break;
            case 2:
                membership = new SessionPack(memId, member.getId(), startDate, startDate.plusDays(60), 12);
                amount = 1200000;
                break;
            case 3:
                membership = new UnlimitedPass(memId, member.getId(), startDate, startDate.plusDays(365));
                amount = 5000000;
                break;
            case 9: // Dành cho test case
                membership = new SessionPack(memId, member.getId(), startDate, startDate.plusDays(1), 1);
                amount = 100000;
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ.");
                return;
        }

        Registration newReg = new Registration(regId, member.getId(), membership, startDate, amount);
        registrations.add(newReg);
        member.addRegistrationId(regId); // Cập nhật cho hội viên
        System.out.println("Đăng ký gói tập thành công cho hội viên " + member.getName());
        System.out.println(newReg);
    }

    private void manageScheduling() {
        System.out.println("--- Đặt lịch với PT ---");
        Member member = findMember();
        if (member == null) {
            System.out.println("Không tìm thấy hội viên!");
            return;
        }

        Trainer trainer = findTrainer();
        if (trainer == null) {
            System.out.println("Không tìm thấy HLV!");
            return;
        }

        System.out.println("Nhập thời gian bắt đầu (VD: 2025-11-10 15:00):");
        String startTimeStr = scanner.nextLine();
        LocalDateTime startTime = DateUtils.parseDateTime(startTimeStr);
        if (startTime == null) return;

        // Giả sử mỗi buổi tập 1 tiếng
        LocalDateTime endTime = startTime.plusHours(1);

        // Kiểm tra PT bận
        boolean isBusy = schedules.stream().anyMatch(schedule ->
                schedule.getTrainerId().equals(trainer.getId()) &&
                        // Kiểm tra trùng lặp thời gian
                        (startTime.isBefore(schedule.getEndTime()) && endTime.isAfter(schedule.getStartTime()))
        );

        if (isBusy) {
            System.out.println("Lỗi: PT đã bận vào thời gian này. Vui lòng chọn giờ khác.");
            return;
        }

        // Tạo lịch
        String schedId = "S-" + UUID.randomUUID().toString().substring(0, 8);
        Schedule newSchedule = new Schedule(schedId, trainer.getId(), member.getId(), startTime, endTime);
        schedules.add(newSchedule);
        System.out.println("Đặt lịch thành công:");
        System.out.println(newSchedule);
    }

    private void doCheckIn() {
        System.out.println("--- Điểm danh (Check-in) ---");
        Member member = findMember();
        if (member == null) {
            System.out.println("Không tìm thấy hội viên!");
            return;
        }

        // Tìm gói tập còn hoạt động của hội viên
        Registration activeRegistration = null;
        for (String regId : member.getRegistrationIds()) {
            // Ưu tiên gói SessionPack nếu có
            Registration reg = registrations.stream().filter(r -> r.getId().equals(regId)).findFirst().orElse(null);
            if (reg != null && reg.getMembership().isActive() && reg.getMembership() instanceof SessionPack) {
                activeRegistration = reg;
                break;
            }
            // Nếu không có SessionPack, lấy gói bất kỳ còn hạn
            if (reg != null && reg.getMembership().isActive()) {
                activeRegistration = reg;
            }
        }


        if (activeRegistration == null) {
            System.out.println("Hội viên này không có gói tập nào đang hoạt động!");
            return;
        }

        System.out.println("Đang dùng gói: " + activeRegistration.getMembership());

        try {
            // Cốt lõi của logic điểm danh
            activeRegistration.getMembership().useSession();

            // Nếu thành công, tạo checkin
            String checkinId = "CI-" + UUID.randomUUID().toString().substring(0, 8);
            Checkin checkin = new Checkin(checkinId, member.getId(), activeRegistration.getId(), LocalDateTime.now());
            checkins.add(checkin);

            System.out.println("Check-in thành công!");
            System.out.println("Thông tin gói: " + activeRegistration.getMembership());

        } catch (OutOfSessionsException | PackageExpiredException e) {
            System.err.println("LỖI CHECK-IN: " + e.getMessage());
        }
    }

    private void runReports() {
        System.out.println("--- Báo cáo & Thống kê ---");
        System.out.println("1. Doanh thu theo tháng");
        System.out.println("2. DS hội viên sắp hết hạn / hết buổi");
        System.out.println("3. Top 3 PT có nhiều lịch nhất");
        int choice = InputValidator.getInt(scanner, "Lựa chọn: ");

        if (choice == 1) {
            reportRevenue();
        } else if (choice == 2) {
            reportExpiringMembers();
        } else if (choice == 3) {
            reportTopTrainers();
        }
    }

    private void reportRevenue() {
        System.out.println("--- Báo cáo doanh thu ---");
        // Lọc registrations theo tháng (ví dụ: tháng hiện tại)
        double totalRevenue = registrations.stream()
                .filter(r -> r.getRegistrationDate().getMonth() == LocalDate.now().getMonth() &&
                        r.getRegistrationDate().getYear() == LocalDate.now().getYear())
                .mapToDouble(Registration::getAmountPaid)
                .sum();
        System.out.println("Tổng doanh thu tháng này ("+ LocalDate.now().getMonth() +"): " + String.format("%,.0f", totalRevenue) + " VND");
    }

    private void reportExpiringMembers() {
        System.out.println("--- Hội viên sắp hết buổi (<= 3 buổi) ---");
        List<Registration> expiringSession = registrations.stream()
                .filter(r -> r.getMembership().getStatus() == PackageStatus.ACTIVE)
                .filter(r -> r.getMembership() instanceof SessionPack)
                .filter(r -> r.getMembership().getRemainingSessions() <= 3)
                .collect(Collectors.toList());

        if (expiringSession.isEmpty()) {
            System.out.println("Không có hội viên nào sắp hết buổi.");
        } else {
            expiringSession.forEach(reg -> {
                try {
                    System.out.println(findMemberById(reg.getMemberId()).getName() + " - " + reg.getMembership());
                } catch (MemberNotFoundException e) {/*bỏ qua*/}
            });
        }

        System.out.println("\n--- Hội viên sắp hết hạn (<= 7 ngày) ---");
        LocalDate nextWeek = LocalDate.now().plusDays(7);
        List<Registration> expiringDate = registrations.stream()
                .filter(r -> r.getMembership().getStatus() == PackageStatus.ACTIVE)
                .filter(r -> r.getMembership().getEndDate().isAfter(LocalDate.now()) &&
                        r.getMembership().getEndDate().isBefore(nextWeek))
                .collect(Collectors.toList());

        if (expiringDate.isEmpty()) {
            System.out.println("Không có hội viên nào sắp hết hạn.");
        } else {
            expiringDate.forEach(reg -> {
                try {
                    System.out.println(findMemberById(reg.getMemberId()).getName() + " - " + reg.getMembership());
                } catch (MemberNotFoundException e) {/*bỏ qua*/}
            });
        }
    }

    private void reportTopTrainers() {
        System.out.println("--- Top 3 PT có nhiều lịch nhất ---");

        // Đếm số lịch tập cho mỗi PT
        Map<String, Long> trainerCounts = schedules.stream()
                .collect(Collectors.groupingBy(Schedule::getTrainerId, Collectors.counting()));

        if (trainerCounts.isEmpty()) {
            System.out.println("Chưa có lịch tập nào được đặt.");
            return;
        }

        // Sắp xếp và lấy top 3
        List<Map.Entry<String, Long>> topTrainers = trainerCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        int rank = 1;
        for (Map.Entry<String, Long> entry : topTrainers) {
            try {
                Trainer trainer = findTrainerById(entry.getKey());
                System.out.printf("%d. HLV %s - %d buổi tập\n", rank++, trainer.getName(), entry.getValue());
            } catch (TrainerNotFoundException e) {
                System.out.printf("%d. HLV (ID: %s) - %d buổi tập\n", rank++, entry.getKey(), entry.getValue());
            }
        }
    }
}