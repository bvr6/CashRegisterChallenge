import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Arrays;
public class CashRegisterDriver {
    public static void main(String[] args) {
        String result = CashRegisterChallenge.returnString(args[0]);
        System.out.println(result);
        String[] allOutputs = result.split("\n");
        int[] coinResult = coinCounter(allOutputs);
        int[] countedResult = new int[coinResult.length];
        int counter = 0;
        try {
            File f = new File(args[0]);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    String[] parsedLine = line.split(",");
                    double moneyOwedRaw = Double.parseDouble(parsedLine[0]);
                    double moneyReceivedRaw = Double.parseDouble(parsedLine[1]);
                    int moneyOwed = CashRegisterChallenge.convertToInt(moneyOwedRaw);
                    int moneyReceived = CashRegisterChallenge.convertToInt(moneyReceivedRaw);
                    countedResult[counter] = moneyReceived - moneyOwed;
                    counter++;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Error in driver. Cannot find file.");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("Error in driver.");
            e.printStackTrace();
        }
        for (int i = 0; i < coinResult.length; i++) {
            if (coinResult[i] == countedResult[i]) {
                // System.out.println("Correct");
            }
            else {
                System.out.println("Incorrect for output: " + allOutputs[i]);
            }
        }
    }

    /**
     * Counts the coins from the readable string form back to an array of each number of coins
     * @param outputArray - the string form of denominations
     * @return - an array that counts how many of each coin there is
     */
    public static int[] coinCounter(String[] outputArray) {
        int[] result = new int[outputArray.length];
        Arrays.fill(result, 0);
        for (int i = 0; i < outputArray.length; i++) {
            String[] splitEntry = outputArray[i].split(",");
            for (String entry : splitEntry) {
                if (entry.indexOf("dollar") != -1) {
                    entry = entry.replaceAll(" dollars", "");
                    entry = entry.replaceAll(" dollar", "");
                    result[i] += 100 * Integer.parseInt(entry);
                }
                if (entry.indexOf("quarter") != -1) {
                    entry = entry.replaceAll(" quarters", "");
                    entry = entry.replaceAll(" quarter", "");
                    result[i] += 25 * Integer.parseInt(entry);
                }
                if (entry.indexOf("dime") != -1) {
                    entry = entry.replaceAll(" dimes", "");
                    entry = entry.replaceAll(" dime", "");
                    result[i] += 10 * Integer.parseInt(entry);
                }
                if (entry.indexOf("nickel") != -1) {
                    entry = entry.replaceAll(" nickels", "");
                    entry = entry.replaceAll(" nickel", "");
                    result[i] += 5 * Integer.parseInt(entry);
                }
                if (entry.indexOf("penn") != -1) {
                    entry = entry.replaceAll(" pennies", "");
                    entry = entry.replaceAll(" penny", "");
                    result[i] += Integer.parseInt(entry);
                }
            }
        }
        return result;
    }
}