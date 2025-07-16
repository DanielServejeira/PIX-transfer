/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pix.transfer;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Daniel Servejeira
 */
public class PIXTransfer {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            //setup
            Bank bank1 = new Bank("Banco do Brasil");
            Bank bank2 = new Bank("Caixa Econômica Federal");

            Holder holder1 = new Holder("Alice", LocalDate.of(2002, 5, 15), "423.456.789-12");
            Holder holder2 = new Holder("Mariana", LocalDate.of(2001, 3, 10), "487.654.321-34");
            Holder holder3 = new Holder("Carlos", LocalDate.of(1995, 8, 20), "321.654.987-56");

            BankAccount account1 = new BankAccount("000123", holder1, "0001", "alice@hotmail.com");
            BankAccount account2 = new BankAccount("000456", holder2, "0002", "mariana@gmail.com");
            BankAccount account3 = new BankAccount("000789", holder3, "0003", "carlos@mail.com");

            bank1.addAgency(1);
            bank1.addAccount(account1);
            bank1.addAccount(account2);

            bank2.addAgency(3);
            bank2.addAccount(account3);

            List<Bank> banks = Arrays.asList(bank1, bank2);

            System.out.println("=== Selecione o banco ===");
            for (int i = 0; i < banks.size(); i++) {
                System.out.println("[" + i + "] " + banks.get(i).getName());
            }

            int bankIndex = -1;
            while (bankIndex < 0 || bankIndex >= banks.size()) {
                System.out.print("Digite o número do banco: ");
                bankIndex = scanner.nextInt();
                scanner.nextLine();
            }

            Bank selectedBank = banks.get(bankIndex);

            List<BankAccount> accounts = selectedBank.getBankAccounts();
            System.out.println("\n=== Selecione a conta para login ===");
            for (int i = 0; i < accounts.size(); i++) {
                System.out.printf("[%d] %s (%s)\n", i, accounts.get(i).getHolder().getName(), accounts.get(i).getPixKey());
            }

            int accIndex = -1;
            while (accIndex < 0 || accIndex >= accounts.size()) {
                System.out.print("Digite o número da conta: ");
                accIndex = scanner.nextInt();
                String nextLine = scanner.nextLine();
            }

            BankAccount loggedAccount = accounts.get(accIndex);
            Controller controller = new Controller(loggedAccount, selectedBank);

            int option = -1;
            while (option != 0) {
                System.out.println("\n=== PIX - Sistema de Pagamento Instantâneo ===");
                System.out.println("[1] Enviar PIX");
                System.out.println("[2] Ver dados da conta");
                System.out.println("[0] Sair");
                System.out.print("Escolha uma opção: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        System.out.print("Chave PIX de destino: ");
                        String pixKey = scanner.nextLine();

                        System.out.print("Valor a transferir: R$ ");
                        BigDecimal value = scanner.nextBigDecimal();
                        scanner.nextLine();

                        if (controller.transfer(pixKey, value)) {
                            System.out.println("Transferência realizada com sucesso!");
                        } else {
                            System.out.println("Transferência falhou.");
                        }

                        System.out.println("\n=== Conta de origem ===");
                        System.out.println(controller.getSourceAccount());

                        BankAccount destAccount = controller.getDestinationAccount(pixKey);
                        if (destAccount != null) {
                            System.out.println("\n=== Conta de destino ===");
                            System.out.println(destAccount);
                        }
                        break;

                    case 2:
                        System.out.println("\n=== Dados da Conta ===");
                        System.out.println(controller.getSourceAccount());
                        break;

                    case 0:
                        System.out.println("Saindo...");
                        break;

                    default:
                        System.out.println("Operação inválida.");
                }
            }
        }
        catch(Exception e) {
            System.out.println("Erro ao carregar programa.");
        }
    }
}
