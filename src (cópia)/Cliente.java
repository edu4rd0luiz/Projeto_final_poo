import java.util.ArrayList;
import java.util.List;

public class Cliente extends Pessoa {

    private final List<SolicitacaoAdocao> solicitacoes = new ArrayList<>();

    public Cliente(int id, String nome, String email, String cpf) {
        super(id, nome, email, cpf);
    }
    @Override
    public String getTipo() {
        return "Cliente";
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
    }

    // Doa um pet para o abrigo
    public void doarPet(Pet pet, Petrepositorio petRepo) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet não pode ser nulo.");
        }
        pet.setDisponivel(true);
        petRepo.adicionar(pet);
        System.out.println(this.getNome() + " doou o pet '" + pet.getNome() + "' com sucesso.");
    }

    // Solicita adoção de um pet disponível

    public SolicitacaoAdocao solicitarAdotacao(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("Pet não pode ser nulo.");
        }
        if (!pet.isDisponivel()) {
            throw new IllegalStateException("O pet '" + pet.getNome() + "' não está disponível.");
        }
        SolicitacaoAdocao solicitacao = new SolicitacaoAdocao(0, this, pet);
        solicitacoes.add(solicitacao);
        pet.setDisponivel(false);
        System.out.println(this.getNome() + " solicitou adoção do pet '" + pet.getNome() + "'.");
        return solicitacao;
    }

    // Cancela uma solicitação pendente

    public void cancelarSolicitacao(SolicitacaoAdocao solicitacao) {
        if (solicitacao == null) {
            throw new IllegalArgumentException("Solicitação não pode ser nula.");
        }
        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            throw new IllegalStateException("Só é possível cancelar solicitações pendentes.");
        }
        solicitacao.setStatus(StatusSolicitacao.REPROVADA);
        solicitacao.getPet().setDisponivel(true); // pet volta a ficar disponível
        solicitacoes.remove(solicitacao);
        System.out.println("Solicitação cancelada. Pet '" + solicitacao.getPet().getNome() + "' disponível novamente.");
    }

    // Lista todas as solicitações do cliente

    public void listarSolicitacoes() {
        if (solicitacoes.isEmpty()) {
            System.out.println(this.getNome() + " não tem solicitações.");
            return;
        }
        System.out.println("Solicitações de " + this.getNome());
        for (SolicitacaoAdocao s : solicitacoes) {
            s.mostrarInfo();
        }
    }

    public List<SolicitacaoAdocao> getSolicitacoes() { return solicitacoes; }
}