import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

        // testando conexão
        Connection conn = ConexaoBanco.conectar();
        ConexaoBanco.fechar(conn);

        // testando cadastro de cliente
        Clienterepositorio clienteRepo = new Clienterepositorio();

        Cliente cliente = new Cliente(0, "João Silva", "joao@email.com", "143.456.789-00");
        clienteRepo.adicionar(cliente);

        // listando clientes
        System.out.println("\n--- Clientes cadastrados ---");
        for (Cliente c : clienteRepo.listarTodos()) {
            System.out.println(c.getNome());


            Petrepositorio petRepo = new Petrepositorio();

            Cachorro cachorro = new Cachorro(0, "Rex", 3, 15.5, "Labrador", null);
            petRepo.adicionar(cachorro);

            System.out.println("\n--- Pets cadastrados ---");
            for (Pet p : petRepo.listarTodos()) {
                System.out.println(p.getNome() + " - " + p.getTipo());
            }
        }
    }
}