package com.company;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        int BankChoice, ClientChoice, AccountChoice, choice;

        Scanner input = new Scanner(System.in);

        String name, nationalID = "", address, phone, commericalID = "";

        double balance;
        String accountNum;

        System.out.println("Enter Bank name, address and phone");

        name = input.next();
        address = input.next();
        phone = input.next();

        Bank bank = new Bank(name, address, phone);
        System.out.println("============================ Welcome in " + name + " =================================");

        while (true) {

            System.out.println("Please press from the following\n" + "1--> Add client\n" + "2--> Get client\n" + "3--> Display all clients\n" + "4-->Exit");
            BankChoice = input.nextInt();

            while (BankChoice > 4 || BankChoice < 0) {
                System.out.println("Please enter valid choice");
                BankChoice = input.nextInt();
            }

            if (BankChoice == 1) {
                System.out.println("Enter choice from the following \n" + "1--> Client\n" + "2--> Commercial Client");

                ClientChoice = input.nextInt();

                while (ClientChoice > 2 || ClientChoice < 0) {
                    System.out.println("Please enter valid choice");
                    ClientChoice = input.nextInt();

                }

                System.out.println("Please Enter the data in this format\n");

                if (ClientChoice == 1) {
                    System.out.println("Name | National ID | Address | Phone ");
                } else if (ClientChoice == 2) {
                    System.out.println("Name | Commerical ID | Address | Phone ");
                }

                name = input.next();

                if (ClientChoice == 1) {
                    nationalID = input.next();
                } else {
                    commericalID = input.next();
                }

                address = input.next();
                phone = input.next();
                Account account = new Account();

                System.out.println("Enter choice from the following \n" + "1--> Normal Account\n" + "2--> Special account");

                AccountChoice = input.nextInt();

                while (AccountChoice < 0 || AccountChoice > 2) {
                    System.out.println("Please enter valid choice");
                    AccountChoice = input.nextInt();
                }

                System.out.println("Enter the Balance and AccountNum");

                balance = input.nextDouble();
                accountNum = input.next();


                if (AccountChoice == 1) {
                    account = new Account(balance, accountNum);
                } else if (AccountChoice == 2) {
                    account = new SpecialAccount(balance, accountNum);
                }

                Client client = new Client();

                if (ClientChoice == 1) {
                    client = new Client(name, nationalID, address, phone, account);
                } else if (ClientChoice == 2) {
                    client = new CommercialClient(name, commericalID, address, phone, account);
                }

                bank.AddClient(client);

            } else if (BankChoice == 2) {
                System.out.println("Please, Enter Account number");
                boolean found = true;
                String accNum = input.next();

                if (bank.GetClient(accNum) == null) {
                    System.out.println("Client is Not found");
                    found = false;
                } else {
                    System.out.println(bank.GetClient(accNum).toString());
                }

                if (found) {
                    while (true) {
                        System.out.println("Enter choice from the following \n" + "1-->Withdraw\n" + "2-->Deposit\n" + "3-->Change Address\n" + "4-->Change PhoneNumber\n" + "5-->Main menu");
                        choice = input.nextInt();
                        while (choice < 0 || choice > 5) {
                            System.out.println("Please enter valid choice");
                            choice = input.nextInt();
                        }

                        if (choice == 1) {
                            System.out.println("Enter the amount you want to withdraw");
                            double amount = input.nextDouble();

                            bank.GetAccount(accNum).Withdraw(amount);
                            System.out.println(bank.GetAccount(accNum).toString());
                        } else if (choice == 2) {
                            System.out.println("Enter the amount you want to deposit");
                            double amount = input.nextDouble();

                            bank.GetAccount(accNum).Deposit(amount);
                            System.out.println(bank.GetAccount(accNum).toString());

                        } else if (choice == 3) {
                            System.out.println("Please Enter the new Address");
                            address = input.next();

                            bank.GetClient(accNum).setAddress(address);
                        } else if (choice == 4) {
                            System.out.println("Please Enter the new PhoneNumber");
                            phone = input.next();

                            bank.GetClient(accNum).setPhone(phone);
                        }
                        else
                            break;

                    }
                }

            } else if (BankChoice == 3) {
                bank.DISPLAY();
            } else {
                break;
            }
        }   //end while
    }
}//Mohamed 121212212 Giz 0114747
//aLI 1421444455 Cairo 123654
