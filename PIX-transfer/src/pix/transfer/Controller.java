/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;
import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDate;

/**
 *
 * @author Daniel Servejeira
 */
public class Controller {
    private final static ArrayList<Holder> holders = new ArrayList<>();
    private final static ArrayList<Bank> banks = new ArrayList<>();
    private final static Map<String, BankAccount> pixMap = new HashMap<>();
    
    public static void initialize() {
        Bank bank = new Bank("Banco do Brasil");

        Agency agency = new Agency("0001");

        Holder holder = new Holder("12345678900", "João da Silva", LocalDate.of(1990, 5, 22));

        BankAccount bankAccount = new BankAccount("10001", "joao@mail.com");

        agency.getBankAccounts().add(bankAccount);
        holder.getBankAccounts().add(bankAccount);

        bank.getAgencies().add(agency);

        banks.add(bank);
        holders.add(holder);
    }
    
    private static void credit(BankAccount account, BigDecimal amount) {
        account.credit(amount);
    }

    private static void debit(BankAccount account, BigDecimal amount) {
        account.debit(amount);
    }
    
    private static void validate(BankAccount account) {
        
    }
    
    public static void transferAmount(BankAccount sender, BankAccount receiver, BigDecimal amount) {
        //validar contas?
        debit(sender, amount);
        credit(receiver, amount);
    }
    
    public static void deposit(BankAccount account, BigDecimal amount) {
        validate(account);
        credit(account, amount);
    }
    
    public static List<Holder> getHolders() {
        return holders;
    }
    
    public static List<Bank> getBanks() {
        return banks;
    }
    
    public static Bank getBankByAgency(Agency agency) {
        for (Bank bank : banks) { ///////////////////?????????????????????
            if (bank.getAgencies().contains(agency)) {
                return bank;
            }
        }
        return null;
    }
    
    public static List<Agency> getAgencies(Bank bank) {
        return bank.getAgencies();
    }
    
    public static List<BankAccount> getBankAccountsByAgency(Agency agency) {
        return agency.getBankAccounts();
    }
    
    public static List<BankAccount> getBankAccountsByHolder(Holder holder) {
        return holder.getBankAccounts();
    }
    
    public static void addBank(Bank bank) {
        banks.add(bank);
    }

    public static void removeBank(Bank bank) {
        banks.remove(bank);
    }
    
    public static void addAgency(Agency agency, Bank bank) {
        bank.addAgency(agency);
    }

    public static void removeAgency(Agency agency, Bank bank) {
        bank.removeAgency(agency);
    }
    
    public static void addBankAccount(BankAccount bankAccount, Agency agency, Holder holder) {
        agency.addAccount(bankAccount);
        holder.addBankAccount(bankAccount);
    }

    public static void removeBankAccount(BankAccount bankAccount, Agency agency, Holder holder) {
        agency.removeAccount(bankAccount);
        holder.removeBankAccount(bankAccount);
    }
    
    public static void addHolder(Holder holder) {
        holders.add(holder);
    }
    
    public static void removeHolder(Holder holder) {
        holders.remove(holder);
    }
    
    public static BigDecimal getBalance(BankAccount bankAccount) {
        return bankAccount.getBalance();
    }
    
    public static String getPixKey(BankAccount bankAccount) {
        return bankAccount.getPixKey();
    }
    
    public static String getBankAccountNumber(BankAccount bankAccount) {
        return bankAccount.getNumber();
    }
    
    public static String getHolderName(Holder holder) {
        return holder.getName();
    }
    
    public static String getHolderCpf(Holder holder) {
        return holder.getCpf();
    }
    
    public static boolean bankAccountsIsEmpty(List bankAccounts) {
        return bankAccounts.isEmpty();
    }
    
    public static boolean banksIsEmpty(List banks) {
        return banks.isEmpty();
    }
    
    public static boolean agenciesIsEmpty(List agencies) {
        return agencies.isEmpty();
    }
    
    public static String getBankName(Bank bank) {
        return bank.getName();
    }
    
    public static String getAgencyNumber(Agency agency) {
        return agency.getNumber();
    }
    
    public static int getBankAccountsSize(List bankAccounts) {
        return bankAccounts.size();
    }
    
    public static int getBanksSize(List banks) {
        return banks.size();
    }
    
    public static int getAgenciesSize(List agencies) {
        return agencies.size();
    }
    
    public static int getHoldersSize(List holders) {
        return holders.size();
    }
    
    public static boolean holdersIsEmpty(List holders) {
        return holders.isEmpty();
    }
    
    public static void addPixKey(String pixKey, BankAccount account) {
        pixMap.put(pixKey, account);
    }
    
    public static void removePixKey(BankAccount account, String pixKey) {
        if (pixKey.equals(account.getPixKey()) && pixMap.containsKey(pixKey)) {
            pixMap.remove(pixKey);
            account.setPixKey(null);
        }
    }
    
    public static BankAccount getAccountByPixKey(String pixKey) {
        return pixMap.get(pixKey);
    }
}
