package model;

public class Prestador {
    private Integer id;
    private String nome;
    private String senha;
    private Integer telefone;
    private String email;
    private Integer cpf;
    private Servico servicos; // associação
    private String profissionalizacao;

    // Construtor vazio
    public Prestador() {
    }

    // Construtor simples (sobrecarga)
    public Prestador(Integer id) {
        this.id = id;
    }

    // Construtor completo (sobrecarga)
    public Prestador(Integer id, String nome, String senha, Integer telefone,
                     String email, Integer cpf, Servico servicos, String profissionalizacao) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.servicos = servicos;
        this.profissionalizacao = profissionalizacao;
    }

    // Método de negócio simples
    public boolean validarEmail() {
        return (this.email != null && this.email.contains("@"));
    }

    public boolean validarCpf() {
        return (this.cpf != null && this.cpf.toString().length() == 11);
    }

    // Getters e Setters básicos
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Integer getTelefone() { return telefone; }
    public void setTelefone(Integer telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Integer getCpf() { return cpf; }
    public void setCpf(Integer cpf) { this.cpf = cpf; }

    public Servico getServicos() { return servicos; }
    public void setServicos(Servico servicos) { this.servicos = servicos; }

    public String getProfissionalizacao() { return profissionalizacao; }
    public void setProfissionalizacao(String profissionalizacao) { this.profissionalizacao = profissionalizacao; }

    // Sobrescrita para depuração
    @Override
    public String toString() {
        return "Prestador #" + (id != null ? id : "novo") +
               " - Nome: " + nome +
               " - Profissionalização: " + profissionalizacao +
               " - Serviço: " + (servicos != null ? servicos.getNomeServico() : "—");
    }
}
