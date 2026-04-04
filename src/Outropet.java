import java.util.Scanner;

public class Outropet extends Pet{
    Scanner sc = new Scanner(System.in);

    public Outropet(int id, String nome, int idade, double peso, String raca, Cliente doador, boolean disponivel) {
            super(id, nome, idade, peso, raca, doador, disponivel);
        }

    @Override
    public String getTipo() {
        return sc.nextLine();
    }


        public void mostrarInfo() {
            System.out.println("ID: " + this.id);
            System.out.println("Nome: " + this.nome);
            System.out.println("Idade: " + this.idade + " anos");
            System.out.println("Peso: " + this.peso + " kg");
            System.out.println("Tipo: " + getTipo());
            System.out.println("Cadastrado em: " + this.dataDeCadastro);
            System.out.println("Raca: " + this.raca);
            System.out.println("Doador: " + this.doador);
            System.out.println("Disponivel: " + this.disponivel);
        }
    }

