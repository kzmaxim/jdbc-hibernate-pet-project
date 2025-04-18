package model.utils;

public class CardUtils {

    public static String generateCardNumber(String prefix, int length) {
        StringBuilder number = new StringBuilder(prefix);
        while (number.length() < length - 1) {
            number.append((int) (Math.random() * 10));
        }

        int checkDigit = calculateLuhnCheckDigit(number.toString());
        number.append(checkDigit);
        return number.toString();
    }

    public static int calculateLuhnCheckDigit(String numberWithoutCheckDigit) {
        int sum = 0;
        boolean alternate = true;
        for (int i = numberWithoutCheckDigit.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(numberWithoutCheckDigit.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (10 - (sum % 10)) % 10;
    }

    public static boolean isValidLuhn(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Character.getNumericValue(number.charAt(i));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return sum % 10 == 0;
    }
}

