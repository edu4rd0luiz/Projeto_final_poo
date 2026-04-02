import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Petrepositorio {

    // ─── Adicionar ─────────────────────────────────────────────────────────────

    public void adicionar(Pet pet) {
        String sql = "INSERT INTO pet (nome, idade, peso, raca, tipo, disponivel, data_de_cadastro, id_doador) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, pet.getNome());
            stmt.setInt(2, pet.getIdade());
            stmt.setDouble(3, pet.getPeso());
            stmt.setString(4, pet.getRaca());
            stmt.setString(5, pet.getTipo());
            stmt.setBoolean(6, pet.isDisponivel());
            stmt.setDate(7, Date.valueOf(pet.getDataDeCadastro()));
            if (pet.getDoador() != null) {
                stmt.setInt(8, pet.getDoador().getId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }
            stmt.executeUpdate();
            System.out.println("Pet '" + pet.getNome() + "' salvo no banco.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar pet: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // ─── Remover ───────────────────────────────────────────────────────────────

    public void remover(int id) {
        String sql = "DELETE FROM pet WHERE id = ?";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Pet com id " + id + " removido.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover pet: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // ─── Buscar por ID ─────────────────────────────────────────────────────────

    public Pet buscarPorId(int id) {
        String sql = "SELECT * FROM pet WHERE id = ?";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return construirPet(rs);
            }
            throw new IllegalArgumentException("Pet com id " + id + " não encontrado.");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pet: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // ─── Listar todos ──────────────────────────────────────────────────────────

    public List<Pet> listarTodos() {
        String sql = "SELECT * FROM pet";
        Connection conn = ConexaoBanco.conectar();
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pets.add(construirPet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pets: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return pets;
    }

    // ─── Listar disponíveis ────────────────────────────────────────────────────

    public List<Pet> listarDisponiveis() {
        String sql = "SELECT * FROM pet WHERE disponivel = true";
        Connection conn = ConexaoBanco.conectar();
        List<Pet> pets = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pets.add(construirPet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pets disponíveis: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return pets;
    }

    // ─── Monta o objeto Pet a partir do ResultSet ──────────────────────────────

    private Pet construirPet(ResultSet rs) throws SQLException {
        int id       = rs.getInt("id");
        String nome  = rs.getString("nome");
        int idade    = rs.getInt("idade");
        double peso  = rs.getDouble("peso");
        String raca  = rs.getString("raca");
        String tipo  = rs.getString("tipo");

        switch (tipo.toLowerCase()) {
            case "cachorro": return new Cachorro(id, nome, idade, peso, raca, null);
            case "gato":     return new Gato(id, nome, idade, peso, raca, null);
            default:         return new Outropet(id, nome, idade, peso, raca, null);
        }
    }
}