/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;
import java.math.BigDecimal;

/**
 *
 * @author Daniel Servejeira
 */
public class BankAccount {
    private String number;
    private User user;
    private Bank bank;
    private BigDecimal balance;
    
    public BankAccount(String number, User user, Bank bank) {
        this.number = number;
        this.user = user;
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    
    public void transfer() {
        
    }
}
