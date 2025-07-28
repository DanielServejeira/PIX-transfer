/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pix.transfer;

import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.LocalDateTime;

/**
 *
 * @author Daniel Servejeira
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            Controller.initialize();
            
            byte option;
        
            do {
                System.out.println("=== Menu Banco ===");
                System.out.println("[1] Menu Usuário");
                System.out.println("[2] Menu Administrador");
                System.out.println("[0] Sair");
                option = scanner.nextByte();
                scanner.nextLine();

                switch(option) {
                    case 1 -> userMenu(scanner);
                    case 2 -> adminMenu(scanner);
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Operação [" + option + "] inválida.");
                }
            } while (option != 0);
        }
        catch(Exception e) {
            System.out.println("Erro ao carregar programa.");
        }
    }
    
    public static void userMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Usuário ===");
            System.out.println("[1] Cadastrar titular");
            System.out.println("[2] Acessar titular");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> addHolder(scanner);
                case 2 -> signInHolder(scanner);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void signInHolder(Scanner scanner) {
        List<Holder> holders = Controller.getHolders();
        Holder loggedHolder = null;
        String holderCpf;

        if (Controller.holdersIsEmpty()) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        while (loggedHolder == null) {
            System.out.print("Digite o CPF do titular: ");
            holderCpf = scanner.nextLine();

            for (Holder h : holders) {
                if (Controller.getHolderCpf(h).equalsIgnoreCase(holderCpf)) {
                    loggedHolder = h;
                    break;
                }
            }

            if (loggedHolder == null) {
                System.out.println("CPF não encontrado. Por favor, digite outro CPF.");
            }
        }

        System.out.println("Bem-vindo, " + Controller.getHolderName(loggedHolder) + "!");
        bankAccountsHolderMenu(scanner, loggedHolder);
    }

    public static void addHolder(Scanner scanner) {
        boolean exists, validDate = false;
        String holderCpf, holderName;
        LocalDate birthDate = null;
        List<Holder> holders = Controller.getHolders();

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getHoldersSize(); i++) {
            System.out.println("\n[" + i + "] " + Controller.getHolderNameByIndex(i));
            System.out.println("CPF: " + Controller.getHolderCpfByIndex(i));
        }

        do {
            System.out.print("Digite o CPF do titular para adicioná-lo: ");
            holderCpf = scanner.nextLine();

            exists = false;
            for (Holder h : holders) {
                if (Controller.getHolderCpf(h).equalsIgnoreCase(holderCpf)) {
                    exists = true;
                    System.out.println("O titular " + holderCpf + " (" + Controller.getHolderName(h) + ") já está cadastrado. Por favor, digite outro CPF.");
                    break;
                }
            }
        } while (exists);
        
        System.out.print("Digite o nome do titular para adicioná-lo: ");
        holderName = scanner.nextLine();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        while (!validDate) {
            try {
                System.out.print("Digite a data de nascimento do titular (DD/MM/YYYY): ");
                String input = scanner.nextLine();
                birthDate = LocalDate.parse(input, formatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida! Use o formato DD/MM/YYYY.");
            }
        }
        Controller.addHolder(holderCpf, holderName, birthDate);
        System.out.println("Titular " + holderName + " adicionado com sucesso!");
    }

    public static void removeHolder(Scanner scanner) {
        byte index;
        String cpf;

        if (Controller.holdersIsEmpty()) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getHoldersSize(); i++) {
            System.out.println("\n[" + i + "] " + Controller.getHolderNameByIndex(i));
            System.out.println("CPF: " + Controller.getHolderCpfByIndex(i));
        }

        do {
            System.out.print("Digite o índice do titular para removê-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getHoldersSize());

        cpf = Controller.getHolderCpfByIndex(index);
        Controller.removeHolder(cpf);
        System.out.println("Titular removido com sucesso!");
    }
    
    public static void bankAccountsHolderMenu(Scanner scanner, Holder holder) {
        byte option;

        do {
            System.out.println("\n=== Menu Contas Bancárias do Titular " + Controller.getHolderName(holder) + " ===");
            System.out.println("[1] Selecionar conta bancária");
            System.out.println("[2] Adicionar conta bancária");
            System.out.println("[3] Remover conta bancária");
            System.out.println("[4] Ver contas bancárias");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> selectBankAccountByUser(scanner, holder);
                case 2 -> addBankAccountByUser(scanner, holder);
                case 3 -> removeBankAccountByUser(scanner, holder);
                case 4 -> viewBankAccountsByUser(scanner, holder);
                case 0 -> System.out.println("Voltando ao menu de titulares...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void selectBankAccountByUser(Scanner scanner, Holder holder) {
        byte index;
        List<BankAccount> bankAccounts = Controller.getBankAccountsByHolder(holder);

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("Nenhuma conta bancária cadastrada.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getBankAccountsSize(bankAccounts); i++) {
            System.out.println("\n[" + i + "] " + Controller.getBankAccountNumberByHolderAndIndex(holder, i));
        }
        
        do {
            System.out.print("Digite o índice da conta bancária para selecioná-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBankAccountsSize(bankAccounts));
        
        BankAccount bankAccount = bankAccounts.get(index);
        operateBankAccountMenu(scanner, bankAccount);
    }
    
    public static void operateBankAccountMenu(Scanner scanner, BankAccount bankAccount) {
        byte option;

        do {
            System.out.println("\n=== Menu Operações da Conta Bancária " + Controller.getBankAccountNumber(bankAccount) + " ===");
            System.out.println("[1] Transferir PIX");
            System.out.println("[2] Depositar");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> pixTransfer(scanner, bankAccount);
                case 2 -> deposit(scanner, bankAccount);
                case 0 -> System.out.println("Voltando ao menu de agências...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void pixTransfer(Scanner scanner, BankAccount senderAccount) {
        String receiverPixKey;
        BankAccount receiverAccount;
        BigDecimal amount;
        
        System.out.println("\n=== Transferência via PIX ===\n");
        System.out.print("Digite a chave PIX do destinatário: ");
        try {
            receiverPixKey = scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Chave inválida.");
            return;
        }
        
        receiverAccount = Controller.getAccountByPixKey(receiverPixKey);
        if (receiverAccount == null) {
            System.out.println("Chave PIX não encontrada.");
            return;
        }
        
        if (receiverAccount == senderAccount) {
            System.out.println("Não é possível transferir para a mesma conta.");
            return;
        }
        
        System.out.print("Digite o valor da transferência: R$ ");
        try {
            amount = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido.");
            return;
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Valor deve ser maior que zero.");
            return;
        }

        if (Controller.getBalance(senderAccount).compareTo(amount) < 0) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        Controller.transferAmount(senderAccount, receiverAccount, amount);
        
        Controller.logTransfer(senderAccount, receiverAccount, amount);
        System.out.println(Controller.getLogTransfer(senderAccount));
    }
    
    public static void deposit(Scanner scanner, BankAccount bankAccount) {
        System.out.print("Digite o valor que deseja depositar: R$ ");

        try {
            BigDecimal amount = new BigDecimal(scanner.nextLine().replace(",", "."));

            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Valor inválido. O depósito deve ser maior que zero.");
                return;
            }

            Controller.deposit(bankAccount, amount);
            
            System.out.println("Depósito de R$ " + amount + " realizado com sucesso.");
            System.out.println("Novo saldo: R$ " + Controller.getBalance(bankAccount));
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Certifique-se de digitar um número válido.");
        }
    }
    
    public static void viewBankAccountsByUser(Scanner scanner, Holder holder) {
        List<BankAccount> bankAccounts = Controller.getBankAccountsByHolder(holder);

        System.out.println("\n=== Contas Bancárias de " + Controller.getHolderName(holder) + " ===");

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("Este titular não possui contas bancárias cadastradas.");
            return;
        }

        for (BankAccount account : bankAccounts) {
            System.out.println("\nConta nº: " + Controller.getBankAccountNumber(account));
            System.out.println("Saldo: R$ " + Controller.getBalance(account));
            System.out.println("Chave PIX: " + Controller.getPixKey(account));
            System.out.println("Agência: " + Controller.getAgencyNumberByBankAccount(account));
        }
    }

    public static void addBankAccountByUser(Scanner scanner, Holder holder) {
        byte index;
        String agencyNumber;
        Agency agency;

        if (Controller.agenciesIsEmpty()) {
            System.out.println("O banco não possui agências.");
            return;
        }

        System.out.println("\n=== Agências ===");
        for (int i = 0; i < Controller.getAgenciesSize(); i++) {
            System.out.println("[" + i + "] Agência " + Controller.getAgencyNumberByIndex(i));
        }
        
        do {
            System.out.print("Escolha o índice da agência: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getAgenciesSize());
        
        agencyNumber = Controller.getAgencyNumberByIndex(index);
        agency = Controller.getAgency(agencyNumber);

        System.out.print("Digite o número da conta: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Digite a chave PIX: ");
        String pixKey = scanner.nextLine();
        
        Controller.addBankAccount(accountNumber, pixKey, agency, holder);

        System.out.println("Conta criada com sucesso para " + Controller.getHolderName(holder) + " na agência " + agencyNumber + "!");
    }
    
    public static void removeBankAccountByUser(Scanner scanner, Holder holder) {
        byte index;
        List<BankAccount> bankAccounts = Controller.getBankAccountsByHolder(holder);
        BankAccount bankAccount;

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("O titular " + Controller.getHolderName(holder) + " não possui contas bancárias cadastradas.");
            return;
        }

        System.out.println("\n=== Contas Bancárias de " + Controller.getHolderName(holder) + " ===");
        for (int i = 0; i < Controller.getBankAccountsSize(bankAccounts); i++) {
            System.out.println("[" + i + "] Conta: " + Controller.getBankAccountNumberByHolderAndIndex(holder, i) +
                               " | PIX: " + Controller.getBankAccountPixKeyByHolderAndIndex(holder, i));
        }

        do {
            System.out.print("Digite o índice da conta que deseja remover: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBankAccountsSize(bankAccounts));
        
        bankAccount = bankAccounts.get(index);
        
        Agency agency = Controller.getAgencyByBankAccount(bankAccount);

        Controller.removeBankAccount(bankAccount, agency, holder);

        System.out.println("Conta bancária " + bankAccount.getNumber() + " removida com sucesso.");
    }
    
    public static void adminMenu(Scanner scanner) {
        byte option;

        do {
            System.out.println("\n=== Menu Administrador ===");
            System.out.println("[1] Adicionar agência");
            System.out.println("[2] Remover agência");
            System.out.println("[3] Remover Titular");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> addAgency(scanner);
                case 2 -> removeAgency(scanner);
                case 3 -> removeHolder(scanner);
                case 0 -> System.out.println("Voltando ao menu de bancos...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }

    public static void addAgency(Scanner scanner) {
        boolean exists;
        String agencyNumber;
        
        do {
            System.out.print("Digite o número da agência para adicioná-la: ");
            agencyNumber = scanner.nextLine();

            exists = false;
            for (Agency a : Controller.getAgencies()) {
                if (Controller.getAgencyNumber(a).equalsIgnoreCase(agencyNumber)) {
                    exists = true;
                    System.out.println("A agência " + agencyNumber + " já está cadastrada. Por favor, digite outro número.");
                    break;
                }
            }
        } while (exists);

        Controller.addAgency(agencyNumber);
        System.out.println("Agência " + agencyNumber + " adicionada com sucesso!");
    }

    public static void removeAgency(Scanner scanner) {
        byte index = 1;
        String agencyNumber;

        if (Controller.agenciesIsEmpty()) {
            System.out.println("Nenhuma agência cadastrada.");
            return;
        }

        System.out.println("=== Agências Cadastradas ===");
        for (Agency agency : Controller.getAgencies()) {
            System.out.println("[" + index + "] Agência: " + Controller.getAgencyNumber(agency));
            index++;
        }

        do {
            System.out.print("Digite o número da agência para removê-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getAgenciesSize());

        agencyNumber = Controller.getAgencyNumberByIndex(index);
        Controller.removeAgency(agencyNumber);
        System.out.println("Agência " + agencyNumber + " removida com sucesso!");
    }
}
