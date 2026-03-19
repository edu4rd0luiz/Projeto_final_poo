import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = ConexaoBanco.conectar();
        ConexaoBanco.fechar(conn);
    }
}