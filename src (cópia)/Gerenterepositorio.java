import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Gerenterepositorio {

    // Adicionar

    public void adicionar(Gerente gerente) {
        Connection conn = ConexaoBanco.conectar();
        try {
            // 1. insere na tabela pessoa primeiro
            String sqlPessoa = "INSERT INTO pessoa (nome, email, cpf, data_de_cadastro, tipo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            stmtPessoa.setString(1, gerente.getNome());
            stmtPessoa.setString(2, gerente.getEmail());
            stmtPessoa.setString(3, gerente.getCpf());
            stmtPessoa.setDate(4, Date.valueOf(gerente.getDataDeCadastro()));
            stmtPessoa.setString(5, gerente.getTipo());
            stmtPessoa.executeUpdate();

            // 2. pega o id gerado automaticamente
            ResultSet keys = stmtPessoa.getGeneratedKeys();
            if (keys.next()) {
                int idGerado = keys.getInt(1);

                // 3. insere na tabela gerente com o mesmo id
                String sqlGerente = "INSERT INTO gerente (id, matricula, salario) VALUES (?, ?, ?)";
                PreparedStatement stmtGerente = conn.prepareStatement(sqlGerente);
                stmtGerente.setInt(1, idGerado);
                stmtGerente.setString(2, gerente.getMatricula());
                stmtGerente.setDouble(3, gerente.getSalario());
                stmtGerente.executeUpdate();

                System.out.println("Gerente '" + gerente.getNome() + "' salvo no banco com id " + idGerado + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar gerente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Remover

    public void remover(int id) {
        Connection conn = ConexaoBanco.conectar();
        try {
            String sqlGerente = "DELETE FROM gerente WHERE id = ?";
            PreparedStatement stmtGerente = conn.prepareStatement(sqlGerente);
            stmtGerente.setInt(1, id);
            stmtGerente.executeUpdate();

            String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";
            PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            System.out.println("Gerente com id " + id + " removido.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover gerente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Buscar por ID

    public Gerente buscarPorId(int id) {
        String sql = "SELECT p.*, g.matricula, g.salario FROM pessoa p INNER JOIN gerente g ON p.id = g.id WHERE p.id = ?";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirGerente(rs);
            }
            throw new IllegalArgumentException("Gerente com id " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar gerente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Listar todos

    public List<Gerente> listarTodos() {
        String sql = "SELECT p.*, g.matricula, g.salario FROM pessoa p INNER JOIN gerente g ON p.id = g.id";
        Connection conn = ConexaoBanco.conectar();
        List<Gerente> gerentes = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                gerentes.add(construirGerente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar gerentes: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return gerentes;
    }

    // Monta o objeto Gerente a partir do ResultSet

    private Gerente construirGerente(ResultSet rs) throws SQLException {
        return new Gerente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cpf"),
                rs.getString("matricula"),
                rs.getDouble("salario")
        );
    }
}