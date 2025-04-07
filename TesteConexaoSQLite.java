import java.sql.*;

public class TesteConexaoSQLite {
    public static void main(String[] args) {
        try {
            // Carrega a classe do driver SQLite
            Class.forName("org.sqlite.JDBC");

            // Conecta ao banco
            Connection conn = DriverManager.getConnection("jdbc:sqlite:teste.db");
            System.out.println("Conectado com sucesso!");

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
