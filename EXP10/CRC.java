import java.io.*;

public class CRC {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Generator:");
        String generator = br.readLine();

        System.out.println("Enter Data:");
        String data = br.readLine();

        String codeWord = generateCRC(data, generator);

        System.out.println("The transmitted Code Word is: " + codeWord);

        System.out.println("Please enter the received Code Word: ");
        String receivedCodeWord = br.readLine();

        if (checkCRC(receivedCodeWord, generator)) {
            System.out.println("The received code word contains no errors.");
        } else {
            System.out.println("The received code word contains errors.");
        }
    }

    static String generateCRC(String data, String generator) {
        String code = data;
        while (code.length() < (data.length() + generator.length() - 1)) {
            code += "0";
        }
        code += performDivision(code, generator);
        return code;
    }

    static boolean checkCRC(String receivedCodeWord, String generator) {
        return Integer.parseInt(performDivision(receivedCodeWord, generator)) == 0;
    }

    static String performDivision(String num1, String num2) {
        int pointer = num2.length();
        String result = num1.substring(0, pointer);
        String remainder = "";

        for (int i = 0; i < num2.length(); i++) {
            if (result.charAt(i) == num2.charAt(i)) {
                remainder += "0";
            } else {
                remainder += "1";
            }
        }

        while (pointer < num1.length()) {
            if (remainder.charAt(0) == '0') {
                remainder = remainder.substring(1) + num1.charAt(pointer);
                pointer++;
            }
            result = remainder;
            remainder = "";

            for (int i = 0; i < num2.length(); i++) {
                if (result.charAt(i) == num2.charAt(i)) {
                    remainder += "0";
                } else {
                    remainder += "1";
                }
            }
        }

        return remainder.substring(1);
    }
}
