import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PasswordGenerator {
    
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()-_=+<>?/{}[]|";
    private static final String AMBIGUOUS_CHARACTERS = "l1O0";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        System.out.println("Enter desired password length:");
        int length = scanner.nextInt();
        scanner.nextLine();  

        System.out.println("Include lowercase letters? (y/n):");
        boolean includeLowercase = scanner.nextLine().equalsIgnoreCase("y");

        System.out.println("Include uppercase letters? (y/n):");
        boolean includeUppercase = scanner.nextLine().equalsIgnoreCase("y");

        System.out.println("Include digits? (y/n):");
        boolean includeDigits = scanner.nextLine().equalsIgnoreCase("y");

        System.out.println("Include special characters? (y/n):");
        boolean includeSpecialCharacters = scanner.nextLine().equalsIgnoreCase("y");

        System.out.println("Avoid ambiguous characters (e.g., 'l', '1', 'O', '0')? (y/n):");
        boolean avoidAmbiguous = scanner.nextLine().equalsIgnoreCase("y");

       
        String password = generatePassword(length, includeLowercase, includeUppercase, includeDigits, includeSpecialCharacters, avoidAmbiguous);
        System.out.println("Generated password: " + password);
    }

    private static String generatePassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeDigits, boolean includeSpecialCharacters, boolean avoidAmbiguous) {
        if (length <= 0 || (!includeLowercase && !includeUppercase && !includeDigits && !includeSpecialCharacters)) {
            throw new IllegalArgumentException("Invalid password criteria");
        }

        StringBuilder characterSet = new StringBuilder();
        List<Character> requiredCharacters = new ArrayList<>();
        
        if (includeLowercase) {
            characterSet.append(LOWERCASE);
            requiredCharacters.add(getRandomCharacter(LOWERCASE));
        }
        if (includeUppercase) {
            characterSet.append(UPPERCASE);
            requiredCharacters.add(getRandomCharacter(UPPERCASE));
        }
        if (includeDigits) {
            characterSet.append(DIGITS);
            requiredCharacters.add(getRandomCharacter(DIGITS));
        }
        if (includeSpecialCharacters) {
            characterSet.append(SPECIAL_CHARACTERS);
            requiredCharacters.add(getRandomCharacter(SPECIAL_CHARACTERS));
        }

        if (avoidAmbiguous) {
            for (char c : AMBIGUOUS_CHARACTERS.toCharArray()) {
                int index;
                while ((index = characterSet.indexOf(String.valueOf(c))) != -1) {
                    characterSet.deleteCharAt(index);
                }
            }
        }

        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length - requiredCharacters.size(); i++) {
            int index = random.nextInt(characterSet.length());
            password.append(characterSet.charAt(index));
        }

        
        for (char c : requiredCharacters) {
            int index = random.nextInt(password.length() + 1);
            password.insert(index, c);
        }

        return password.toString();
    }

    private static char getRandomCharacter(String characterSet) {
        SecureRandom random = new SecureRandom();
        int index = random.nextInt(characterSet.length());
        return characterSet.charAt(index);
    }
}
