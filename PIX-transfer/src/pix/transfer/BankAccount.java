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
    private Holder holder;
    private BigDecimal balance;
    private String agency;
    private String pixKey;
    
    public BankAccount(String number, Holder holder, String agency, String pixKey) {
        this.number = number;
        this.holder = holder;
        balance = new BigDecimal("0.00");
        this.agency = agency;
        this.pixKey = pixKey;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Holder getHolder() {
        return holder;
    }

    public void setHolder(Holder holder) {
        this.holder = holder;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getPixKey() {
        return pixKey;
    }

    public void setPixKey(String pixKey) {
        this.pixKey = pixKey;
    }
    
    public boolean debit(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
            return true;
        }
        return false;
    }
    
    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
    
    @Override
    public String toString() {
        return String.format(
            "Titular: %s\nConta: %s\nAgência: %s\nChave PIX: %s\nSaldo: R$ %s",
            holder.getName(), number, agency, pixKey, balance.toPlainString()
        );
    }
}
