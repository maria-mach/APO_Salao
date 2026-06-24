package controller;

import dao.AgendamentoDAO;
import dao.AgendaDAO;
import model.Agendamento;
import model.Agenda;
import model.Cliente;

import java.sql.Connection;
import java.util.List;

public class CancelamentoController {
    private AgendamentoDAO agendamentoDAO;
    private AgendaDAO agendaDAO;
    private Connection conn;

    public CancelamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
        this.conn = agendamentoDAO.getConnection(); // tive que expor a conexao
        this.agendaDAO = new AgendaDAO(conn);
    }

    public Cliente logarCliente(String usuario, String senha) {
        return agendamentoDAO.loginCliente(usuario, senha);
    }

    public List<Agendamento> buscarAgendamentosPorCliente(int idCliente) {
        return agendamentoDAO.buscarPorCliente(idCliente);
    }

    public boolean cancelarAgendamento(int idAgendamento) {
        return agendamentoDAO.atualizarStatus(idAgendamento);
    }

    // Novo método para buscar agenda do cliente
    public Agenda buscarAgendaDoCliente(int idCliente) {
        try {
            return agendaDAO.buscarAgendaDoCliente(idCliente);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Fecha conexões de ambos DAOs
    public void fecharConexao() {
        agendamentoDAO.fecharConexao();
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}