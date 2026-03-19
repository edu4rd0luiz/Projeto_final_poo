public class Gato extends Pet {


        public Gato(int id, String nome, int idade, double peso, String raca, Cliente doador) {
            super(id, nome, idade, peso, raca, doador);
        }

        @Override
        public String getTipo() {
            return "Gato";
        }

        public void mostrarInfo() {
            System.out.println("ID: " + this.id);
            System.out.println("Nome: " + this.nome);
            System.out.println("Idade: " + this.idade + " anos");
            System.out.println("Peso: " + this.peso + " kg");
            System.out.println("Tipo: " + getTipo());
            System.out.println("Cadastrado em: " + this.dataDeCadastro);
        }
    }

