package com.company;

import java.util.ArrayList;

/**This class represents a Bank information like accounts and clients
 * It store the information of each client and his account
 * @author Mohamed Ali
 * @version 1.00.00 29/4/2021
 */
public class Bank {


    private String name;
    private String Address;
    private String phone;
    private ArrayList<Account> accounts;
    private ArrayList<Client> clients;

    /**Default constructor that make initialization for accounts and clients Arraylists*/
    public Bank() {
        name = Address = phone = "";
        accounts = new ArrayList<Account>(0);
        clients = new ArrayList<Client>(0);

    }

    /**
     * Parametrize constructor set the values to the data members and making initialization for account,clients arraylists
     * @param name to set Bank name
     * @param address to set Bank address
     * @param phone to set Bank address
     */
    public Bank(String name, String address, String phone) {
        this.name = name;
        Address = address;
        this.phone = phone;

        accounts = new ArrayList<Account>(0);
        clients = new ArrayList<Client>(0);

    }

    /**
     * The getName Method returns the name of the Bank
     * @return returns the value of the Bank name
     */
    public String getName() {
        return name;
    }

    /**
     * The setName Method Assign the given value to the Bank Name
     * @param name is the value to set Bank Name
     */
    public void setName(String name) {
        this.name = name;
    }

    /** The getAddress method returns the Address of the Bank
     * @return returns the value of the Bank Address
     */
    public String getAddress() {
        return Address;
    }

    /** The setAddress Method Assign the  Address of the Bank
     *  @param address is the value to set Bank Address
     */

    public void setAddress(String address) {
        Address = address;
    }

    /** The getPhone method returns the phone of the Bank
     * @return returns the value of the Bank Phone
     */
    public String getPhone() {
        return phone;
    }


    /** The setPhone Method Assign the given value to the Bank Phone
     * @param phone is the value to set Bank Phone
     */

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** The AddClient Method Add a given client and account to the arraylist of client and account
     * @param client is the value to add to clients arraylist and its account of this client to the accounts arraylist
     */
    public void AddClient(Client client) {
        clients.add(client);
        accounts.add(client.getAccount());
    }

    /** The GetClient method returns need Client from Clients arraylist
     * @return returns the value of the Bank Client if found, if not found it's return null
     */
    public Client GetClient(String accountNum) {
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getAccount().getAccountNum().equals(accountNum)) {
                return clients.get(i);
            }
        }
        return null;
    }

    /** The GetAccount method returns need account of the client form accounts arraylist
     * @return returns the value of the Bank Client's account if found, if not found it returns null
     */
    public Account GetAccount(String accountNum) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNum().equals(accountNum)) {
                return accounts.get(i);
            }
        }
        return null;
    }


    /**The DISPLAY method is represent all data in the client Arraylist*/
    public void DISPLAY() {
        for (int i = 0; i < clients.size(); i++) {
            System.out.println(clients.get(i));
        }
    }
}

