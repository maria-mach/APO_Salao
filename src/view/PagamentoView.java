package view;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import controller.PagamentoController;
import java.math.BigDecimal;

public class PagamentoView {

    protected Shell shell;
    private int idAgendamento;
    private BigDecimal preco;

    public PagamentoView(int idAgendamento, BigDecimal preco) {
        this.idAgendamento = idAgendamento;
        this.preco = preco;
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
        shell.setText("Pagamento");
        shell.setSize(780, 480);
        shell.setLayout(new GridLayout(2, true));

        // grupo cliente
        Group left = new Group(shell, SWT.NONE);
        left.setText("Cliente");
        left.setLayout(new GridLayout(1, false));
        left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        new Label(left, SWT.NONE).setText("ID Agendamento:");
        Composite idRow = new Composite(left, SWT.NONE);
        idRow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        idRow.setLayout(new GridLayout(2, false));

        Text idAgendamentoField = new Text(idRow, SWT.BORDER);
        idAgendamentoField.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        idAgendamentoField.setText(String.valueOf(idAgendamento));
        idAgendamentoField.setEditable(false);

        new Label(left, SWT.NONE).setText("Preço do serviço:");
        Label precoValorLabel = new Label(left, SWT.NONE);
        precoValorLabel.setText("R$ " + preco.toPlainString());

        Group right = new Group(shell, SWT.NONE);
        right.setText("Pagamento");
        right.setLayout(new GridLayout(2, false));
        right.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        new Label(right, SWT.NONE).setText("Tipo:");
        Composite tipoComp = new Composite(right, SWT.NONE);
        tipoComp.setLayout(new RowLayout());
        tipoComp.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        Button credito = new Button(tipoComp, SWT.RADIO); credito.setText("CRÉDITO");
        Button debito = new Button(tipoComp, SWT.RADIO); debito.setText("DÉBITO");

        new Label(right, SWT.NONE).setText("NOME DO TITULAR");
        Text titular = new Text(right, SWT.BORDER);
        titular.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

        new Label(right, SWT.NONE).setText("NÚMERO DO CARTÃO");
        Text card = new Text(right, SWT.BORDER);
        card.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        card.setTextLimit(19);
        card.addListener(SWT.Verify, e -> e.doit = e.text.matches("\\d*"));

        new Label(right, SWT.NONE).setText("VENCIMENTO (MM/AA)");
        Text venc = new Text(right, SWT.BORDER);
        venc.setTextLimit(5);

        new Label(right, SWT.NONE).setText("CVV");
        Text cvv = new Text(right, SWT.BORDER | SWT.PASSWORD);
        cvv.setTextLimit(4);

        new Label(right, SWT.NONE).setText("Preço do serviço:");
        Label precoLabelRight = new Label(right, SWT.NONE);
        precoLabelRight.setText("R$ " + preco.toPlainString());

        Button pagar = new Button(right, SWT.PUSH);
        pagar.setText("PAGAR");
        GridData pd = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        pd.horizontalSpan = 2;
        pagar.setLayoutData(pd);

        pagar.addListener(SWT.Selection, ev -> {
            if (card.getText().replaceAll("\\D", "").length() < 16) {
                MessageBox mb = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
                mb.setText("Validação");
                mb.setMessage("Número do cartão inválido.");
                mb.open();
                return;
            }

            PagamentoController controller = new PagamentoController();
            boolean sucesso = controller.processarPagamento(
                idAgendamento,
                credito.getSelection() ? "CREDITO" : "DEBITO",
                titular.getText(),
                card.getText(),
                venc.getText(),
                cvv.getText()
            );

            if (sucesso) {
                MessageBox msgValidacao = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                msgValidacao.setText("Pagamento");
                msgValidacao.setMessage("Pagamento enviado para validação.");
                msgValidacao.open();

                MessageBox msgPago = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
                msgPago.setText("Pagamento");
                msgPago.setMessage("Pagamento confirmado com sucesso!");
                msgPago.open();
            } else {
                MessageBox msgErro = new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
                msgErro.setText("Pagamento");
                msgErro.setMessage("Erro ao processar pagamento.");
                msgErro.open();
            }

        });
    }
}