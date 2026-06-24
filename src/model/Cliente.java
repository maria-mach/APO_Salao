package model;

import java.util.List;

public class Cliente {
    protected Integer id;
    protected String nome;
    protected String senha;
    protected String telefone;
    protected String email;
    protected String cpf;
    protected String preferencia;
    protected List<Agendamento> agendamentos; 

    // estatico: contador de clientes criados
    private static int totalClientes = 0;

    
    public Cliente() {
        totalClientes++;
    }

    
    public Cliente(Integer id) {
        this.id = id;
        totalClientes++;
    }

    
    public Cliente(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
        totalClientes++;
    }

    
    public Cliente(Integer id, String nome, String senha, String telefone, String email, String cpf,
                   String preferencia, List<Agendamento> agendamentos) {
        this.id = id;
        this.nome = nome;
        this.senha = senha;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.preferencia = preferencia;
        this.agendamentos = agendamentos;
        totalClientes++;
    }

    
    public boolean validarEmail() {
        return (this.email != null && this.email.contains("@"));
    }

    public boolean validarCpf() {
        return (this.cpf != null && this.cpf.toString().length() == 11);
    }

    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getPreferencia() { return preferencia; }
    public void setPreferencia(String preferencia) { this.preferencia = preferencia; }

    public List<Agendamento> getAgendamentos() { return agendamentos; }
    public void setAgendamentos(List<Agendamento> agendamentos) { this.agendamentos = agendamentos; }

    // metodo estático para acessar o contador
    public static int getTotalClientes() {
        return totalClientes;
    }

    @Override
    public String toString() {
        return "Cliente #" + (id != null ? id : "novo") +
               " - Nome: " + nome +
               " - Email: " + email +
               " - Preferência: " + preferencia;
    }
}
