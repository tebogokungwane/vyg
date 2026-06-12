package com.vyg.dsa.practice;

public class BankMain {

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.balance = 1000;
        bank.balance = -9999;

        Bank2 bank2 = new Bank2();
        bank2.setBalance(2000);
        bank2.setBalance(-99999);

        System.out.println("Bank Balance is: " + bank.balance);
        System.out.println("Bank 2 Balance is: "+ bank2.getBalance());

    }
}
