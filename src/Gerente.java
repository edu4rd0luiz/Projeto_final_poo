import java.util.ArrayList;
import java.util.List;

public class Gerente extends Pessoa {

    private final String matricula;
    private final double salario;
    private final List<String> permissoes;

    public Gerente(int id, String nome, String email, String cpf,
                   String matricula, double salario) {
        super(id, nome, email, cpf);
        this.matricula = matricula;
        this.salario = salario;
        this.permissoes = new ArrayList<>();
        this.permissoes.add("CADASTRAR_PET");
        this.permissoes.add("EDITAR_PET");
        this.permissoes.add("REMOVER_PET");
        this.permissoes.add("APROVAR_ADOCAO");
        this.permissoes.add("REPROVAR_ADOCAO");
    }

    @Override
    public String getTipo() {
        return "Gerente";
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Matrícula: " + this.matricula);
        System.out.printf("Salário: R$ %.2f%n", this.salario);
        System.out.println("Permissões: " + this.permissoes);
    }

    // ─── Cadastra um pet no repositório ───────────────────────────────────────

    public void cadastrarPet(Pet pet, Petrepositorio petRepo) {
        if (!temPermissao("CADASTRAR_PET")) {
            throw new SecurityException("Gerente sem permissão para cadastrar pets.");
        }
        petRepo.adicionar(pet);
        System.out.println(this.getNome() + " cadastrou o pet '" + pet.getNome() + "'.");
    }

    // ─── Edita um pet no repositório ──────────────────────────────────────────

    public void editarPet(Pet pet, String novoNome, int novaIdade, double novoPeso,
                          String novaRaca, boolean novaDisponibilidade, Petrepositorio petRepo) {
        if (!temPermissao("EDITAR_PET")) {
            throw new SecurityException("Gerente sem permissão para editar pets.");
        }
        pet.setNome(novoNome);
        pet.setIdade(novaIdade);
        pet.setPeso(novoPeso);
        pet.setRaca(novaRaca);
        pet.setDisponivel(novaDisponibilidade);
        petRepo.editar(pet);
        System.out.println(this.getNome() + " editou o pet '" + pet.getNome() + "'.");
    }

    // ─── Remove um pet do repositório ─────────────────────────────────────────

    public void removerPet(int idPet, Petrepositorio petRepo) {
        if (!temPermissao("REMOVER_PET")) {
            throw new SecurityException("Gerente sem permissão para remover pets.");
        }
        petRepo.remover(idPet);
        System.out.println(this.getNome() + " removeu o pet de id " + idPet + ".");
    }

    // ─── Aprova uma solicitação de adoção ─────────────────────────────────────

    public boolean aprovarAdocao(SolicitacaoAdocao solicitacao) {
        if (!temPermissao("APROVAR_ADOCAO")) {
            throw new SecurityException("Gerente sem permissão para aprovar adoções.");
        }
        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            System.out.println("Solicitação não está pendente.");
            return false;
        }
        solicitacao.setStatus(StatusSolicitacao.APROVADA);
        System.out.println(this.getNome() + " aprovou a adoção do pet '"
                + solicitacao.getPet().getNome() + "' para " + solicitacao.getCliente().getNome() + ".");
        return true;
    }

    // ─── Reprova uma solicitação de adoção ────────────────────────────────────

    public boolean reprovarAdocao(SolicitacaoAdocao solicitacao) {
        if (!temPermissao("REPROVAR_ADOCAO")) {
            throw new SecurityException("Gerente sem permissão para reprovar adoções.");
        }
        if (solicitacao.getStatus() != StatusSolicitacao.PENDENTE) {
            System.out.println("Solicitação não está pendente.");
            return false;
        }
        solicitacao.setStatus(StatusSolicitacao.REPROVADA);
        solicitacao.getPet().setDisponivel(true);
        System.out.println(this.getNome() + " reprovou a adoção do pet '"
                + solicitacao.getPet().getNome() + "'.");
        return true;
    }

    // ─── Verifica se o gerente tem uma permissão ──────────────────────────────

    public boolean temPermissao(String permissao) {
        return this.permissoes.contains(permissao);
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getMatricula() { return matricula; }
    public double getSalario() { return salario; }
    public List<String> getPermissoes() { return permissoes; }
}