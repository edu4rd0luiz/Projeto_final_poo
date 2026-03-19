import java.time.LocalDate;

public abstract class Pet {

    protected int id;
    protected String nome;
    protected int idade;
    protected double peso;
    protected String raca;
    protected LocalDate dataDeCadastro;
    protected Cliente doador;

    public Pet(int id, String nome, int idade, double peso, String raca, Cliente doador) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.raca = raca;
        this.dataDeCadastro = LocalDate.now();
        this.doador = doador;
    }


    public abstract String getTipo();

    public void mostrarInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade + " anos");
        System.out.println("Peso: " + this.peso + " kg");
        System.out.println("Tipo: " + getTipo()); // chama o método da subclasse
        System.out.println("Cadastrado em: " + this.dataDeCadastro);
    }
}