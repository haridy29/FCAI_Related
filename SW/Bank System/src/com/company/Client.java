package com.company;

/**
 * This class represent Bank's clients
 * It represent the information of each client in the bank system
 * @author Sara Alaa
 * @version 1.00.00 29/4/2021
 */


public class Client {
    private String name;
    private String NationalID;
    private String Address;
    private String Phone;
    private Account account;

    /** Default constructor if the client doesn't take any parameters. */
    public Client() { }

    /** constructor that sets the client informations
     * @param name to set Client name
     * @param nationalID to set Client National ID
     * @param address to set Client Address
     * @param phone to set Client Phone
     * @param account to set Client Account
     * this class make composition relation with class Account to set any client with his account information
     */

    public Client(String name, String nationalID, String address, String phone, Account account) {
        this.name = name;
        this.NationalID = nationalID;
        this.Address = address;
        this.Phone = phone;
        this.account = account;
    }


    /** getName method returns name of client.
     *@return returns name of client
     */
    public String getName() {
        return name;
    }

    /** getNationalID method returns NationalID of client.
     *@return returns NationalID of client*/
    public String getNationalID() {
        return NationalID;
    }

    /** getAddress method returns Address of client.
     *@return returns Address of client
     */
    public String getAddress() {
        return Address;
    }

    /** getPhone method returns Phone of client.
     *@return returns the Phone of the client
     */
    public String getPhone() {
        return Phone;
    }

    /** getAccount method returns account of client.
     *@return returns the account of the client
     */
    public Account getAccount() {
        return account;
    }

    /** setName method set the name of client.
     *@param name which is the name of client
     */
    public void setName(String name) {
        this.name = name;
    }

    /** setAddress method set the address of client.
     *@param address which is the address of client
     */
    public void setAddress(String address) {
        Address = address;
    }

    /** setPhone method set the phone of client.
     *@param phone which is the phone of client
     */
    public void setPhone(String phone) {
        Phone = phone;
    }

    /** setAccount method set the account information of client.
     *@param account which is the account information of client.
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /** setNationalID method set the NationalID of client.
     *@param nationalID which is the nationalID of client
     */
    public void setNationalID(String nationalID) {
        NationalID = nationalID;
    }

    /** override method toString that extended from class object to display the client information and his account information.
     *@return returns the client information and information of his account.
     */
    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", NationalID='" + NationalID + '\'' +
                ", Address='" + Address + '\'' +
                ", Phone='" + Phone + '\'' +
                ", account=" + account;


    }


}
