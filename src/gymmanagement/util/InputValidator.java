package gymmanagement.util;

import java.util.Scanner;

public class InputValidator {

    public static String getString(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                System.out.print("Không được để trống. Vui lòng nhập lại: ");
            } else {
                return input;
            }
        }
    }

    public static int getInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập một số nguyên. Nhập lại: ");
            }
        }
    }

    public static double getDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                String input = sc.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("Vui lòng nhập một số. Nhập lại: ");
            }
        }
    }

    public static String getEmail(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String email = sc.nextLine().trim();
            // Regex đơn giản
            if (email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                return email;
            } else {
                System.out.print("Email không hợp lệ. Vui lòng nhập lại: ");
            }
        }
    }
}