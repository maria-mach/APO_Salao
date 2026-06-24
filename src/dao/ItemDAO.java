package dao;

import model.Item;
import java.sql.*;

import banco.DBConnection;

public class ItemDAO {
    private Connection conn;

    public ItemDAO() {
        DBConnection conexao = new DBConnection();
        this.conn = conexao.getConnection();
    }
    
    public ResultSet buscaServicos() {
        ResultSet rs = null;
        String sql = "{CALL BuscaServicos()}";

        try {
            CallableStatement stmt = conn.prepareCall(sql);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }


    public boolean inserir(Item item, int idServico) {
        String sql = "{CALL inserir_item(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setString(1, item.getNomeItem());
            stmt.setString(2, item.getMarca());
            stmt.setString(3, item.getModelo());

            if (item.getDataValidade() != null) {
                stmt.setDate(4, Date.valueOf(item.getDataValidade()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.setString(5, item.getFornecedor());
            stmt.setFloat(6, item.getCusto());
            stmt.setInt(7, item.getQuantidade() != null ? item.getQuantidade() : 0);
            stmt.setInt(8, item.getLimiteMin() != null ? item.getLimiteMin() : 0);

            stmt.setInt(9, idServico);

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
