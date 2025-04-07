import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EditorTexto extends JFrame {
    private JTextArea areaTexto;
    private JButton btnSalvar, btnLimpar, btnSair;

    private Connection conexao;

    public EditorTexto() {
        super("Editor de Texto");

        // Área de texto
        areaTexto = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(areaTexto);

        // Botões
        btnSalvar = new JButton("Salvar");
        btnLimpar = new JButton("Limpar");
        btnSair = new JButton("Sair");

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSair);

        // Layout principal
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ações dos botões
        btnSalvar.addActionListener(e -> salvarTexto());
        btnLimpar.addActionListener(e -> areaTexto.setText(""));
        btnSair.addActionListener(e -> System.exit(0));

        // Configurações da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        conectarBanco();
        criarTabela();
    }

    private void conectarBanco() {
        try {
            // Caminho relativo: você pode mudar para um caminho absoluto se quiser
            String url = "jdbc:sqlite:editor.db";
            conexao = DriverManager.getConnection(url);
            System.out.println("Conexão com SQLite estabelecida.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados.");
        }
    }

    private void criarTabela() {
        try (Statement stmt = conexao.createStatement()) {
            String sql = "CREATE TABLE IF NOT EXISTS textos (id INTEGER PRIMARY KEY AUTOINCREMENT, conteudo TEXT NOT NULL)";
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void salvarTexto() {
        String texto = areaTexto.getText();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite algo para salvar!");
            return;
        }

        try (PreparedStatement stmt = conexao.prepareStatement("INSERT INTO textos (conteudo) VALUES (?)")) {
            stmt.setString(1, texto);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Texto salvo com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar texto.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditorTexto::new);
    }
}
