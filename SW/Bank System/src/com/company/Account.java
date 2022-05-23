package com.company;

/**
 * This class is used to set the the account information of the client
 * @author Menna Hamdy Mahmoud
 * @version 1.00.00 29/4/2021
 */

public class Account {

    private double balance;
    private String AccountNum;


    /** Default constructor
     */
    public Account() {
        balance = 0;
        AccountNum = null;
    }

    /** A parameterized constructor take the account number from user and put initial value of the balance
     * @param balance  variable takes to set the balance
     * @param AccountNum variable takes to set the AccountNum
     */
    public Account(double balance, String AccountNum) {
        this.balance = balance;
        this.AccountNum = AccountNum;
    }

    /** method that return the value of the balance
     * @return return the value of the balance
     */

    public double getBalance() {return balance;}

    /**setter method to set the value of the balance
     * @param balance variable takes to set to the balance
     */
    public void setBalance(double balance) { this.balance = balance; }

    /**method that return the value of the account number
     * @return return the value of the Account number
     */

    public String getAccountNum() {return AccountNum; }


    /** setter method to set the value of teh account number
     * @param accountNum parameter which use to set the value of the account number
     */
    public void setAccountNum(String accountNum){AccountNum = accountNum; }

    /**Override the method toString ( ) inherited from class Object to make it return a meaningful string representation of the account information.
     * @return a meaningful string representation of the account information.
     */
    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                ", AccountNum='" + AccountNum + '\'' +
                '}';
    }
    /**method enable the client to deposit money to his account
     * @param amount the value that he want to put to his account
     */
    public void Deposit(double amount) {balance = balance + amount; }
    /**method enable client to be able to withdraw from his account
     * @param  amount the value that he want to take from his balance
     */

    public void Withdraw(double amount) {
        if (balance >= amount) {
            balance = balance - amount;
        } else {
            System.out.println("Insufficient balance");
        }
    }
}
