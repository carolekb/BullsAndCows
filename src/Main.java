import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String wpis;
        do {
            String secret = "1234";
            int cows = 0;
            int bulls = 0;
            wpis = scanner.nextLine();
            for (int i = 0; i < secret.length(); i++) {
                if (wpis.charAt(i) == secret.charAt(i)) {
                    bulls++;
                }
                for (int j = 0; j < wpis.length(); j++) {
                    if (i == j) continue;
                    if (wpis.charAt(j) == secret.charAt(i)) {
                        cows++;
                    }
                }
            }
            if (cows > 0 & bulls > 0) {
                System.out.printf("Grade: %d bull(s) %d cow(s). The secret code is %s.\n", bulls, cows, wpis);
            } else if (cows > 0) {
                System.out.printf("Grade: %d cow(s). The secret code is %s.\n", cows, wpis);
            } else if (bulls > 0) {
                System.out.printf("Grade: %d bull(s). The secret code is %s.\n", bulls, wpis);
            } else {
                System.out.printf("Grade: None. The secret code is %s.\n", wpis);
            }
        }while(wpis.charAt(0) != 0);
    }
}