/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author Daniel Servejeira
 */
public class Holder {
    private String cpf;
    private String name;
    private LocalDate birthDate;
    private final ArrayList<BankAccount> bankAccounts;

    public Holder(String cpf, String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.cpf = cpf;
        this.bankAccounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public ArrayList<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void addBankAccount(BankAccount bankAccount) {
        bankAccounts.add(bankAccount);
    }

    public void removeBankAccount(BankAccount bankAccount) {
        bankAccounts.remove(bankAccount);
    }
}
