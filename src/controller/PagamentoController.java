package controller;

import dao.PagamentoDAO;
import model.Pagamento;


public class PagamentoController {

    private String gerarCodigoAutorizacao() {
        return "AUTH-" + System.currentTimeMillis();
    }

    public boolean processarPagamento(int idAgendamento,
                                      String tipo,
                                      String titular,
                                      String numeroCartao,
                                      String vencimento,
                                      String cvv) {
        Pagamento.MetodoPagamento metodo;

        if ("CREDITO".equalsIgnoreCase(tipo)) {
            metodo = Pagamento.MetodoPagamento.CREDITO;
        } else {
            metodo = Pagamento.MetodoPagamento.DEBITO;
        }
    	
        try {
            String codigoAutorizacao = gerarCodigoAutorizacao();
            int cvvInt = Integer.parseInt(cvv.trim());
            Pagamento pg = new Pagamento(
            	idAgendamento,
            	titular,
            	metodo,
            	numeroCartao,
            	vencimento,
            	cvvInt,
            	codigoAutorizacao
            );
            		
            PagamentoDAO dao = new PagamentoDAO();
            dao.inserirPagamento(pg);
            dao.fecharConexao();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}