
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) throws InputMismatchException{
        boolean error;
        long pseudoRandomNumber = System.nanoTime();
        Random random = new Random(pseudoRandomNumber);
        Scanner scanner = new Scanner(System.in);
        String wpis;
        int length, nOfSymbols;
        StringBuilder number = new StringBuilder();
        do {
            error = false;
            do {
                System.out.println("Input the length of the secret code: (1-36)");
                wpis = scanner.nextLine();
                length = checkInput(wpis);
            }while(length == -1);
            if (length < 1) {
                System.out.println("Error: length must be more than 0.");
                error = true;
            } else if (length > 36) {
                System.out.printf("Error: can't generate a secret number with a length of %d because \n", length);
                System.out.println("there aren't enough unique digits.");
                error = true;
            }
        }while(error);
        do {
            error = false;
            do {
                System.out.println("Input the number of possible symbols in the code:");
                wpis = scanner.nextLine();
                nOfSymbols = checkInput(wpis);
            }while(nOfSymbols == -1);
            if (nOfSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                error = true;
            }
            if (nOfSymbols < length) {
                System.out.printf("Error: it's not possible to generate a code with" +
                        " a length of %d with %d unique symbols.\n", length, nOfSymbols);
                error = true;
            }
        }while(error);
        System.out.println("Okay, let's start a game!");
        StringBuilder stars = new StringBuilder();
        stars.append("*".repeat(length));
        stars.append(" (0-");
        if(nOfSymbols <= 10) {
            stars.append(nOfSymbols - 1);
        }
        if(nOfSymbols > 10){
            stars.append("9, a");
        }
        if(nOfSymbols > 11){
            stars.append("-");
            stars.append((char) (nOfSymbols + 86));
        }
        stars.append(")");
        System.out.printf("The secret is prepared: %s\n", stars);

        char[] tab = new char[nOfSymbols];
        for (int i = 0; i < tab.length; i++) {
            boolean flag;
            char r;
            do {
                flag = false;
                r = (char) random.nextInt(nOfSymbols);
                if(r > 9) {
                    r += 87;
                }else{
                    r += 48;
                }
                for (char c : tab) {
                    if (c == r) {
                        flag = true;
                        break;
                    }
                }
            }while(flag);
            tab[i] = r;
        }

        //  -----------  now we have array of chars tab that is filled with nOfSymbols chars (or digits)
        number.append(tab[random.nextInt(nOfSymbols)]);
        for(int i = 1 ; i < length ; i++){
            boolean flag;
            char r;   // --  index of array tab
            do {
                flag = false;
                r = tab[random.nextInt(nOfSymbols)];
                for (int j = 0; j < number.length(); j++) {
                    if (number.charAt(j)  == r) {
                        flag = true;
                        break;
                    }
                }
            }while(flag);
            number.append(r);
        }

        grade(number.toString(), tab, stars);

    }
    public static void grade(String secret, char[] tab, StringBuilder stars){
        Scanner scanner = new Scanner(System.in);
        String wpis;
        int no = 1;
        do {
            int cows = 0;
            int bulls = 0;
            System.out.printf("Turn %d:", no);
            boolean flag;
            do {
                flag = false;
                wpis = scanner.nextLine();
                if(wpis.length() != secret.length()){
                    System.out.printf("input %d chars:\n", secret.length());
                    flag = true;
                    continue;
                }
                for(int i = 0 ; i < wpis.length() - 1; i++){
                    for(int j = i + 1; j < wpis.length() ; j++){
                        if(wpis.charAt(i) == wpis.charAt(j)){
                            System.out.println("symbols must be unique");
                            flag = true;
                            break;
                        }
                    }
                    if(flag) break;
                }
                if (flag) continue;
                for(int i = 0 ; i < wpis.length() ; i++) {
                    flag = true;
                    for (char c : tab) {
                        if (wpis.charAt(i) == c) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag) {
                        System.out.println("Symbols myst be in range: " + stars.delete(0, secret.length()));
                        break;
                    }
                }
            }while(flag);
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
            if(bulls == secret.length()){
                System.out.println("Grade: " + bulls + " bulls\n" +
                        "Congratulations! You guessed the secret code.");
                System.exit(0);
            } else if (cows > 0 & bulls > 0) {
                System.out.printf("Grade: %d %s %d %s\n", bulls, bulls > 1 ?  "bulls" : "bull", cows, cows > 1 ? "cows" : "cow");
            } else if (cows > 0) {
                System.out.printf("Grade: %d %s\n", cows, cows > 1 ? "cows" : "cow");
            } else if (bulls > 0) {
                System.out.printf("Grade: %d %s\n", bulls, bulls > 1 ?  "bulls" : "bull");
            } else {
                System.out.println("Grade: None");
            }
            no++;
        }while(true);
    }

    public static int checkInput(String wpis){
        int number = 0;
        for(int i = 0 ; i < wpis.length() ; i++){
            if(wpis.charAt(i) < 48 || wpis.charAt(i) > 57 ){
                System.out.printf("Error: \"%s\" isn't a valid number.\n", wpis);
                return -1;
            }
        }
        for(int i = 0 ; i < wpis.length() ; i++){
            number = number * 10;
            number = number + wpis.charAt(i) - 48;
        }
        return number;
    }
}