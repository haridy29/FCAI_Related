package com.company;
/**
 * This class represent special type of Bank's clients
 * It represent the information of special bank's clients called Commercial client.
 * Commercial client is a company not a person.
 * This class extend from class Client.
 * @author Sara Alaa
 *   @version 1.00.00 29/4/2021
 */
public class CommercialClient extends Client {

    private String commercialID;


    /** Default constructor that set the national id in super class(client class) with 000000000000.*/
    public CommercialClient() {
        super.setNationalID("0000000000000000");
    }

    /** Constructor that set the information of client(super class) and initialize the national id with 00000000
     * and set the commercialID with the given value*/

    public CommercialClient(String name, String commercialID, String address, String phone, Account account) {

        super(name, "0000000000000000", address, phone, account);
        this.commercialID = commercialID;
    }



    /** getCommercialID method returns commercialID of client which is instead of national id in class client.
     *@return returns commercialID of client
     */
    public String getCommercialID() {
        return commercialID;
    }
    /** setCommercialID method set the commercialID of client.
     *@param commercialID which is the commercialID of client
     */

    public void setCommercialID(String commercialID) {
        this.commercialID = commercialID;
    }


    /** override method toString that extended from class object to display all information of special client and
     *  information of his account.
     * @return returns the information of special client and information of his account.
     */
    @Override
    public String toString() {
        return "name='" + super.getName() + '\'' +
                ", Commerical ID='" + commercialID + '\'' +
                ", Address='" + super.getAddress() + '\'' +
                ", Phone='" + super.getPhone() + '\'' +
                ", account=" + super.getAccount();
    }
}
