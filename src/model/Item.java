package model;

import java.time.LocalDate;

public class Item {
    private String nomeItem;
    private String marca;
    private String modelo;
    private LocalDate dataValidade;
    private String fornecedor;
    private float custo;
    private Integer quantidade;
    private Integer limiteMin;

    // Construtor vazio
    public Item() {
    }

    // Construtor parcial
    public Item(String nomeItem, String marca) {
        this.nomeItem = nomeItem;
        this.marca = marca;
    }

    // Construtor completo
    public Item(String nomeItem, String marca, String modelo, LocalDate dataValidade,
                String fornecedor, float custo, Integer quantidade, Integer limiteMin) {
        this.nomeItem = nomeItem;
        this.marca = marca;
        this.modelo = modelo;
        this.dataValidade = dataValidade;
        this.fornecedor = fornecedor;
        this.custo = custo;
        this.quantidade = quantidade;
        this.limiteMin = limiteMin;
    }

    // Método de negócio simples
    public boolean verificarEstoqueMinimo() {
        if (this.quantidade != null && this.limiteMin != null) {
            return this.quantidade >= this.limiteMin;
        }
        return false;
    }

    // Getters e Setters básicos
    public String getNomeItem() { return nomeItem; }
    public void setNomeItem(String nomeItem) { this.nomeItem = nomeItem; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public LocalDate getDataValidade() { return dataValidade; }
    public void setDataValidade(LocalDate dataValidade) { this.dataValidade = dataValidade; }

    public String getFornecedor() { return fornecedor; }
    public void setFornecedor(String fornecedor) { this.fornecedor = fornecedor; }

    public float getCusto() { return custo; }
    public void setCusto(float custo) { this.custo = custo; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Integer getLimiteMin() { return limiteMin; }
    public void setLimiteMin(Integer limiteMin) { this.limiteMin = limiteMin; }

    // Sobrescrita para depuração
    @Override
    public String toString() {
        return "Item: " + nomeItem +
               " | Marca: " + marca +
               " | Modelo: " + modelo +
               " | Validade: " + dataValidade +
               " | Fornecedor: " + fornecedor +
               " | Custo: " + custo +
               " | Quantidade: " + quantidade +
               " | Limite mínimo: " + limiteMin;
    }
}
