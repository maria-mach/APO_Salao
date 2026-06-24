package view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Composite;


import controller.CadastrarItemController;
import model.Servico;

public class CadastrarItemView {

    protected Shell shell;
    private Text txtNomeItem;
    private Text txtMarca;
    private Text txtModelo;
    private Text txtValidade;
    private Text txtFornecedor;
    private Text txtCusto;
    private Text txtQuantidade;
    private Text txtLimiteMin;

    private Table tabelaServicos;
    private Servico servicoSelecionado;

    public static void main(String[] args) {
        try {
            CadastrarItemView window = new CadastrarItemView();
            window.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    protected void createContents() {
        shell = new Shell();
        shell.setSize(1200, 480);
        shell.setText("Cadastro de Item e Serviços");

        SashForm sashForm = new SashForm(shell, SWT.HORIZONTAL);
        sashForm.setBounds(10, 10, 1180, 380);


        sashForm.setLayout(new FillLayout());

        tabelaServicos = new Table(sashForm, SWT.BORDER | SWT.FULL_SELECTION);
        tabelaServicos.setBounds(10, 10, 600, 120); 
        tabelaServicos.setHeaderVisible(true);
        tabelaServicos.setLinesVisible(true);

        String[] colunas = {"ID", "Nome Serviço", "Especificação", "Preço"};
        int[] larguras = {50, 200, 300, 100};

        for (int i = 0; i < colunas.length; i++) {
            TableColumn col = new TableColumn(tabelaServicos, SWT.NONE);
            col.setText(colunas[i]);
            col.setWidth(larguras[i]);
        }

        Composite rightPanel = new Composite(sashForm, SWT.NONE);
        rightPanel.setLayout(null);
        rightPanel.setEnabled(false);

        Label lblNome = new Label(rightPanel, SWT.NONE);
        lblNome.setText("Nome Item:");
        lblNome.setBounds(10, 60, 120, 20);
        txtNomeItem = new Text(rightPanel, SWT.BORDER);
        txtNomeItem.setBounds(140, 60, 300, 20);

        Label lblMarca = new Label(rightPanel, SWT.NONE);
        lblMarca.setText("Marca:");
        lblMarca.setBounds(10, 90, 120, 20);
        txtMarca = new Text(rightPanel, SWT.BORDER);
        txtMarca.setBounds(140, 90, 300, 20);

        Label lblModelo = new Label(rightPanel, SWT.NONE);
        lblModelo.setText("Modelo:");
        lblModelo.setBounds(10, 120, 120, 20);
        txtModelo = new Text(rightPanel, SWT.BORDER);
        txtModelo.setBounds(140, 120, 300, 20);

        Label lblValidade = new Label(rightPanel, SWT.NONE);
        lblValidade.setText("Validade (ano-mes-dia):");
        lblValidade.setBounds(10, 150, 150, 20);
        txtValidade = new Text(rightPanel, SWT.BORDER);
        txtValidade.setBounds(170, 150, 270, 20);

        Label lblFornecedor = new Label(rightPanel, SWT.NONE);
        lblFornecedor.setText("Fornecedor:");
        lblFornecedor.setBounds(10, 180, 120, 20);
        txtFornecedor = new Text(rightPanel, SWT.BORDER);
        txtFornecedor.setBounds(140, 180, 300, 20);

        Label lblCusto = new Label(rightPanel, SWT.NONE);
        lblCusto.setText("Custo:");
        lblCusto.setBounds(10, 210, 120, 20);
        txtCusto = new Text(rightPanel, SWT.BORDER);
        txtCusto.setBounds(140, 210, 300, 20);

        Label lblQuantidade = new Label(rightPanel, SWT.NONE);
        lblQuantidade.setText("Quantidade:");
        lblQuantidade.setBounds(10, 240, 120, 20);
        txtQuantidade = new Text(rightPanel, SWT.BORDER);
        txtQuantidade.setBounds(140, 240, 300, 20);

        Label lblLimite = new Label(rightPanel, SWT.NONE);
        lblLimite.setText("Limite Min:");
        lblLimite.setBounds(10, 270, 120, 20);
        txtLimiteMin = new Text(rightPanel, SWT.BORDER);
        txtLimiteMin.setBounds(140, 270, 300, 20);

        Button btnCadastrar = new Button(rightPanel, SWT.PUSH);
        btnCadastrar.setText("CONFIRMAR CADASTRO");
        btnCadastrar.setBounds(10, 320, 460, 30);
        sashForm.setWeights(new int[] {469, 408});

        CadastrarItemController controller = new CadastrarItemController();
        Button btnListarServicos = new Button(shell, SWT.PUSH);
        btnListarServicos.setText("Listar Serviços");
        btnListarServicos.setBounds(10, 400, 200, 30);

        Button btnSelecionar = new Button(shell, SWT.PUSH);
        btnSelecionar.setText("Selecionar Serviço");
        btnSelecionar.setBounds(220, 400, 200, 30);

        btnListarServicos.addListener(SWT.Selection, e -> {
            tabelaServicos.removeAll();
            List<Servico> servicos = controller.CriaListaServicos();

            if (servicos.isEmpty()) {
                TableItem item = new TableItem(tabelaServicos, SWT.NONE);
                item.setText(new String[]{"Nenhum serviço encontrado", "", "", ""});
            } else {
            	for (Servico sv : servicos) {
            	    TableItem item = new TableItem(tabelaServicos, SWT.NONE);
            	    item.setText(new String[]{
            	        String.valueOf(sv.getId()),
            	        sv.getNomeServico(),
            	        sv.getEspecificacao(),
            	        String.valueOf(sv.getPreco())
            	    });
            	    item.setData(sv); 
            	}
            }
        });

        btnSelecionar.addListener(SWT.Selection, e -> {
            TableItem[] selecionados = tabelaServicos.getSelection();
            if (selecionados.length > 0) {
                Servico servicoSelecionado = (Servico) selecionados[0].getData(); // pega o objeto
                rightPanel.setEnabled(true);

                MessageBox msg = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                msg.setText("Serviço Selecionado");
                msg.setMessage("Você selecionou: " + servicoSelecionado.getNomeServico());
                msg.open();

                this.servicoSelecionado = servicoSelecionado;
            } else {
                MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                msg.setText("Aviso");
                msg.setMessage("Selecione um serviço na tabela antes de continuar.");
                msg.open();
            }
        });

        
        btnCadastrar.addListener(SWT.Selection, event -> {
            try {
                if (servicoSelecionado == null) {
                    MessageBox msg = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                    msg.setText("Aviso");
                    msg.setMessage("Selecione um serviço antes de cadastrar o item.");
                    msg.open();
                    return;
                }

                String nome = txtNomeItem.getText();
                String marca = txtMarca.getText();
                String modelo = txtModelo.getText();
                String validadeStr = txtValidade.getText();
                String fornecedor = txtFornecedor.getText();
                float custo = Float.parseFloat(txtCusto.getText());
                int quantidade = Integer.parseInt(txtQuantidade.getText());
                int limiteMin = Integer.parseInt(txtLimiteMin.getText());

                if (controller.cadastrarItem(servicoSelecionado, nome, marca, modelo, validadeStr, fornecedor, custo, quantidade, limiteMin)) {
                    MessageBox msg = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                    msg.setText("Sucesso");
                    msg.setMessage("Item cadastrado com sucesso: " + nome);
                    msg.open();
                } else {
                    MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                    msg.setText("Erro");
                    msg.setMessage("Falha ao cadastrar item.");
                    msg.open();
                }

                txtNomeItem.setText("");
                txtMarca.setText("");
                txtModelo.setText("");
                txtValidade.setText("");
                txtFornecedor.setText("");
                txtCusto.setText("");
                txtQuantidade.setText("");
                txtLimiteMin.setText("");

            } catch (Exception e) {
                MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                msg.setText("Erro");
                msg.setMessage("Falha ao cadastrar item: " + e.getMessage());
                msg.open();
            }
        });
    }

}
