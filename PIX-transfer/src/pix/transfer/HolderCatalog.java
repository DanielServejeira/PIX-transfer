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
public class HolderCatalog {
    private final Map<String, Holder> holdersByCpf = new HashMap<>();

    public void registerHolder(Holder holder) {
        holdersByCpf.put(holder.getCpf(), holder);
    }
    
    public void removeHolder(String cpf) {
        holdersByCpf.remove(cpf);
    }

    public Holder getHolder(String cpf) {
        return holdersByCpf.get(cpf);
    }

    public boolean containsHolder(String cpf) {
        return holdersByCpf.containsKey(cpf);
    }

    public List<Holder> getAllHolders() {
        return new ArrayList<>(holdersByCpf.values());
    }

    public int getSize() {
        return holdersByCpf.size();
    }

    public Holder getHolderByIndex(int index) {
        List<Holder> holders = getAllHolders();
        if (index >= 0 && index < holders.size()) {
            return holders.get(index);
        }
        return null;
    }
}