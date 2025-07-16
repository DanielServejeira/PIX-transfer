/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.io.FileWriter;

/**
 *
 * @author Daniel Servejeira
 */
public class Controller {
    private BankAccount sourceAccount;
    private Bank bank;

    public Controller(BankAccount sourceAccount, Bank bank) {
        this.sourceAccount = sourceAccount;
        this.bank = bank;
    }

    public boolean transfer(String pixKeyDestination, BigDecimal amount) {
        BankAccount destination = bank.findByPixKey(pixKeyDestination);

        if (destination == null) {
            System.out.println("Chave PIX não encontrada.");
            return false;
        }

        if (!sourceAccount.debit(amount)) {
            System.out.println("Saldo insuficiente.");
            return false;
        }

        destination.credit(amount);
        logTransaction(destination, amount);
        return true;
    }

    private void logTransaction(BankAccount destination, BigDecimal amount) {
        String log = String.format(
            "[%s] Transferência de R$ %s de %s para %s\n",
            LocalDateTime.now(),
            amount.toPlainString(),
            sourceAccount.getPixKey(),
            destination.getPixKey()
        );
        
        System.out.println("\n=== LOG DE TRANSFERÊNCIA ===");
        System.out.println(log);
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public BankAccount getDestinationAccount(String pixKey) {
        return bank.findByPixKey(pixKey);
    }
}
