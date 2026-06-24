package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import controller.CancelamentoController;
import model.Cliente;

public class LoginView extends Composite {

    private Text txtUsuario;
    private Text txtSenha;

    public LoginView(Composite parent, int style) {
        super(parent, style);

        setLayout(new GridLayout(1, false));
        new Label(this, SWT.NONE);

        Label lblTitulo = new Label(this, SWT.CENTER);
        lblTitulo.setText("SALÃO DE BELEZA");
        GridData gdTitulo = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gdTitulo.verticalIndent = 24;
        lblTitulo.setLayoutData(gdTitulo);

        Composite form = new Composite(this, SWT.NONE);
        form.setLayout(new GridLayout(2, false));
        GridData gdForm = new GridData(SWT.CENTER, SWT.CENTER, true, true);
        gdForm.widthHint = 380;
        form.setLayoutData(gdForm);

        Label lblUsuario = new Label(form, SWT.NONE);
        lblUsuario.setText("Usuário:");

        txtUsuario = new Text(form, SWT.BORDER);
        GridData gdUsuario = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gdUsuario.widthHint = 240;
        txtUsuario.setLayoutData(gdUsuario);

        Label lblSenha = new Label(form, SWT.NONE);
        lblSenha.setText("Senha:");

        txtSenha = new Text(form, SWT.BORDER | SWT.PASSWORD);
        GridData gdSenha = new GridData(SWT.FILL, SWT.CENTER, true, false);
        gdSenha.widthHint = 240;
        txtSenha.setLayoutData(gdSenha);

        Composite actions = new Composite(this, SWT.NONE);
        actions.setLayout(new GridLayout(1, true));
        GridData gdActions = new GridData(SWT.CENTER, SWT.CENTER, true, false);
        gdActions.verticalIndent = 12;
        actions.setLayoutData(gdActions);

        Button btnEntrar = new Button(actions, SWT.PUSH);
        btnEntrar.setText("Entrar");

        btnEntrar.addListener(SWT.Selection, e -> {
            String usuario = txtUsuario.getText().trim();
            String senha   = txtSenha.getText().trim();

            CancelamentoController controller = new CancelamentoController();
            Cliente cliente = controller.logarCliente(usuario, senha);

            if (cliente != null) {
                Display display = getDisplay();
                getShell().dispose();

                new ListaAgendamentosView(display, cliente.getNome(), cliente.getId());
            } else {
                MessageBox msg = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
                msg.setText("Erro de Login");
                msg.setMessage("Usuário ou senha inválidos.");
                msg.open();
            }
        });
    }

    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Login - Salão de Beleza");
        shell.setSize(500, 360);
        shell.setLayout(new FillLayout());

        new LoginView(shell, SWT.NONE);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }

        display.dispose();
    }
}