import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CashRegisterChallengeClient {

// Currency values
private final static CashRegisterDenomination DOLLAR = new CashRegisterDenomination("dollar", "dollars", 100);
private final static CashRegisterDenomination QUARTER = new CashRegisterDenomination("quarter", "quarters", 25);
private final static CashRegisterDenomination DIME = new CashRegisterDenomination("dime", "dimes", 10);
private final static CashRegisterDenomination NICKEL = new CashRegisterDenomination("nickel", "nickels", 5);
private final static CashRegisterDenomination PENNY = new CashRegisterDenomination("penny", "pennies", 1);

// Array containing all denominations
private final static int CURRENCY_ARRAY_LENGTH = 5;

    /**
     * Performs the file read in and returns the 
     * @param input - the file to be read in
     * @return - the resulting change denominations
     */
    public static String returnString(String input) {
        String result = "";
        // Read in file (FOE exceptions as well)
        try {
            File f = new File(input);
            Scanner scanner = new Scanner(f);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    String[] parsedLine = line.split(",");
                    double moneyOwedRaw = Double.parseDouble(parsedLine[0]);
                    int moneyOwed = convertToInt(moneyOwedRaw);
                    double moneyReceivedRaw = Double.parseDouble(parsedLine[1]);
                    int moneyReceived = convertToInt(moneyReceivedRaw);
                    if (isDivByThree(moneyOwed)) {

                    }
                    else {
                        result += calculateChange(moneyOwed, moneyReceived);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred, file not found.");
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            System.out.println("An error occurred, illegal formatting of input.");
            e.printStackTrace();
            return "";
        }
        return result;
    }

    /**
     * Calculates the minimum amount of change needed to return
     * @param moneyOwed - amount of money required from customer
     * @param moneyReceived - amount of money received from customer
     * @return - the minimum amount of physical change in String form
     */
    public static String calculateChange(int moneyOwed, int moneyReceived) {
        int change = moneyReceived - moneyOwed;
        if (change < 0) {
            return "Not enough money received.\n";
        }
        if (change == 0) {
            return "No change required.\n";
        }
        CashRegisterDenomination[] coinResult = {DOLLAR, QUARTER, DIME, NICKEL, PENNY};
        coinResult = minCoins(coinResult, change);
        String result = resultToString(coinResult);
        for (CashRegisterDenomination coin : coinResult) {
            coin.resetNumberOfCoins();
        }
        return result;
    }

    /**
     * 
     * @param coins - the number of coins counted to return as change
     * @param change - the value of change needed to be returned
     * @return - the array of each number of total coins to be returned
     */
    public static CashRegisterDenomination[] minCoins(CashRegisterDenomination[] coins, int change) {
        for (int i = 0; i < CURRENCY_ARRAY_LENGTH; i++) {
            while (coins[i].getValue() <= change) {
                coins[i].incrementNumberOfCoins();
                change -= coins[i].getValue();
            }
        }
        return coins;
    }

    /**
     * 
     * @param num - the value to check
     * @return - true if the value is divisible by 3, false if otherwise
     */
    public static boolean isDivByThree(int num) {
        if (num % 3 == 0) {
            return true;
        }
        return false;
    }

    /**
     * 
     * @param num - the value to convert
     * @return - the value in integer form
     */
    public static int convertToInt(double num) {
        return (int) (num * 100);
    }

    public static String resultToString(CashRegisterDenomination[] resultArray) {
        String result = "";
        boolean first = true; // use this to check when the first denomination is used (mainly for comma usage)
        for (CashRegisterDenomination coin : resultArray) {
            if (!first && coin.getNumberOfCoins() > 0) {
                result += ",";
            }
            if (coin.getNumberOfCoins() == 1) {
                result += "1 " + coin.getNameSingular();
            }
            if (coin.getNumberOfCoins() > 1) {
                result += coin.getNumberOfCoins() + " " + coin.getNamePlural();
            }
            if (first && coin.getNumberOfCoins() > 0) {
                first = false;
            }
        }
        result += "\n";
        return result;
    }
}