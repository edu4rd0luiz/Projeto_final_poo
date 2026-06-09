import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Clienterepositorio {

    // Adicionar

    public void adicionar(Cliente cliente) {
        Connection conn = ConexaoBanco.conectar();
        try {
            // 1. insere na tabela pessoa primeiro
            String sqlPessoa = "INSERT INTO pessoa (nome, email, cpf, data_de_cadastro, tipo) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa, Statement.RETURN_GENERATED_KEYS);
            stmtPessoa.setString(1, cliente.getNome());
            stmtPessoa.setString(2, cliente.getEmail());
            stmtPessoa.setString(3, cliente.getCpf());
            stmtPessoa.setDate(4, Date.valueOf(cliente.getDataDeCadastro()));
            stmtPessoa.setString(5, cliente.getTipo());
            stmtPessoa.executeUpdate();

            // 2. pega o id gerado automaticamente
            ResultSet keys = stmtPessoa.getGeneratedKeys();
            if (keys.next()) {
                int idGerado = keys.getInt(1);

                // 3. insere na tabela cliente com o mesmo id
                String sqlCliente = "INSERT INTO cliente (id) VALUES (?)";
                PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente);
                stmtCliente.setInt(1, idGerado);
                stmtCliente.executeUpdate();

                System.out.println("Cliente '" + cliente.getNome() + "' salvo no banco com id " + idGerado + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Remover

    public void remover(int id) {
        Connection conn = ConexaoBanco.conectar();
        try {
            // remove da tabela filha primeiro por causa da foreign key
            String sqlCliente = "DELETE FROM cliente WHERE id = ?";
            PreparedStatement stmtCliente = conn.prepareStatement(sqlCliente);
            stmtCliente.setInt(1, id);
            stmtCliente.executeUpdate();

            String sqlPessoa = "DELETE FROM pessoa WHERE id = ?";
            PreparedStatement stmtPessoa = conn.prepareStatement(sqlPessoa);
            stmtPessoa.setInt(1, id);
            stmtPessoa.executeUpdate();

            System.out.println("Cliente com id " + id + " removido.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover cliente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Buscar por ID

    public Cliente buscarPorId(int id) {
        String sql = "SELECT p.* FROM pessoa p INNER JOIN cliente c ON p.id = c.id WHERE p.id = ?";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirCliente(rs);
            }
            throw new IllegalArgumentException("Cliente com id " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // Listar todos

    public List<Cliente> listarTodos() {
        String sql = "SELECT p.* FROM pessoa p INNER JOIN cliente c ON p.id = c.id";
        Connection conn = ConexaoBanco.conectar();
        List<Cliente> clientes = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clientes.add(construirCliente(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return clientes;
    }

    // Monta o objeto Cliente a partir do ResultSet

    private Cliente construirCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("cpf")
        );
    }
}