package controller;

import dao.ItemDAO;
import model.Item;
import model.Servico;
import java.time.LocalDate;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;


public class CadastrarItemController {
    private ItemDAO dao;

    public CadastrarItemController() {
        this.dao = new ItemDAO();
    }
    
    ItemDAO conexaoItem = new ItemDAO();
    
    public List<Servico> CriaListaServicos() {
        List<Servico> listaSv = new ArrayList<>();
        ResultSet rs = null;

        try {
            rs = conexaoItem.buscaServicos(); 
            while (rs.next()) {
                Servico sv = new Servico();
                sv.setId(rs.getInt("id"));
                sv.setNomeServico(rs.getString("nome_servico"));
                sv.setEspecificacao(rs.getString("especificacao"));
                sv.setPreco(rs.getFloat("preco"));

                listaSv.add(sv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
       
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

		for (Servico serv : listaSv) {
		    System.out.println("ID: " + serv.getId());
		    System.out.println("Nome: " + serv.getNomeServico());
		    System.out.println("Especificação: " + serv.getEspecificacao());
		    System.out.println("Preço: " + serv.getPreco());
		    System.out.println("-----------------------------");
    	}
    	return listaSv;
    }
    public boolean cadastrarItem(
    		Servico sv,
    		String nome,
    		String marca, 
    		String modelo,
    		String validadeStr,
    		String fornecedor,
    		float custo,
    		int quantidade,
    		int limiteMin) {
        try {
            LocalDate validade = LocalDate.parse(validadeStr);

            Item item = new Item();
            item.setNomeItem(nome);
            item.setMarca(marca);
            item.setModelo(modelo);
            item.setDataValidade(validade);
            item.setFornecedor(fornecedor);
            item.setCusto(custo);
            item.setQuantidade(quantidade);
            item.setLimiteMin(limiteMin);
            
            List<Item> itens = new ArrayList<>();
            itens.add(item);
            sv.setItens(itens);

            return dao.inserir(item, sv.getId());
        } finally {
            dao.fecharConexao();
        }
    }
}
