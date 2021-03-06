public class CashRegisterRunner {
    public static void main(String[] args) {
        String s = CashRegisterChallengeClient.returnString(args[0]);
        System.out.println(s);
    }

    // TODO Count and compare coins, return boolean of arrays to ensure correctness
    public static int coinCounter(String[] sArray) {
        for (String s : sArray) {
            System.out.println(s);
        }
        return 0;
    }
}