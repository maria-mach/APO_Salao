package banco;

import java.sql.Connection;

public class TesteConexao {
    public static void main(String[] args) {
        try {
            DBConnection conexao = new DBConnection();
            Connection conn = conexao.getConnection(); // usa a variável

            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão ok");
            } else {
                System.out.println("Conexão falhou");
            }
        } catch (Exception e) {
            System.out.println("Conexão nok");
            e.printStackTrace();
        }
    }
}
