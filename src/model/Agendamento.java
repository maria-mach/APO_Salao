package model;

import java.util.List;
import java.time.LocalDateTime;

public class Agendamento {
    private Integer id;  
    private Cliente cliente;    
    private Prestador prestador;
    private LocalDateTime dataRealizacao;
    private List<Servico> servico; // composicao
    private LocalDateTime dataCriacao;
    private StatusAgendamento statusAgendamento;
    private StatusPagamento statusPagamento;

    public enum StatusAgendamento { AGENDADO, EM_ANDAMENTO, CANCELADO, REALIZADO }
    public enum StatusPagamento   { PAGO, EM_ABERTO }
	
    
    public Agendamento() {
    	
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	public Prestador getPrestador() {
		return prestador;
	}


	public void setPrestador(Prestador prestador) {
		this.prestador = prestador;
	}


	public LocalDateTime getDataRealizacao() {
		return dataRealizacao;
	}


	public void setDataRealizacao(LocalDateTime dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}


	public List<Servico> getServico() {
		return servico;
	}


	public void setServico(List<Servico> servico) {
		this.servico = servico;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public StatusAgendamento getStatusAgendamento() {
		return statusAgendamento;
	}


	public void setStatusAgendamento(StatusAgendamento statusAgendamento) {
		this.statusAgendamento = statusAgendamento;
	}


	public StatusPagamento getStatusPagamento() {
		return statusPagamento;
	}


	public void setStatusPagamento(StatusPagamento statusPagamento) {
		this.statusPagamento = statusPagamento;
	}
    
    


}
