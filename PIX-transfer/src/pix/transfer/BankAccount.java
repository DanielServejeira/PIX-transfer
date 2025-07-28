/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel Servejeira
 */
public class BankAccount {
    private final String number;
    private BigDecimal balance;
    private String pixKey;
    private final Holder holder;
    private final Agency agency;
    
    private final List<String> transferLog = new ArrayList<>();
    
    public BankAccount(String number, String pixKey, Holder holder, Agency agency) {
        this.number = number;
        balance = new BigDecimal("0.00");
        this.pixKey = pixKey;
        this.holder = holder;
        this.agency = agency;
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
    
    public Holder getHolder() {
        return holder;
    }

    public Agency getAgency() {
        return agency;
    }
    
    public void debit(BigDecimal amount) {
        if (balance.compareTo(amount) >= 0) {
            balance = balance.subtract(amount);
        }
    }
    
    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
    
    public void logTransfer(String type, BankAccount otherAccount, BigDecimal amount) {
        String logEntry = String.format(
            "[%s] %s R$%.2f %s conta %s",
            LocalDateTime.now(),
            type.equalsIgnoreCase("send") ? "Enviou" : "Recebeu",
            amount,
            type.equalsIgnoreCase("send") ? "para" : "de",
            otherAccount.getNumber()
        );
        transferLog.add(logEntry);
    }
    
    public List<String> getTransferLog() {
        return Collections.unmodifiableList(transferLog);
    }
}
