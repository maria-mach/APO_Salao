package model;

import java.util.List;
import java.util.ArrayList;

public class Servico {
    private Integer id;
    private String nomeServico;
    private String especificacao;
    private float preco;
    private List<Item> itens;              // composição
    private List<Prestador> prestadores;   // agregação

    // Construtor vazio
    public Servico() {
        this.itens = new ArrayList<>();
    }

    // Construtor simples (sobrecarga)
    public Servico(Integer id) {
        this.id = id;
        this.itens = new ArrayList<>();
    }

    // Construtor com atributos básicos
    public Servico(Integer id, String nomeServico, String especificacao, float preco) {
        this.id = id;
        this.nomeServico = nomeServico;
        this.especificacao = especificacao;
        this.preco = preco;
        this.itens = new ArrayList<>();
    }

    // Construtor com listas (composição e agregação)
    public Servico(String nomeServico, String especificacao, float preco,
                   List<Prestador> prestadores, List<Item> dadosItens) {
        this.nomeServico = nomeServico;
        this.especificacao = especificacao;
        this.preco = preco;
        this.prestadores = prestadores;
        this.itens = new ArrayList<>(dadosItens);
    }

    // Método de negócio simples
    public boolean verificarPrecoValido() {
        return this.preco >= 0;
    }

    public boolean possuiItens() {
        return (this.itens != null && !this.itens.isEmpty());
    }

    // Getters e Setters básicos
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNomeServico() { return nomeServico; }
    public void setNomeServico(String nomeServico) { this.nomeServico = nomeServico; }

    public String getEspecificacao() { return especificacao; }
    public void setEspecificacao(String especificacao) { this.especificacao = especificacao; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public List<Item> getItens() { return itens; }
    public void setItens(List<Item> itens) { this.itens = itens; }

    public List<Prestador> getPrestadores() { return prestadores; }
    public void setPrestadores(List<Prestador> prestadores) { this.prestadores = prestadores; }

    // Sobrescrita para depuração
    @Override
    public String toString() {
        return "Servico #" + (id != null ? id : "novo") +
               " - Nome: " + nomeServico +
               " - Preço: " + preco +
               " - Itens: " + (itens != null ? itens.size() : 0);
    }
}
