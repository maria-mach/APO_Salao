package dao;

import java.sql.*;
import banco.DBConnection;
import model.Pagamento;

public class PagamentoDAO {

    private Connection conn;

    public PagamentoDAO() {
        DBConnection conexao = new DBConnection();
        this.conn = conexao.getConnection();
    }
    
    public void inserirPagamento(Pagamento pg) 
    	throws SQLException {
        String sql = "{CALL inserir_pagamento(?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cs = conn.prepareCall(sql)) {
            cs.setInt(1, pg.getAgendamentoId());
            cs.setString(2, pg.getTitularConta());
            cs.setString(3, pg.getNumeroCartao());
            cs.setString(4, pg.getDataVencimento());
            cs.setInt(5, pg.getCvv());
            cs.setString(6, pg.getMetodoPagamento().toString());
            cs.setString(7, pg.getAutorizacao());

            cs.executeUpdate();
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