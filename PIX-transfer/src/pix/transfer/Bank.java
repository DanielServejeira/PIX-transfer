/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;
import java.util.ArrayList;

/**
 *
 * @author Daniel Servejeira
 */
public class Bank {
    private String name;
    private final ArrayList<Agency> agencies;

    public Bank(String name) {
        this.name = name;
        this.agencies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Agency> getAgencies() {
        return agencies;
    }
    
    public void addAgency(Agency agency) {
        agencies.add(agency);
    }

    public void removeAgency(Agency agency) {
        agencies.remove(agency);
    }
}
