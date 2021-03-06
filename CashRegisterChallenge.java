import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;

public class CashRegisterChallenge {

// Currency values
private final static CashRegisterDenomination DOLLAR = new CashRegisterDenomination("dollar", "dollars", 100);
private final static CashRegisterDenomination QUARTER = new CashRegisterDenomination("quarter", "quarters", 25);
private final static CashRegisterDenomination DIME = new CashRegisterDenomination("dime", "dimes", 10);
private final static CashRegisterDenomination NICKEL = new CashRegisterDenomination("nickel", "nickels", 5);
private final static CashRegisterDenomination PENNY = new CashRegisterDenomination("penny", "pennies", 1);

// Array containing all denominations (must be in descending order)
private final static CashRegisterDenomination[] coinArray = {DOLLAR, QUARTER, DIME, NICKEL, PENNY};

// Array length
private final static int CURRENCY_ARRAY_LENGTH = 5;

    /**
     * Performs the file read in and returns the output of denominations within a string
     * @param input - the file to be read in
     * @return - the resulting change denominations
     */
    public static String returnString(String input) {
        String result = "";
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
                    // Twist condition specified here
                    if (isDivByThree(moneyOwed)) {
                        result += calculateChangeRandom(moneyOwed, moneyReceived);
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
     * @return - the minimum amount of physical change
     */
    public static String calculateChange(int moneyOwed, int moneyReceived) {
        int change = moneyReceived - moneyOwed;
        if (change < 0) {
            return "Not enough money received.\n";
        }
        if (change == 0) {
            return "No change required.\n";
        }
        CashRegisterDenomination[] coinResult = coinArray;
        coinResult = minCoins(coinResult, change);
        String result = resultToString(coinResult);
        for (CashRegisterDenomination coin : coinResult) {
            coin.resetNumberOfCoins();
        }
        return result;
    }

    /**
     * Calculates change by randomly selecting how many of each denomination to return
     * @param moneyOwed - amount of money required from customer
     * @param moneyReceived - amount of money received from customer
     * @return - a random amount of physical change
     */
    public static String calculateChangeRandom(int moneyOwed, int moneyReceived) {
        int change = moneyReceived - moneyOwed;
        if (change < 0) {
            return "Not enough money received.\n";
        }
        if (change == 0) {
            return "No change required.\n";
        }
        CashRegisterDenomination[] coinResult = coinArray;
        coinResult = randomCoins(coinResult, change);
        String result = resultToString(coinResult);
        for (CashRegisterDenomination coin : coinResult) {
            coin.resetNumberOfCoins();
        }
        return result;
    }

    /**
     * Calculates and returns and array of each coin (the minimum amount of physical change)
     * @param coins - the number of coins counted to return as change
     * @param change - the value of change needed to be returned
     * @return - the array of each number of total coins to be returned
     */
    public static CashRegisterDenomination[] minCoins(CashRegisterDenomination[] coins, int change) {
        for (int i = 0; i < CURRENCY_ARRAY_LENGTH; i++) {
            int maxCoins = change / coins[i].getValue();
            if (maxCoins > 0) {
                coins[i].addNumberOfCoins(maxCoins);
                change -= coins[i].getValue() * maxCoins;
            }
        }
        return coins;
    }

    /**
     * Calculates and returns and array of each coin (a random amount of physical change)
     * @param coins - the number of coins counted to return as change
     * @param change - the value of change needed to be returned
     * @return - the array of each number of total coins to be returned
     */
    public static CashRegisterDenomination[] randomCoins(CashRegisterDenomination[] coins, int change) {
        Random random = new Random();
        for (int i = 0; i < CURRENCY_ARRAY_LENGTH; i++) {
            if (i == CURRENCY_ARRAY_LENGTH - 1) {
                coins[i].addNumberOfCoins(change);
            }
            else {
                int maxCoins = change / coins[i].getValue();
                if (maxCoins > 0) {
                    int randomCoins = random.nextInt(maxCoins + 1);
                    coins[i].addNumberOfCoins(randomCoins);
                    change -= coins[i].getValue() * randomCoins;
                }
            }
        }
        return coins;
    }

    /**
     * The "special twist" condition. Checks if the amount is divisible by three.
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
     * Converts the double value to an integer (in cents). Used to determine the twist condition.
     * @param num - the value to convert
     * @return - the value in integer form
     */
    public static int convertToInt(double num) {
        return (int) (num * 100);
    }

    /**
     * Converts the resulting coin array into a more readable string form
     * @param resultArray - the number of coins for each denomination
     * @return - the readable string form of that array
     */
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