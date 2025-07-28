/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pix.transfer;

import java.util.*;

/**
 *
 * @author Daniel Servejeira
 */
public class AgencyCatalog {
    private final Map<String, Agency> agenciesByNumber = new HashMap<>();

    public boolean registerAgency(Agency agency) {
        return agenciesByNumber.put(agency.getNumber(), agency) != null;
    }
    
    public boolean removeAgency(String agencyNumber) {
        return agenciesByNumber.remove(agencyNumber) != null;
    }

    public Agency getAgency(String agencyNumber) {
        return agenciesByNumber.get(agencyNumber);
    }

    public List<Agency> getAllAgencies() {
        return new ArrayList<>(agenciesByNumber.values());
    }
    
    public boolean containsAgency(String agencyNumber) {
        return agenciesByNumber.containsKey(agencyNumber);
    }
    
    public Agency getAgencyByIndex(int index) {
        List<Agency> agencies = getAllAgencies();
        if (index >= 0 && index < agencies.size()) {
            return agencies.get(index);
        }
        return null;
    }
}
