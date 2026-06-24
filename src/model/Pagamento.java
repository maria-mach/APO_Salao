package model;

public class Pagamento {
    private Integer agendamentoId;
    private String titularConta;
    private MetodoPagamento metodoPagamento;
    private String numeroCartao;
    private String dataVencimento;
    private Integer cvv;
    private String autorizacao;

    public enum MetodoPagamento {
        CREDITO, DEBITO
    }

    // Construtor vazio
    public Pagamento() {
    }

    // Construtor parcial
    public Pagamento(Integer agendamentoId, MetodoPagamento metodoPagamento) {
        this.agendamentoId = agendamentoId;
        this.metodoPagamento = metodoPagamento;
    }

    // Construtor completo
    public Pagamento(Integer agendamentoId, String titularConta, MetodoPagamento metodoPagamento,
                     String numeroCartao, String dataVencimento, Integer cvv, String autorizacao) {
        this.agendamentoId = agendamentoId;
        this.titularConta = titularConta;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.dataVencimento = dataVencimento;
        this.cvv = cvv;
        this.autorizacao = autorizacao;
    }

    // Método de negócio simples
    public boolean validarCartao() {
        if (this.numeroCartao != null && this.numeroCartao.length() >= 13 && this.numeroCartao.length() <= 19) {
            return true;
        }
        return false;
    }

    public boolean confirmarPagamento() {
        if (this.metodoPagamento != null && this.agendamentoId != null) {
            return true;
        }
        return false;
    }

    // Getters e Setters básicos
    public Integer getAgendamentoId() { return agendamentoId; }
    public void setAgendamentoId(Integer agendamentoId) { this.agendamentoId = agendamentoId; }

    public String getTitularConta() { return titularConta; }
    public void setTitularConta(String titularConta) { this.titularConta = titularConta; }

    public MetodoPagamento getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(MetodoPagamento metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }

    public String getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(String dataVencimento) { this.dataVencimento = dataVencimento; }

    public Integer getCvv() { return cvv; }
    public void setCvv(Integer cvv) { this.cvv = cvv; }
    
    public String getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(String autorizacao) {
		this.autorizacao = autorizacao;
	}

	
}
