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

        if (holders.isEmpty()) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        Holder loggedHolder = null;

        while (loggedHolder == null) {
            System.out.print("Digite o CPF do titular: ");
            String holderCpf = scanner.nextLine();

            for (Holder h : holders) {
                if (h.getCpf().equalsIgnoreCase(holderCpf)) {
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
    
    public static void adminMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Administrador ===");
            System.out.println("[1] Menu Titulares");
            System.out.println("[2] Menu Banco");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> holdersMenu(scanner);
                case 2 -> banksMenu(scanner);
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void holdersMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Titulares ===");
            System.out.println("[1] Selecionar titular");
            System.out.println("[2] Adicionar titular");
            System.out.println("[3] Remover titular");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> selectHolder(scanner);
                case 2 -> addHolder(scanner);
                case 3 -> removeHolder(scanner);
                case 0 -> System.out.println("Voltando ao menu administrador...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void selectHolder(Scanner scanner) {
        byte index;
        Holder holder;
        List<Holder> holders = Controller.getHolders();

        if (holders.isEmpty()) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < holders.size(); i++) {
            holder = holders.get(i);
            System.out.println("\n[" + i + "] " + Controller.getHolderName(holder));
            System.out.println("CPF: " + Controller.getHolderCpf(holder));
        }
        
        do {
            System.out.print("Digite o número do titular para selecioná-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= holders.size());
        
        holder = holders.get(index);
        bankAccountsHolderMenu(scanner, holder);
    }

    public static void addHolder(Scanner scanner) {
        boolean exists, validDate = false;
        String holderCpf, holderName;
        Holder holder;
        LocalDate birthDate = null;
        List<Holder> holders = Controller.getHolders();

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < Controller.getHolderSize(holders); i++) {
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
                    holderName = Controller.getHolderName(h);
                    System.out.println("O titular " + holderCpf + " (" + holderName + ") já está cadastrado. Por favor, digite outro CPF.");
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
        String holderName;
        List<Holder> holders = Controller.getHolders();

        if (holders.isEmpty()) {
            System.out.println("Nenhum titular cadastrado.");
            return;
        }

        System.out.println("=== Titulares Cadastrados ===");
        for (int i = 0; i < holders.size(); i++) {
            System.out.println("\n[" + i + "] " + holders.get(i).getName());
            System.out.println("CPF: " + holders.get(i).getCpf());
        }

        do {
            System.out.print("Digite o CPF do titular para removê-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= holders.size());

        holderName = holders.get(index).getName();
        Controller.removeBank(holders.get(index));
        System.out.println("Titular " + holderName + " removido com sucesso!");
    }
    
    public static void bankAccountsHolderMenu(Scanner scanner, Holder holder) {
        byte option;

        do {
            System.out.println("\n=== Menu Contas Bancárias do Titular " + holder.getName() + " ===");
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
        for (int i = 0; i < Controller.getBankAccountSize(bankAccounts); i++) {
            account = bankAccounts.get(i);
            System.out.println("\n[" + i + "] " + Controller.getBankAccountNumber(account));
            System.out.println("Chave PIX: " + Controller.getPixKey(account));
        }
        
        do {
            System.out.print("Digite o índice da conta bancária para selecioná-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= Controller.getBankAccountSize(bankAccounts));
        
        BankAccount bankAccount = bankAccounts.get(index);
        operateBankAccountMenu(scanner, bankAccount);
    }
    
    public static void operateBankAccountMenu(Scanner scanner, BankAccount bankAccount) {
        byte option;

        do {
            System.out.println("\n=== Menu Operações da Conta Bancária " + bankAccount.getNumber() + " ===");
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
    
    public static void pixTransfer(Scanner scanner, BankAccount bankAccount) {
        String receiverPixKey;
        BankAccount receiverAccount = null;
        
        System.out.println("\n=== Transferência via PIX ===");
        System.out.println("Conta origem: " + Controller.getBankAccountNumber(bankAccount) + " | Saldo: R$ " + Controller.getBalance(bankAccount));

        System.out.print("Digite a chave PIX do destinatário: ");
        receiverPixKey = scanner.nextLine();

        outer:
        for (Bank bank : Controller.getBanks()) {
            for (Agency agency : Controller.getAgencies(bank)) {
                for (BankAccount account : Controller.getBankAccountsByAgency(agency)) {
                    if (Controller.getPixKey(bankAccount).equalsIgnoreCase(receiverPixKey)) {
                        receiverAccount = account;
                        break outer;
                    }
                }
            }
        }

        if (receiverAccount == null) {
            System.out.println("Chave PIX não encontrada.");
            return;
        }

        if (receiverAccount == bankAccount) {
            System.out.println("Não é possível transferir para a mesma conta.");
            return;
        }

        System.out.print("Digite o valor da transferência: R$ ");
        BigDecimal amount;
        
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

        if (bankAccount.getBalance().compareTo(amount) < 0) {
            System.out.println("Saldo insuficiente.");
            return;
        }

        Controller.debit(bankAccount, amount);
        Controller.credit(receiverAccount, amount);

        System.out.println("PIX enviado com sucesso!");
        
        logPIXTransfer(bankAccount, receiverAccount, amount);
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

            Controller.credit(bankAccount, amount);
            BigDecimal newBalance = Controller.getBalance(bankAccount);

            System.out.println("Depósito de R$ " + amount + " realizado com sucesso.");
            System.out.println("Novo saldo: R$ " + newBalance);
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

            outerLoop:
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
        for (int i = 0; i < Controller.getBankSize(banks); i++) {
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
        for (int i = 0; i < Controller.getAgencySize(agencies); i++) {
            agency = agencies.get(i);
            System.out.println("[" + i + "] Agência " + Controller.getAgencyNumber(agency));
        }
        
        do {
            System.out.print("Escolha o índice da agência: ");
            agencyIndex = scanner.nextByte();
            scanner.nextLine();
        } while (agencyIndex < 0 || agencyIndex >= Controller.getAgencySize(agencies));

        Agency selectedAgency = agencies.get(agencyIndex);

        System.out.print("Digite o número da conta: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Digite a chave PIX: ");
        String pixKey = scanner.nextLine();

        BankAccount newAccount = new BankAccount(accountNumber, pixKey);

        selectedAgency.getBankAccounts().add(newAccount);
        holder.getBankAccounts().add(newAccount);

        System.out.println("Conta criada com sucesso para " + holder.getName() + " na agência " + selectedAgency.getNumber() + " do banco " + selectedBank.getName());
    }
    
    public static void removeBankAccountByUser(Scanner scanner, Holder holder) {
        byte index;
        List<BankAccount> bankAccounts = holder.getBankAccounts();

        if (bankAccounts.isEmpty()) {
            System.out.println("O titular " + holder.getName() + " não possui contas cadastradas.");
            return;
        }

        System.out.println("\n=== Contas Bancárias de " + holder.getName() + " ===");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println("[" + i + "] Conta: " + bankAccounts.get(i).getNumber() + " | PIX: " + bankAccounts.get(i).getPixKey());
        }

        do {
            System.out.print("Digite o índice da conta que deseja remover: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= bankAccounts.size());

        BankAccount removedAccount = bankAccounts.remove(index);

        List<Bank> banks = Controller.getBanks();
        for (Bank bank : banks) {
            for (Agency agency : bank.getAgencies()) {
                agency.getBankAccounts().remove(removedAccount);
            }
        }

        System.out.println("Conta " + removedAccount.getNumber() + " removida com sucesso.");
    }
    
    public static void banksMenu(Scanner scanner) {
        byte option;
        
        do {
            System.out.println("=== Menu Bancos ===");
            System.out.println("[1] Selecionar banco");
            System.out.println("[2] Adicionar banco");
            System.out.println("[3] Remover banco");
            System.out.println("[0] Voltar");
            option = scanner.nextByte();
            scanner.nextLine();

            switch(option) {
                case 1 -> selectBank(scanner);
                case 2 -> addBank(scanner);
                case 3 -> removeBank(scanner);
                case 0 -> System.out.println("Voltando ao menu administrador...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }

    public static void selectBank(Scanner scanner) {
        byte index = -1;
        List<Bank> banks = Controller.getBanks();

        if (banks.isEmpty()) {
            System.out.println("Nenhum banco cadastrado.");
            return;
        }

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < banks.size(); i++) {
            System.out.println("[" + i + "] " + banks.get(i).getName());
        }
        
        do {
            System.out.print("Digite o número do banco para selecioná-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= banks.size());
        
        Bank selectedBank = banks.get(index);
        agenciesMenu(scanner, selectedBank);
    }

    public static void addBank(Scanner scanner) {
        boolean exists;
        String bankName;
        List<Bank> banks = Controller.getBanks();

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < banks.size(); i++) {
            System.out.println("[" + i + "] " + banks.get(i).getName());
        }

        do {
            System.out.print("Digite o nome do banco para adicioná-lo: ");
            bankName = scanner.nextLine();

            exists = false;
            for (Bank b : banks) {
                if (b.getName().equalsIgnoreCase(bankName)) {
                    exists = true;
                    System.out.println("O banco " + bankName + " já está cadastrado. Por favor, digite outro nome.");
                    break;
                }
            }
        } while (exists);

        Bank bank = new Bank(bankName);
        Controller.addBank(bank);
        System.out.println("Banco " + bankName + " adicionado com sucesso!");
    }

    public static void removeBank(Scanner scanner) {
        byte index;
        String bankName;
        List<Bank> banks = Controller.getBanks();

        if (banks.isEmpty()) {
            System.out.println("Nenhum banco cadastrado.");
            return;
        }

        System.out.println("=== Bancos Cadastrados ===");
        for (int i = 0; i < banks.size(); i++) {
            System.out.println("[" + i + "] " + banks.get(i).getName());
        }

        do {
            System.out.print("Digite o número do banco para removê-lo: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= banks.size());

        bankName = banks.get(index).getName();
        Controller.removeBank(banks.get(index));
        System.out.println("Banco " + bankName + " removido com sucesso!");
    }

    public static void agenciesMenu(Scanner scanner, Bank bank) {
        byte option;

        do {
            System.out.println("\n=== Menu Agências de " + bank.getName() + " ===");
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
        List<Agency> agencies = Controller.getAgencies(bank);

        if (agencies.isEmpty()) {
            System.out.println("Nenhuma agência cadastrada no banco " + bank.getName() + ".");
            return;
        }

        System.out.println("=== Agências Cadastradas ===");
        for (int i = 0; i < agencies.size(); i++) {
            System.out.println("[" + i + "] " + agencies.get(i).getNumber());
        }
        
        do {
            System.out.print("Digite o número da agência para selecioná-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= agencies.size());
        
        Agency agency = agencies.get(index);
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
                if (a.getNumber().equalsIgnoreCase(agencyNumber)) {
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
        List<Agency> agencies = Controller.getAgencies(bank);

        if (agencies.isEmpty()) {
            System.out.println("Nenhuma agência cadastrada no banco " + bank.getName() + ".");
            return;
        }

        System.out.println("=== Agências Cadastradas ===");
        for (int i = 0; i < agencies.size(); i++) {
            System.out.println("[" + i + "] " + agencies.get(i).getNumber());
        }

        do {
            System.out.print("Digite o número da agência para removê-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= agencies.size());

        agencyNumber = agencies.get(index).getNumber();
        Controller.removeAgency(agencies.get(index), bank);
        System.out.println("Agência " + agencyNumber + " removida com sucesso!");
    }
    
    public static void bankAccountsAgencyMenu(Scanner scanner, Agency agency) {
        byte option;

        do {
            System.out.println("\n=== Menu Contas Bancárias da Agência " + agency.getNumber() + " ===");
            System.out.println("[1] Ver conta bancária");
            System.out.println("[2] Adicionar conta bancária");
            System.out.println("[3] Remover conta bancária");
            System.out.println("[0] Voltar");

            option = scanner.nextByte();
            scanner.nextLine();

            switch (option) {
                case 1 -> viewBankAccountByAgency(scanner, agency);
                case 2 -> addBankAccount(scanner, agency);
                case 3 -> removeBankAccount(scanner, agency);
                case 0 -> System.out.println("Voltando ao menu de agências...");
                default -> System.out.println("Operação [" + option + "] inválida.");
            }
        } while (option != 0);
    }
    
    public static void viewBankAccountByAgency(Scanner scanner, Agency agency) {
        List<BankAccount> accounts = agency.getBankAccounts();
        Bank bank = Controller.getBankByAgency(agency);

        System.out.println("\n=== Contas Bancárias da Agência " + agency.getNumber() + " ===");

        if (accounts.isEmpty()) {
            System.out.println("Esta agência não possui contas bancárias cadastradas.");
            return;
        }

        for (BankAccount account : accounts) {
            System.out.println("\nConta nº: " + account.getNumber());
            System.out.println("Saldo: R$ " + account.getBalance());
            System.out.println("Chave PIX: " + account.getPixKey());
            System.out.println("Banco: " + (bank != null ? bank.getName() : "Desconhecido"));
        }
    }

    public static void addBankAccount(Scanner scanner, Agency agency) {
        boolean exists;
        String bankAccountNumber, pixKey;
        List<BankAccount> bankAccounts = Controller.getBankAccountsByAgency(agency);
        
        do {
            System.out.print("Digite o número da conta bancária para adicioná-la: ");
            bankAccountNumber = scanner.nextLine();

            exists = false;
            for (BankAccount ba : bankAccounts) {
                if (ba.getNumber().equalsIgnoreCase(bankAccountNumber)) {
                    exists = true;
                    System.out.println("A conta bancária " + bankAccountNumber + " já está cadastrada. Por favor, digite outro número.");
                    break;
                }
            }
        } while (exists);
        
        do {
            System.out.print("Digite a chave PIX da conta bancária: ");
            pixKey = scanner.nextLine();

            exists = false;
            for (BankAccount ba : bankAccounts) {
                if (ba.getNumber().equalsIgnoreCase(pixKey)) {
                    exists = true;
                    System.out.println("A chave PIX " + pixKey + " já está cadastrada. Por favor, digite outra chave PIX.");
                    break;
                }
            }
        } while (exists);

        BankAccount bankAccount = new BankAccount(bankAccountNumber, pixKey);
        Controller.addBankAccount(bankAccount, agency);
        System.out.println("Conta bancária " + bankAccountNumber + " adicionada com sucesso!");
    }

    public static void removeBankAccount(Scanner scanner, Agency agency) {
        byte index;
        String bankAccountNumber;
        List<BankAccount> bankAccounts = Controller.getBankAccountsByAgency(agency);

        if (bankAccounts.isEmpty()) {
            System.out.println("Nenhuma conta bancária cadastrada na agência " + agency.getNumber() + ".");
            return;
        }

        System.out.println("=== Contas Bancárias Cadastradas ===");
        for (int i = 0; i < bankAccounts.size(); i++) {
            System.out.println("[" + i + "] " + bankAccounts.get(i).getNumber());
        }

        do {
            System.out.print("Digite o número da conta bancária para removê-la: ");
            index = scanner.nextByte();
            scanner.nextLine();
        } while (index < 0 || index >= bankAccounts.size());

        bankAccountNumber = bankAccounts.get(index).getNumber();
        Controller.removeBankAccount(bankAccounts.get(index), agency);
        System.out.println("Conta bancária " + bankAccountNumber + " removida com sucesso!");
    }
}
