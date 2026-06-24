package model;

import java.util.List;
import java.util.ArrayList;

public class Estoque {
    private List<Item> itens; // composição

    
    public Estoque() {
        this.itens = new ArrayList<>();
    }

    
    public Estoque(Item item) {
        this.itens = new ArrayList<>();
        this.itens.add(item);
    }

    
    public Estoque(List<Item> itens) {
        this.itens = itens;
    }

    
    public boolean adicionarItem(Item item) {
        if (item != null) {
            itens.add(item);
            return true;
        }
        return false;
    }

    public boolean removerItem(Item item) {
        if (item != null && itens.contains(item)) {
            itens.remove(item);
            return true;
        }
        return false;
    }

    
    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }

    
    @Override
    public String toString() {
        return "Estoque com " + (itens != null ? itens.size() : 0) + " itens";
    }
}
