import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa {
//classe abstrata, classe pai de gerente e de cliente

    protected int id;
    protected String nome;
    protected String email;
    protected String cpf;
    protected LocalDate dataDeCadastro;
    //atributos gerais

    public Pessoa(int id, String nome, String email, String cpf) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.dataDeCadastro = LocalDate.now();
    }

    public abstract String getTipo();


    public void mostrarInfo() {
        System.out.println("ID: " + this.id);
        System.out.println("Nome: " + this.nome);
        System.out.println("Email: " + this.email);
        System.out.println("CPF: " + this.cpf);
        System.out.println("Tipo: " + this.getTipo());
        System.out.println("Cadastro: " + this.dataDeCadastro);
    }
}