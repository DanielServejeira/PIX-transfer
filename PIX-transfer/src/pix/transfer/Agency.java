/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;

import java.util.ArrayList;

/**
 *
 * @author Daniel Servejeira
 */
public class Agency {
    private final String number;
    private final ArrayList<BankAccount> bankAccounts;

    public Agency(String number) {
        this.number = number;
        this.bankAccounts = new ArrayList<>();
    }

    public String getNumber() {
        return number;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
    
    public void addBankAccount(BankAccount account) {
        bankAccounts.add(account);
    }
    
    public void removeBankAccount(BankAccount account) {
        bankAccounts.remove(account);
    }
}
