public class CashRegisterDenomination {
    private String nameSingular;
    private String namePlural;
    private int value;
    private int numberOfCoins;

    public CashRegisterDenomination(String nameSingular, String namePlural, int value) {
        this.nameSingular = nameSingular;
        this.namePlural = namePlural;
        this.value = value;
        this.numberOfCoins = 0;
    }
    
    public void incrementNumberOfCoins() {
        this.numberOfCoins++;
    }

    public void addNumberOfCoins(int n) {
        this.numberOfCoins += n;
    }

    public void resetNumberOfCoins() {
        this.numberOfCoins = 0;
    }

    public String getNameSingular() {
        return this.nameSingular;
    }

    public String getNamePlural() {
        return this.namePlural;
    }

    public int getValue() {
        return this.value;
    }
    
    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }
}