package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

import controller.CancelamentoController;
import model.Agenda;
import model.Agendamento;
import model.Servico;

import java.util.List;
import java.time.LocalDateTime;
import java.math.BigDecimal;

public class ListaAgendamentosView {

    private Shell shell;
    private Text txtNomeCliente;
    private Text txtIdCliente;
    private Table tabelaAgendamentos;
    private Button btnCancelar;
    private Button btnPagar;
    private Button btnLogout;

    public ListaAgendamentosView(Display display, String nomeCliente, int idCliente) {
        shell = new Shell(display);
        shell.setText("Cancelar Agendamento");
        shell.setSize(700, 450);
        shell.setLayout(null);

        Label lblNome = new Label(shell, SWT.NONE);
        lblNome.setText("Nome do Cliente:");
        lblNome.setBounds(10, 10, 100, 20);

        txtNomeCliente = new Text(shell, SWT.BORDER);
        txtNomeCliente.setBounds(120, 10, 180, 20);
        txtNomeCliente.setText(nomeCliente);
        txtNomeCliente.setEditable(false);

        Label lblId = new Label(shell, SWT.NONE);
        lblId.setText("ID do Cliente:");
        lblId.setBounds(310, 10, 80, 20);

        txtIdCliente = new Text(shell, SWT.BORDER);
        txtIdCliente.setBounds(400, 10, 80, 20);
        txtIdCliente.setText(String.valueOf(idCliente));
        txtIdCliente.setEditable(false);

        Button btnListar = new Button(shell, SWT.PUSH);
        btnListar.setText("LISTAR AGENDAMENTOS");
        btnListar.setBounds(10, 40, 670, 30);

        tabelaAgendamentos = new Table(shell, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        tabelaAgendamentos.setBounds(11, 80, 670, 200);
        tabelaAgendamentos.setHeaderVisible(true);
        tabelaAgendamentos.setLinesVisible(true);

        String[] colunas = {"ID", "Serviço", "Data", "Status Agendamento", "Status Pagamento"};
        int[] larguras = {50, 150, 150, 150, 150};

        for (int i = 0; i < colunas.length; i++) {
            TableColumn column = new TableColumn(tabelaAgendamentos, SWT.NONE);
            column.setText(colunas[i]);
            column.setWidth(larguras[i]);
        }

        btnCancelar = new Button(shell, SWT.PUSH);
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBounds(150, 300, 120, 30);
        btnCancelar.setEnabled(false);

        btnPagar = new Button(shell, SWT.PUSH);
        btnPagar.setText("PAGAR");
        btnPagar.setBounds(300, 300, 120, 30);
        btnPagar.setEnabled(false);

        btnLogout = new Button(shell, SWT.PUSH);
        btnLogout.setText("LOGOUT");
        btnLogout.setBounds(450, 300, 120, 30);

        CancelamentoController controller = new CancelamentoController();
        Agenda agenda = controller.buscarAgendaDoCliente(idCliente);

        System.out.println("Agenda instanciada:");
        System.out.println("ID: " + agenda.getId());
        System.out.println("Prestador: " + (agenda.getPrestador() != null ? agenda.getPrestador().getNome() : "null"));
        for (LocalDateTime h : agenda.getHorario()) {
            System.out.println("Horário: " + h);
        }

        btnListar.addListener(SWT.Selection, e -> {
            tabelaAgendamentos.removeAll();
            List<Agendamento> agendamentos = controller.buscarAgendamentosPorCliente(idCliente);

            if (agendamentos.isEmpty()) {
                TableItem item = new TableItem(tabelaAgendamentos, SWT.NONE);
                item.setText(new String[]{"Nenhum agendamento encontrado", "", "", "", ""});
            } else {
                for (Agendamento ag : agendamentos) {
                    List<Servico> servicos = ag.getServico();
                    String nomeServico = (servicos != null && !servicos.isEmpty())
                            ? servicos.get(0).getNomeServico()
                            : "Serviço desconhecido";

                    TableItem item = new TableItem(tabelaAgendamentos, SWT.NONE);
                    item.setText(new String[]{
                            String.valueOf(ag.getId()),
                            nomeServico,
                            ag.getDataRealizacao() != null ? ag.getDataRealizacao().toString() : "",
                            ag.getStatusAgendamento() != null ? ag.getStatusAgendamento().name() : "indefinido",
                            ag.getStatusPagamento() != null ? ag.getStatusPagamento().name() : "indefinido"
                    });
                }
            }
        });

        tabelaAgendamentos.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int index = tabelaAgendamentos.getSelectionIndex();
                if (index != -1) {
                    Agendamento ag = controller.buscarAgendamentosPorCliente(idCliente).get(index);

                    btnCancelar.setEnabled(true);

                    if (ag.getStatusPagamento() == Agendamento.StatusPagamento.PAGO) {
                        btnPagar.setEnabled(false);
                    } else {
                        btnPagar.setEnabled(true);
                    }
                }
            }
        });

        btnCancelar.addListener(SWT.Selection, e -> {
            int index = tabelaAgendamentos.getSelectionIndex();
            if (index != -1) {
                Agendamento ag = controller.buscarAgendamentosPorCliente(idCliente).get(index);

                if (ag.getStatusPagamento() == Agendamento.StatusPagamento.PAGO) {
                    MessageBox aviso = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                    aviso.setText("Pagamento Estornado");
                    aviso.setMessage("O agendamento já estava pago. O pagamento será estornado antes da conclusão.");
                    aviso.open();
                }

                boolean sucesso = controller.cancelarAgendamento(ag.getId());

                if (sucesso) {
                    ag.setStatusAgendamento(Agendamento.StatusAgendamento.CANCELADO);
                    ag.setStatusPagamento(Agendamento.StatusPagamento.EM_ABERTO);

                    System.out.println("Horários antes do cancelamento: " + agenda.getHorario().size());

                    if (agenda != null && agenda.getHorario() != null) {
                        agenda.getHorario().remove(ag.getDataRealizacao());
                    }
                    tabelaAgendamentos.remove(index);

                    System.out.println("Horários depois do cancelamento: " + agenda.getHorario().size());
                    System.out.println("Agendamento " + ag.getId() + " cancelado.");
                    System.out.println("Novo status: " + ag.getStatusAgendamento());
                    System.out.println("Novo status pagamento: " + ag.getStatusPagamento());
                    System.out.println("Horários restantes na agenda:");
                    for (LocalDateTime h : agenda.getHorario()) {
                        System.out.println("Horário: " + h);
                    }
                }

                MessageBox box = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                box.setText("Cancelar");
                box.setMessage(sucesso
                        ? "Agendamento cancelado:\nAgendamento " + ag.getId()
                        : "Erro ao cancelar agendamento.");
                box.open();
            }
        });

        btnPagar.addListener(SWT.Selection, e -> {
            int index = tabelaAgendamentos.getSelectionIndex();
            if (index != -1) {
                Agendamento ag = controller.buscarAgendamentosPorCliente(idCliente).get(index);

                BigDecimal preco = BigDecimal.ZERO;
                if (ag.getServico() != null && !ag.getServico().isEmpty()) {
                    Servico servico = ag.getServico().get(0);
                    preco = BigDecimal.valueOf(servico.getPreco()); // conversão segura
                }

                shell.dispose();

                PagamentoView pagamentoView = new PagamentoView(ag.getId(), preco);
                pagamentoView.open();
            }
        });

        btnLogout.addListener(SWT.Selection, e -> {
            shell.dispose();

            Shell loginShell = new Shell(display);
            loginShell.setText("Login - Salão de Beleza");
            loginShell.setSize(500, 360);
            loginShell.setLayout(new FillLayout());

            new LoginView(loginShell, SWT.NONE);

            loginShell.open();
            while (!loginShell.isDisposed()) {
                if (!display.readAndDispatch()) display.sleep();
            }
        });

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
    }
}