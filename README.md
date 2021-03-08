# CashRegisterChallenge
This is the CashRegisterChallenge submission for Pineapple Payments. To run, you must compile all Java classes and run:
```
java CashRegisterRunner input.txt
```
If you wish to test this on another input file, replace input.txt with the alternate file name.
## CashRegisterChallenge.java
This is the primary project class. This performs all the main logic by reading an input file and returning an output file containing the coin denominations. This must have the CashRegisterDenomination class to run properly.
## CashRegisterDenomination.java
This is an object class that is used to hold information of each type of coin denomination including its value, how many are included in the current input line, and its name. New types of denominations may be added simply by creating a new class object and adding it to the coinArray in CashRegisterChallenge.java.
## CashRegisterDriver.java
This is the driver of the project that is used to run CashRegisterChallenge. Along with printing out the result of CashRegisterChallenge, this will also calculate the value of the output in cents and compare it to the change to ensure that they are equal. If not, it will print out where the discrepency lies within the output and calculated change amount.