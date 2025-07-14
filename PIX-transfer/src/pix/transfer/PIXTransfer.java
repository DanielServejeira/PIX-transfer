/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pix.transfer;
import java.util.Scanner;

/**
 *
 * @author Daniel Servejeira
 */
public class PIXTransfer {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args){
        Controller controller = new Controller();
        int menu = 1;
        
        while(menu != 0) {
            try (Scanner input = new Scanner(System.in)) {
                System.out.println("\nPIX - Sistema de Pagamento Instantâneo\n");
                System.out.println("Selecione a operação desejada:");
                System.out.println("[0] - Sair");
                System.out.println("[1] - Transferir");
                System.out.println("[2] - Cobrar");

                menu = input.nextInt();
            }
            catch (Exception e) {
            }

            switch(menu) {
                case 0:
                    return;
                case 1:
                    controller.tranferPayment();
                case 2:
                    controller.chargePayment();
                default:
                    System.out.println("Operação [" + menu + "] inválida.");
            }
        }
    }  
}
