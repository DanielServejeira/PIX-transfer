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
public class Bank {
    private String name;
    private ArrayList<Integer> agencies;
    private ArrayList<BankAccount> bankAccounts;

    public Bank(String name) {
        this.name = name;
        this.agencies = new ArrayList<>();
        this.bankAccounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getAgencies() {
        return agencies;
    }

    public void setAgencies(ArrayList<Integer> agencies) {
        this.agencies = agencies;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
    
    public void addAgency(int agency) {
        agencies.add(agency);
    }

    public void removeAgency(int agency) {
        agencies.remove(agency);
    }
    
    public void addAccount(BankAccount account) {
        bankAccounts.add(account);
    }
    
    public void removeAccount(BankAccount account) {
        bankAccounts.remove(account);
    }

    public BankAccount findByPixKey(String pixKey) {
        return bankAccounts.stream()
                .filter(acc -> acc.getPixKey().equals(pixKey))
                .findFirst()
                .orElse(null);
    }
}
