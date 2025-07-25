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
                System.out.println("=== Menu Principal ===");
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

        if (Controller.holdersIsEmpty(holders)) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        Holder loggedHolder = null;

        while (loggedHolder == null) {
            System.out.print("Digite o CPF do titular: ");
            String holderCpf = scanner.nextLine();

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
    
    public static void holdersMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Titulares ===");
            System.out.println("[1] Remover titular");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> removeHolder(scanner);
                case 0 -> System.out.println("Voltando ao menu administrador...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }

    public static void addHolder(Scanner scanner) {
        boolean exists, validDate = false;
        String holderCpf, holderName;
        Holder holder;
        LocalDate birthDate = null;
        List<Holder> holders = Controller.getHolders();

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getHoldersSize(holders); i++) {
            holder = holders.get(i);
            System.out.println("\n[" + i + "] " + Controller.getHolderName(holder));
            System.out.println("CPF: " + Controller.getHolderCpf(holder));
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

        holder = new Holder(holderCpf, holderName, birthDate);
        Controller.addHolder(holder);
        System.out.println("Titular " + holderName + " adicionado com sucesso!");
    }

    public static void removeHolder(Scanner scanner) {
        byte index;
        Holder removedHolder;
        List<Holder> holders = Controller.getHolders();

        if (Controller.holdersIsEmpty(holders)) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getHoldersSize(holders); i++) {
            removedHolder = holders.get(i);
            System.out.println("\n[" + i + "] " + Controller.getHolderName(removedHolder));
            System.out.println("CPF: " + Controller.getHolderCpf(removedHolder));
        }

        do {
            System.out.print("Digite o índice do titular para removê-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getHoldersSize(holders));

        removedHolder = holders.get(index);
        Controller.removeHolder(removedHolder);
        System.out.println("Titular " + Controller.getHolderName(removedHolder) + " removido com sucesso!");
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
        BankAccount account;

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("Nenhuma conta bancária cadastrada.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getBankAccountsSize(bankAccounts); i++) {
            account = bankAccounts.get(i);
            System.out.println("\n[" + i + "] " + Controller.getBankAccountNumber(account));
            System.out.println("Chave PIX: " + Controller.getPixKey(account));
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
        
        System.out.println("\n=== Transferência via PIX ===");
        System.out.println("Conta origem: " + Controller.getBankAccountNumber(senderAccount) + " | Saldo: R$ " + Controller.getBalance(senderAccount));

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
        
        BigDecimal amount;
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

        if (senderAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        Controller.transferAmount(senderAccount, receiverAccount, amount);

        System.out.println("PIX enviado com sucesso!");
        
        logPIXTransfer(senderAccount, receiverAccount, amount);
    }
    
    public static void logPIXTransfer(BankAccount sender, BankAccount receiver, BigDecimal amount) {
        System.out.println("\n=== Comprovante PIX ===");
        System.out.println("Remetente: Conta " + Controller.getBankAccountNumber(sender) + " | Chave PIX: " + Controller.getPixKey(sender));
        System.out.println("Destinatário: Conta " + Controller.getBankAccountNumber(receiver) + " | Chave PIX: " + Controller.getPixKey(receiver));
        System.out.println("Valor: R$ " + amount.setScale(2));
        System.out.println("Data/Hora: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        System.out.println("=========================");
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
        List<Bank> banks = Controller.getBanks();

        System.out.println("\n=== Contas Bancárias de " + Controller.getHolderName(holder) + " ===");

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("Este titular não possui contas bancárias cadastradas.");
            return;
        }

        for (BankAccount account : bankAccounts) {
            Agency foundAgency = null;
            Bank foundBank = null;

            outerLoop: ///////////////////// ?????????????
            for (Bank bank : banks) {
                for (Agency agency : Controller.getAgencies(bank)) {
                    if (Controller.getBankAccountsByAgency(agency).contains(account)) {
                        foundAgency = agency;
                        foundBank = bank;
                        break outerLoop;
                    }
                }
            }

            System.out.println("\nConta nº: " + Controller.getBankAccountNumber(account));
            System.out.println("Saldo: R$ " + Controller.getBalance(account));
            System.out.println("Chave PIX: " + Controller.getPixKey(account));
            System.out.println("Agência: " + (foundAgency != null ? Controller.getAgencyNumber(foundAgency) : "Desconhecida"));
            System.out.println("Banco: " + (foundBank != null ? Controller.getBankName(foundBank) : "Desconhecido"));
        }
    }

    public static void addBankAccountByUser(Scanner scanner, Holder holder) {
        byte bankIndex, agencyIndex;
        Bank bank;
        Agency agency;
        List<Bank> banks = Controller.getBanks();

        if (Controller.bankAccountsIsEmpty(banks)) {
            System.out.println("Nenhum banco cadastrado.");
            return;
        }

        System.out.println("\n=== Bancos Cadastrados ===");
        for (int i = 0; i < Controller.getBanksSize(banks); i++) {
            bank = banks.get(i);
            System.out.println("[" + i + "] " + Controller.getBankName(bank));
        }

        do {
            System.out.print("Escolha o índice do banco: ");
            bankIndex = scanner.nextByte();
            scanner.nextLine();
        } while (bankIndex < 0 || bankIndex >= Controller.getBanks().size());

        Bank selectedBank = banks.get(bankIndex);
        List<Agency> agencies = Controller.getAgencies(selectedBank);

        if (Controller.agenciesIsEmpty(agencies)) {
            System.out.println("O banco selecionado não possui agências.");
            return;
        }

        System.out.println("\n=== Agências do Banco " + selectedBank.getName() + " ===");
        for (int i = 0; i < Controller.getAgenciesSize(agencies); i++) {
            agency = agencies.get(i);
            System.out.println("[" + i + "] Agência " + Controller.getAgencyNumber(agency));
        }
        
        do {
            System.out.print("Escolha o índice da agência: ");
            agencyIndex = scanner.nextByte();
            scanner.nextLine();
        } while (agencyIndex < 0 || agencyIndex >= Controller.getAgenciesSize(agencies));

        Agency selectedAgency = agencies.get(agencyIndex);

        System.out.print("Digite o número da conta: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Digite a chave PIX: ");
        String pixKey = scanner.nextLine();

        BankAccount newAccount = new BankAccount(accountNumber, pixKey);
        Controller.addPixKey(pixKey, newAccount);
        Controller.addBankAccount(newAccount, selectedAgency, holder);

        System.out.println("Conta criada com sucesso para " + Controller.getHolderName(holder) + " na agência " + Controller.getAgencyNumber(selectedAgency) + " do banco " + Controller.getBankName(selectedBank));
    }
    
    public static void removeBankAccountByUser(Scanner scanner, Holder holder) {
        byte index;
        List<BankAccount> bankAccounts = Controller.getBankAccountsByHolder(holder);
        BankAccount bankAccount, removedAccount;

        if (Controller.bankAccountsIsEmpty(bankAccounts)) {
            System.out.println("O titular " + Controller.getHolderName(holder) + " não possui contas bancárias cadastradas.");
            return;
        }

        System.out.println("\n=== Contas Bancárias de " + Controller.getHolderName(holder) + " ===");
        for (int i = 0; i < Controller.getBankAccountsSize(bankAccounts); i++) {
            bankAccount = bankAccounts.get(i);
            System.out.println("[" + i + "] Conta: " + Controller.getBankAccountNumber(bankAccount) + " | PIX: " + Controller.getPixKey(bankAccount));
        }

        do {
            System.out.print("Digite o índice da conta que deseja remover: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBankAccountsSize(bankAccounts));
        
        removedAccount = bankAccounts.get(index);

        List<Bank> banks = Controller.getBanks(); //////////////// ????????????????
        for (Bank bank : banks) {
            for (Agency agency : Controller.getAgencies(bank)) {
                for (BankAccount ba : Controller.getBankAccountsByAgency(agency)) {
                    if (Controller.getBankAccountNumber(ba).equals(Controller.getBankAccountNumber(removedAccount))) {
                        Controller.removeBankAccount(ba, agency, holder);
                        System.out.println("Conta " + Controller.getBankAccountNumber(removedAccount) + " removida com sucesso.");
                    }
                }
            }
        }
    }
    
    public static void adminMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Administrador ===");
            System.out.println("[1] Selecionar banco");
            System.out.println("[2] Adicionar banco");
            System.out.println("[3] Remover banco");
            System.out.println("[4] Remover titular");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> selectBank(scanner);
                case 2 -> addBank(scanner);
                case 3 -> removeBank(scanner);
                case 4 -> removeHolder(scanner);
                case 0 -> System.out.println("Voltando ao menu administrador...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }

    public static void selectBank(Scanner scanner) {
        byte index = -1;
        Bank bank;
        List<Bank> banks = Controller.getBanks();

        if (Controller.banksIsEmpty(banks)) {
            System.out.println("Nenhum banco cadastrado.");
            return;
        }

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < Controller.getBanksSize(banks); i++) {
            bank = banks.get(i);
            System.out.println("[" + i + "] " + Controller.getBankName(bank));
        }
        
        do {
            System.out.print("Digite o número do banco para selecioná-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBanksSize(banks));
        
        bank = banks.get(index);
        agenciesMenu(scanner, bank);
    }

    public static void addBank(Scanner scanner) {
        boolean exists;
        String bankName;
        Bank bank;
        List<Bank> banks = Controller.getBanks();

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < Controller.getBanksSize(banks); i++) {
            bank = banks.get(i);
            System.out.println("[" + i + "] " + Controller.getBankName(bank));
        }

        do {
            System.out.print("Digite o nome do banco para adicioná-lo: ");
            bankName = scanner.nextLine();

            exists = false;
            for (Bank b : banks) {
                if (Controller.getBankName(b).equalsIgnoreCase(bankName)) {
                    exists = true;
                    System.out.println("O banco " + bankName + " já está cadastrado. Por favor, digite outro nome.");
                    break;
                }
            }
        } while (exists);

        bank = new Bank(bankName);
        Controller.addBank(bank);
        System.out.println("Banco " + bankName + " adicionado com sucesso!");
    }

    public static void removeBank(Scanner scanner) {
        byte index;
        String bankName;
        Bank bank;
        List<Bank> banks = Controller.getBanks();

        if (banks.isEmpty()) {
            System.out.println("Nenhum banco cadastrado.");
            return;
        }

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < banks.size(); i++) {
            bank = banks.get(i);
            System.out.println("[" + i + "] " + Controller.getBankName(bank));
        }

        do {
            System.out.print("Digite o nome do banco para removê-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBanksSize(banks));

        bank = banks.get(index);
        bankName = Controller.getBankName(bank);
        Controller.removeBank(bank);
        System.out.println("Banco " + bankName + " removido com sucesso!");
    }

    public static void agenciesMenu(Scanner scanner, Bank bank) {
        byte option;

        do {
            System.out.println("\n=== Menu Agências de " + Controller.getBankName(bank) + " ===");
            System.out.println("[1] Selecionar agência");
            System.out.println("[2] Adicionar agência");
            System.out.println("[3] Remover agência");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> selectAgency(scanner, bank);
                case 2 -> addAgency(scanner, bank);
                case 3 -> removeAgency(scanner, bank);
                case 0 -> System.out.println("Voltando ao menu de bancos...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }

    public static void selectAgency(Scanner scanner, Bank bank) {
        byte index = -1;
        Agency agency;
        List<Agency> agencies = Controller.getAgencies(bank);

        if (Controller.agenciesIsEmpty(agencies)) {
            System.out.println("Nenhuma agência cadastrada no banco " + Controller.getBankName(bank) + ".");
            return;
        }

        System.out.println("=== Agências Cadastradas ===");
        for (int i = 0; i < Controller.getAgenciesSize(agencies); i++) {
            agency = agencies.get(i);
            System.out.println("[" + i + "] " + Controller.getAgencyNumber(agency));
        }
        
        do {
            System.out.print("Digite o número da agência para selecioná-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getAgenciesSize(agencies));
        
        agency = agencies.get(index);
        bankAccountsAgencyMenu(scanner, agency);
    }

    public static void addAgency(Scanner scanner, Bank bank) {
        boolean exists;
        String agencyNumber;
        List<Agency> agencies = Controller.getAgencies(bank);
        
        do {
            System.out.print("Digite o número da agência para adicioná-la: ");
            agencyNumber = scanner.nextLine();

            exists = false;
            for (Agency a : agencies) {
                if (Controller.getAgencyNumber(a).equalsIgnoreCase(agencyNumber)) {
                    exists = true;
                    System.out.println("A agência " + agencyNumber + " já está cadastrada. Por favor, digite outro número.");
                    break;
                }
            }
        } while (exists);

        Agency agency = new Agency(agencyNumber);
        Controller.addAgency(agency, bank);
        System.out.println("Agência " + agencyNumber + " adicionada com sucesso!");
    }

    public static void removeAgency(Scanner scanner, Bank bank) {
        byte index;
        String agencyNumber;
        Agency agency;
        List<Agency> agencies = Controller.getAgencies(bank);

        if (agencies.isEmpty()) {
            System.out.println("Nenhuma agência cadastrada no banco " + Controller.getBankName(bank) + ".");
            return;
        }

        System.out.println("=== Agências Cadastradas ===");
        for (int i = 0; i < Controller.getAgenciesSize(agencies); i++) {
            agency = agencies.get(i);
            System.out.println("[" + i + "] " + Controller.getAgencyNumber(agency));
        }

        do {
            System.out.print("Digite o número da agência para removê-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getAgenciesSize(agencies));

        agency = agencies.get(index);
        agencyNumber = Controller.getAgencyNumber(agency);
        Controller.removeAgency(agency, bank);
        System.out.println("Agência " + agencyNumber + " removida com sucesso!");
    }
    
    public static void bankAccountsAgencyMenu(Scanner scanner, Agency agency) {
        byte option;

        do {
            System.out.println("\n=== Menu Contas Bancárias da Agência " + Controller.getAgencyNumber(agency) + " ===");
            System.out.println("[1] Ver contas bancárias");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> viewBankAccountByAgency(scanner, agency);
                case 0 -> System.out.println("Voltando ao menu de agências...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void viewBankAccountByAgency(Scanner scanner, Agency agency) {
        List<BankAccount> accounts = Controller.getBankAccountsByAgency(agency);
        Bank bank = Controller.getBankByAgency(agency);

        System.out.println("\n=== Contas Bancárias da Agência " + Controller.getAgencyNumber(agency) + " ===");

        if (Controller.bankAccountsIsEmpty(accounts)) {
            System.out.println("Esta agência não possui contas bancárias cadastradas.");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.println("\nNº da conta: " + Controller.getBankAccountNumber(account));
            System.out.println("Chave PIX: " + Controller.getPixKey(account));
            System.out.println("Banco: " + Controller.getBankName(bank));
        }
    }
}
