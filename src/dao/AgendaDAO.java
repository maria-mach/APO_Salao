package dao;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import model.Agenda;
import model.Prestador;

public class AgendaDAO {
    private Connection conn;

    public AgendaDAO(Connection conn) {
        this.conn = conn;
    }

    public Agenda buscarAgendaDoCliente(int idCliente) throws SQLException {
        Agenda agenda = new Agenda();
        List<LocalDateTime> horarios = new ArrayList<>();

        CallableStatement stmt = conn.prepareCall("{CALL BuscarAgendaDoCliente(?)}");
        stmt.setInt(1, idCliente);

        ResultSet rs = stmt.executeQuery();
        Prestador prestador = null;

        while (rs.next()) {
            agenda.setId(rs.getInt("id_agenda"));

            prestador = new Prestador();
            prestador.setId(rs.getInt("id_prestador"));
            prestador.setNome(rs.getString("nome_prestador"));
            agenda.setPrestador(prestador);

            horarios.add(rs.getObject("data_hora", LocalDateTime.class));

        }

        agenda.setHorario(horarios);

        rs.close();
        stmt.close();

        return agenda;
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