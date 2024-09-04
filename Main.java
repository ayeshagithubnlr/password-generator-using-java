import java.security.SecureRandom;
import java.util.Scanner;

class Main {
    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_+=<>?";

    private static SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the desired password length:");
        int length = scanner.nextInt();

        System.out.println("Include uppercase letters? (true/false):");
        boolean includeUppercase = scanner.nextBoolean();

        System.out.println("Include lowercase letters? (true/false):");
        boolean includeLowercase = scanner.nextBoolean();

        System.out.println("Include digits? (true/false):");
        boolean includeDigits = scanner.nextBoolean();

        System.out.println("Include special characters? (true/false):");
        boolean includeSpecial = scanner.nextBoolean();

        try {
            String password = generatePassword(length, includeUppercase, includeLowercase, includeDigits, includeSpecial);
            System.out.println("Generated Password: " + password);
            System.out.println("Password Strength: " + assessPasswordStrength(password));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }

    public static String generatePassword(int length, boolean includeUppercase, boolean includeLowercase, boolean includeDigits, boolean includeSpecial) {
        StringBuilder characterPool = new StringBuilder();
        StringBuilder password = new StringBuilder(length);

        if (includeUppercase) characterPool.append(UPPERCASE);
        if (includeLowercase) characterPool.append(LOWERCASE);
        if (includeDigits) characterPool.append(DIGITS);
        if (includeSpecial) characterPool.append(SPECIAL);

        if (characterPool.length() == 0) {
            throw new IllegalArgumentException("No characters available for password generation. Adjust the settings.");
        }

        // Ensure that at least one character from each selected category is included
        if (includeUppercase) password.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        if (includeLowercase) password.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        if (includeDigits) password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        if (includeSpecial) password.append(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Fill the remaining length with random characters from the pool
        for (int i = password.length(); i < length; i++) {
            password.append(characterPool.charAt(random.nextInt(characterPool.length())));
        }

        // Shuffle the password to ensure randomness
        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        for (int i = 0; i < characters.length; i++) {
            int randomIndex = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[randomIndex];
            characters[randomIndex] = temp;
        }
        return new String(characters);
    }

    public static String assessPasswordStrength(String password) {
        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*()-_+=<>?].*")) score++;

        if (score <= 2) return "Weak";
        if (score == 3 || score == 4) return "Medium";
        return "Strong";
    }
}
