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
    private final String number;
    private BigDecimal balance;
    private String pixKey;
    
    public BankAccount(String number, String pixKey) {
        this.number = number;
        balance = new BigDecimal("0.00");
        this.pixKey = pixKey;
    }

    public String getNumber() {
        return number;
    }

    public BigDecimal getBalance() {
        return balance;
    }
    
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPixKey() {
        return pixKey;
    }

    public void setPixKey(String pixKey) {
        this.pixKey = pixKey;
    }
    
    public void debit(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        }
    }
    
    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
