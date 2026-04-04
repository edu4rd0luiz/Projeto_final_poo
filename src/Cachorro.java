public class Cachorro extends Pet {

    public Cachorro(int id, String nome, int idade, double peso, String raca, Cliente doador, boolean disponivel) {
        super(id, nome, idade, peso, raca, doador, disponivel);
    }

    @Override
    public String getTipo() {
        return "cachorro";
    }

    public void mostrarInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Idade: " + this.idade + " anos");
        System.out.println("Peso: " + this.peso + " kg");
        System.out.println("Tipo: " + getTipo());
        System.out.println("Cadastrado em: " + this.dataDeCadastro);
        System.out.println("Status: " + this.disponivel);
    }
}