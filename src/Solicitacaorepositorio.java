import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Solicitacaorepositorio {

    // ─── Adicionar ─────────────────────────────────────────────────────────────

    public void adicionar(SolicitacaoAdocao solicitacao) {
        String sql = "INSERT INTO solicitacao_adocao (id_cliente, id_pet, data_solicitacao, status) VALUES (?, ?, ?, ?)";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, solicitacao.getCliente().getId());
            stmt.setInt(2, solicitacao.getPet().getId());
            stmt.setDate(3, Date.valueOf(solicitacao.getDataSolicitacao()));
            stmt.setString(4, solicitacao.getStatus().name());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                System.out.println("Solicitação criada com id " + keys.getInt(1) + ".");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar solicitação: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // ─── Atualizar status ──────────────────────────────────────────────────────

    public void atualizarStatus(int id, StatusSolicitacao status) {
        String sql = "UPDATE solicitacao_adocao SET status = ? WHERE id = ?";
        Connection conn = ConexaoBanco.conectar();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, status.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Status da solicitação " + id + " atualizado para " + status + ".");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar status: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
    }

    // ─── Listar pendentes ──────────────────────────────────────────────────────

    public List<SolicitacaoAdocao> listarPendentes(Clienterepositorio clienteRepo, Petrepositorio petRepo) {
        String sql = "SELECT * FROM solicitacao_adocao WHERE status = 'PENDENTE'";
        Connection conn = ConexaoBanco.conectar();
        List<SolicitacaoAdocao> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id          = rs.getInt("id");
                int idCliente   = rs.getInt("id_cliente");
                int idPet       = rs.getInt("id_pet");

                Cliente cliente = clienteRepo.buscarPorId(idCliente);
                Pet pet         = petRepo.buscarPorId(idPet);

                SolicitacaoAdocao s = new SolicitacaoAdocao(id, cliente, pet);
                lista.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return lista;
    }

    // ─── Listar todas ──────────────────────────────────────────────────────────

    public List<SolicitacaoAdocao> listarTodas(Clienterepositorio clienteRepo, Petrepositorio petRepo) {
        String sql = "SELECT * FROM solicitacao_adocao";
        Connection conn = ConexaoBanco.conectar();
        List<SolicitacaoAdocao> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id          = rs.getInt("id");
                int idCliente   = rs.getInt("id_cliente");
                int idPet       = rs.getInt("id_pet");
                String status   = rs.getString("status");

                Cliente cliente = clienteRepo.buscarPorId(idCliente);
                Pet pet         = petRepo.buscarPorId(idPet);

                SolicitacaoAdocao s = new SolicitacaoAdocao(id, cliente, pet);
                s.setStatus(StatusSolicitacao.valueOf(status));
                lista.add(s);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar solicitações: " + e.getMessage());
        } finally {
            ConexaoBanco.fechar(conn);
        }
        return lista;
    }
}