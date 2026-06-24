package dao;

import model.Agendamento;
import model.Prestador;
import model.Servico;
import model.Cliente;
import banco.DBConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO { 
    private Connection conn;

    public AgendamentoDAO() {
        DBConnection conexao = new DBConnection();
        this.conn = conexao.getConnection();
    }
    
    public Connection getConnection() {
        return this.conn;
    }
    
    public Cliente loginCliente(String usuario, String senha) {
        Cliente cli = null;
        String sql = "{CALL BuscarClientePorLogin(?,?)}";
        
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, usuario);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) { 
                    cli = new Cliente();
                    cli.setId(rs.getInt("id"));
                    cli.setNome(rs.getString("nome"));
                    cli.setSenha(rs.getString("senha"));
                    cli.setTelefone(rs.getString("telefone"));
                    cli.setEmail(rs.getString("email"));
                    cli.setCpf(rs.getString("cpf"));
                    cli.setPreferencia(rs.getString("preferencia"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return cli; 
    }

    
    public List<Agendamento> buscarPorCliente(int idCliente) {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "{CALL buscar_agendamentos_cliente_validos(?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento ag = new Agendamento();
                    ag.setId(rs.getInt("id_agendamento"));

                    LocalDateTime dataRealizacao = rs.getObject("data_realizacao", LocalDateTime.class);
                    ag.setDataRealizacao(dataRealizacao);

                    Prestador prestador = new Prestador();
                    prestador.setId(rs.getInt("id_prestador"));
                    prestador.setNome(rs.getString("nome_prestador"));
                    ag.setPrestador(prestador);
                    
                    Servico servico = new Servico();
                    servico.setId(rs.getInt("id_servico"));
                    servico.setNomeServico(rs.getString("nome_servico"));
                    servico.setEspecificacao(rs.getString("especificacao")); // se precisar
                    servico.setPreco(rs.getFloat("preco")); // ou float, dependendo do seu model
                    List<Servico> servicos = new ArrayList<>();
                    servicos.add(servico);
                    ag.setServico(servicos);

                    String status = rs.getString("status_agendamento");
                    if (status != null && !status.isBlank()) {
                        try {
                            ag.setStatusAgendamento(Agendamento.StatusAgendamento.valueOf(status.trim().toUpperCase()));
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Status inválido no banco: " + status);
                        }
                    }

                    String statusPagamento = rs.getString("status_pagamento");
                    if (statusPagamento != null && !statusPagamento.isBlank()) {
                        try {
                            ag.setStatusPagamento(
                                Agendamento.StatusPagamento.valueOf(statusPagamento.trim().toUpperCase())
                            );
                        } catch (IllegalArgumentException ex) {
                            System.err.println("Status de pagamento inválido no banco: " + statusPagamento);
                        }
                    }

                    lista.add(ag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }


    public boolean atualizarStatus(int idAgendamento) {
        String sql = "CALL cancelar_agendamento(?)"; 
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, idAgendamento);
            stmt.execute();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    
    public void fecharConexao() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}