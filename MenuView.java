package ootd;

import java.util.Scanner;

public class MenuView {
    private final Scanner sc = new Scanner(System.in);

    public int getTop() {
        System.out.println("상의를 골라주세요 : ");
        System.out.println("1.블라우스 2.티셔츠 3.니트");

        while (true) {
            int choice = safeInt();
            if (choice == 1 || choice == 2 || choice == 3) {
                return choice;
            }
            System.out.println("잘못 선택하셨습니다. 1,2,3번 중 다시 입력해주세요 : ");
        }
    }

    public int getBottom() {
        System.out.println("하의를 골라주세요 : ");
        System.out.println("1.바지 2.치마");

        while (true) {
            int choice = safeInt();
            if (choice == 1 || choice == 2) {
                return choice;
            }
            System.out.println("잘못 선택하셨습니다. 1,2번 중 다시 입력해주세요 : ");
        }
    }

    public int getAcc() {
        System.out.println("액세서리를 골라주세요 : ");
        System.out.println("1.모자 2.안경");

        while (true) {
            int choice = safeInt();
            if (choice == 1 || choice == 2) {
                return choice;
            }
            System.out.println("잘못 선택하셨습니다. 1,2번 중 다시 입력해주세요 : ");
        }
    }

    private int safeInt() {
        while (true) {
            String answer = sc.nextLine().trim();

            if (answer.isEmpty()) {
                System.out.println("입력값이 비어있습니다. 다시 입력해주세요 : ");
                continue;
            }
            if (!answer.matches("\\d+")) {
                System.out.println("숫자만 입력할 수 있습니다. 다시 입력해주세요 : ");
                continue;
            }
            return Integer.parseInt(answer);
        }
    }
}
