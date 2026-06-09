import java.time.LocalDate;

public class SolicitacaoAdocao {

    private int id;
    private final Cliente cliente;
    private final Pet pet;
    private final LocalDate dataSolicitacao;
    private StatusSolicitacao status;

    public SolicitacaoAdocao(int id, Cliente cliente, Pet pet) {
        this.id = id;
        this.cliente = cliente;
        this.pet = pet;
        this.dataSolicitacao = LocalDate.now();
        this.status = StatusSolicitacao.PENDENTE; // começa sempre como pendente
    }

    public void mostrarInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Cliente: " + this.cliente.getNome());
        System.out.println("Pet: " + this.pet.getNome());
        System.out.println("Data: " + this.dataSolicitacao);
        System.out.println("Status: " + this.status);
    }

    // Getters e Setters

    public int getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public Pet getPet() { return pet; }

    public LocalDate getDataSolicitacao() { return dataSolicitacao; }

    public StatusSolicitacao getStatus() { return status; }
    public void setStatus(StatusSolicitacao status) { this.status = status; }
}