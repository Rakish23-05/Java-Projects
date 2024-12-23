import java.util.*;

class LineCoding {
    
    public static String NRZ(String input) {
        StringBuilder result = new StringBuilder();
        for (char bit : input.toCharArray()) {
            if (bit == '1') {
                result.append("1");
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }

    public static String Manchester(String input) {
        StringBuilder result = new StringBuilder();
        for (char bit : input.toCharArray()) {
            if (bit == '1') {
                result.append("10");
            } else {
                result.append("01");
            }
        }
        return result.toString();
    }

    public static String RZ(String input) {
        StringBuilder result = new StringBuilder();
        for (char bit : input.toCharArray()) {
            if (bit == '1') {
                result.append("10");
            } else {
                result.append("01");
            }
        }
        return result.toString();
    }

    public static String AMI(String input) {
        StringBuilder result = new StringBuilder();
        int lastBit = 1;
        for (char bit : input.toCharArray()) {
            if (bit == '1') {
                if (lastBit == 1) {
                    result.append("1");
                    lastBit = -1;
                } else {
                    result.append("0");
                    lastBit = 1;
                }
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }

    public static String Bipolar(String input) {
        StringBuilder result = new StringBuilder();
        int lastBit = 1;
        for (char bit : input.toCharArray()) {
            if (bit == '1') {
                if (lastBit == 1) {
                    result.append("1");
                    lastBit = -1;
                } else {
                    result.append("-1");
                    lastBit = 1;
                }
            } else {
                result.append("0");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        String input = "110101011";
        System.out.println("Input: " + input);
        System.out.println("NRZ: " + NRZ(input));
        System.out.println("Manchester: " + Manchester(input));
        System.out.println("RZ: " + RZ(input));
        System.out.println("AMI: " + AMI(input));
        System.out.println("Bipolar: " + Bipolar(input));
    }
}
