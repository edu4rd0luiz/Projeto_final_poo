import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gerente extends Pessoa {

    private String matricula;
    private double salario;
    private List<String> permissoes;



    public Gerente(int id, String nome, String email, String cpf,
                   String matricula, double salario) {
        super(id, nome, email, cpf); // chama o construtor de Pessoa
        this.matricula = matricula;
        this.salario = salario;
        this.permissoes = new ArrayList<>();
        this.permissoes.add("CADASTRAR_PET");
        this.permissoes.add("EDITAR_PET");
        this.permissoes.add("REMOVER_PET");
        this.permissoes.add("APROVAR_ADOCAO");
        this.permissoes.add("REPROVAR_ADOCAO");
    }

    // Implementação obrigatória do metodo abstrato

    @Override
    public String getTipo() {
        return "Gerente";
    }

    // Sobrescrita de mostrarInfo com dados específicos do gerente

    @Override
    public void mostrarInfo() {
        super.mostrarInfo(); // reutiliza as infos da classe pai
        System.out.println("Matrícula: " + this.matricula);
        System.out.printf("Salário: R$ %.2f%n", this.salario);
        System.out.println("Permissões: " + this.permissoes);
    }

    //Comportamentos específicos do Gerente

   public void CadastrarPet(){
       this.permissoes.add("CADASTRAR_PET");
   }
   public void EditarPet(){
       this.permissoes.add("EDITAR_PET");
   }
   public void RemoverPet(){
       this.permissoes.add("REMOVER_PET");
   }
   public boolean aprovarPet(){
        this.permissoes.add("APROVAR_ADOCAO");
       return false;
   }
   public boolean reprovarPet(){ this.permissoes.add("REPROVAR_ADOCAO");
       return false;
   }

    public String getMatricula() {
        return matricula;
    }
    public double getSalario() {
        return salario;
    }
}