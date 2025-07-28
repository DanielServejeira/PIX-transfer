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
    private final static HolderCatalog holderCatalog = new HolderCatalog();
    private final static AgencyCatalog agencyCatalog = new AgencyCatalog();
    
    private final static Map<String, BankAccount> pixMap = new HashMap<>();
    
    public static void initialize() {
        Controller.addAgency("0001");
        Controller.addAgency("0002");

        Controller.addHolder("12345678900", "Mariana", LocalDate.of(2001, 5, 20));
        Controller.addHolder("98765432100", "Bruno Souza", LocalDate.of(1985, 8, 15));

        Agency agency1 = Controller.getAgencyByIndex(0);
        Agency agency2 = Controller.getAgencyByIndex(1);

        Holder holder1 = Controller.getHolderByCpf("12345678900");
        Holder holder2 = Controller.getHolderByCpf("98765432100");

        BankAccount acc1 = new BankAccount("1001-4", "mariana@mail.com", holder1, agency1);
        BankAccount acc2 = new BankAccount("1002-1", "bruno@gmail.com", holder2, agency2);

        holder1.addBankAccount(acc1);
        holder2.addBankAccount(acc2);

        agency1.addBankAccount(acc1);
        agency2.addBankAccount(acc2);
    }
    
    private static void credit(BankAccount account, BigDecimal amount) {
        account.credit(amount);
    }

    private static void debit(BankAccount account, BigDecimal amount) {
        account.debit(amount);
    }
    
    private static void validate() {
        
    }
    
    public static void transferAmount(BankAccount sender, BankAccount receiver, BigDecimal amount) {
        validate();
        debit(sender, amount);
        credit(receiver, amount);
    }
    
    public static void deposit(BankAccount account, BigDecimal amount) {
        validate();
        credit(account, amount);
        //log
    }
    
    public static List<Holder> getHolders() {
        return new ArrayList<>(holderCatalog.getAllHolders());
    }
    
    public static List<Agency> getAgencies() {
        return new ArrayList<>(agencyCatalog.getAllAgencies());
    }
    
    public static List<BankAccount> getBankAccountsByAgency(String agencyNumber) {
        return agencyCatalog.getAgency(agencyNumber).getBankAccounts();
    }
    
    public static List<BankAccount> getBankAccountsByHolder(Holder holder) {
        return holder.getBankAccounts();
    }
    
    public static void addAgency(String agencyNumber) {
        Agency agency = new Agency(agencyNumber);
        agencyCatalog.registerAgency(agency);
    }

    public static void removeAgency(String agencyNumber) {
        agencyCatalog.removeAgency(agencyNumber);
    }
    
    public static void addBankAccount(String accountNumber, String pixKey, Agency agency, Holder holder) {
        BankAccount bankAccount = new BankAccount(accountNumber, pixKey, holder, agency);
        
        agency.addBankAccount(bankAccount);
        holder.addBankAccount(bankAccount);
        
        addPixKey(pixKey, bankAccount);
    }

    public static void removeBankAccount(BankAccount bankAccount, Agency agency, Holder holder) {
        agency.removeBankAccount(bankAccount);
        holder.removeBankAccount(bankAccount);
    }
    
    public static void addHolder(String cpf, String name, LocalDate birthDate) {
        Holder holder = new Holder(cpf, name, birthDate);
        holderCatalog.registerHolder(holder);
    }
    
    public static void removeHolder(String cpf) {
        holderCatalog.removeHolder(cpf);
    }
    
    public static BigDecimal getBalance(BankAccount bankAccount) {
        return bankAccount.getBalance();
    }
    
    public static String getPixKey(BankAccount bankAccount) {
        return bankAccount.getPixKey();
    }
    
    public static BankAccount getBankAccountByHolderAndNumber(Holder holder, String accountNumber) {
        for (BankAccount account : holder.getBankAccounts()) {
            if (account.getNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }
    
    public static String getHolderNameByIndex(int index) {
        Holder holder = holderCatalog.getHolderByIndex(index);
        return holder != null ? holder.getName() : "Desconhecido";
    }
    
    public static String getHolderName(Holder holder) {
        return holder.getName();
    }
    
    public static String getHolderCpfByIndex(int index) {
        Holder holder = holderCatalog.getHolderByIndex(index);
        return holder != null ? holder.getCpf() : "Desconhecido";
    }
    
    public static String getHolderCpf(Holder holder) {
        return holder.getCpf();
    }
    
    public static boolean bankAccountsIsEmpty(List bankAccounts) {
        return bankAccounts.isEmpty();
    }
        
    public static boolean agenciesIsEmpty() {
        return agencyCatalog.getAllAgencies().isEmpty();
    }
    
    public static String getAgencyNumber(Agency agency) {
        return agency.getNumber();
    }
    
    public static int getBankAccountsSize(List bankAccounts) {
        return bankAccounts.size();
    }
    
    public static int getAgenciesSize() {
        return agencyCatalog.getAllAgencies().size();
    }
    
    public static int getHoldersSize() {
        return holderCatalog.getSize();
    }
    
    public static boolean holdersIsEmpty() {
        return holderCatalog.getAllHolders().isEmpty();
    }
    
    public static void addPixKey(String pixKey, BankAccount bankAccount) {
        pixMap.put(pixKey, bankAccount);
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
    
    public static String getAgencyNumberByIndex(int index) {
        Agency a = agencyCatalog.getAgencyByIndex(index);
        return a != null ? a.getNumber() : null;
    }
    
    public static String getBankAccountNumberByHolderAndIndex(Holder holder, int index) {
        BankAccount ba = holder.getBankAccountByIndex(index);
        return ba != null ? ba.getNumber() : null;
    }
    
    public static String getBankAccountPixKeyByHolderAndIndex(Holder holder, int index) {
        BankAccount ba = holder.getBankAccountByIndex(index);
        return ba != null ? ba.getPixKey() : null;
    }
    
    public static String getBankAccountNumber(BankAccount bankAccount) {
        return bankAccount != null ? bankAccount.getNumber() : null;
    }
    
    public static void logTransfer(BankAccount sender, BankAccount receiver, BigDecimal amount) {
        sender.logTransfer("send", receiver, amount);
        receiver.logTransfer("receive", sender, amount);
    }
    
    public static List<String> getLogTransfer(BankAccount bankAccount) {
        return bankAccount.getTransferLog();
    }
    
    public static String getAgencyNumberByBankAccount(BankAccount bankAccount) {
        return bankAccount.getAgency().getNumber();
    }
    
    public static Agency getAgency(String agencyNumber) {
        return agencyCatalog.getAgency(agencyNumber);
    }
    
    public static Agency getAgencyByBankAccount(BankAccount bankAccount) {
        return bankAccount.getAgency();
    }
    
    public static Holder getHolderByCpf(String cpf) {
        return holderCatalog.getHolder(cpf);
    }

    public static Agency getAgencyByIndex(int index) {
        return agencyCatalog.getAgencyByIndex(index);
    }
}
