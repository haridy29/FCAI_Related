package com.company;
/** This class is used to set special type of accounts that allow the client to withdraw till -1000
 * @author Menna Hamdy Mahmoud
 * @version 1.00.00 29/4/2021
 */
public class SpecialAccount extends Account {


    /**
     * Default constructor
     */
    public SpecialAccount() { }

    /**
     * A parameterized constructor take the account number from user and put initial value of the balance
     * @param balance variable takes to set the blance
     * @param AccountNum variable takes to set the account number
     */
    public SpecialAccount(double balance, String AccountNum) {
        super(balance, AccountNum);
    }

    /**
     *  override method withdraw to allow over drafting with maximum limit of 1000 L.E
     * @param amount the value that he want to take from his account
     */
    @Override
    public void Withdraw(double amount) {


        if (amount <= super.getBalance() + 1000) {
            super.setBalance(super.getBalance() - amount);
        } else {
            System.out.println("Insufficient balance");
        }
    }

}
