import java.util.List;
import java.util.Scanner;

public class Main {
    static Clienterepositorio clienteRepo = new Clienterepositorio();
    static Petrepositorio petRepo = new Petrepositorio();
    static Solicitacaorepositorio solicitacaoRepo = new Solicitacaorepositorio();
    static Scanner input = new Scanner(System.in);

    static Gerente gerente = new Gerente(1, "Admin", "admin@petshop.com", "000.000.000-00", "G001", 5000.00);

    public static void main(String[] args) {
        int opcao = 1;

        while (opcao != 0) {
            System.out.println("\nPETSHOP");
            System.out.println("1 - Área do Cliente");
            System.out.println("2 - Área do Gerente");
            System.out.println("0 - Sair");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(input.nextLine());
                switch (opcao) {
                    case 1: menuCliente(); break;
                    case 2: menuGerente(); break;
                    case 0: System.out.println("Encerrando..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas números!");
            }
        }

        input.close();
    }

    // Helpers

    static Cliente selecionarCliente() {
        List<Cliente> clientes = clienteRepo.listarTodos();
        if (clientes.isEmpty()) { System.out.println("Nenhum cliente cadastrado."); return null; }
        System.out.println("\nClientes");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + " - " + clientes.get(i).getNome()
                    + " (CPF: " + clientes.get(i).getCpf() + ")");
        }
        System.out.print("Escolha: ");
        int escolha = Integer.parseInt(input.nextLine()) - 1;
        if (escolha < 0 || escolha >= clientes.size()) throw new IllegalArgumentException("Opção inválida.");
        return clientes.get(escolha);
    }

    static Pet selecionarPetDisponivel() {
        List<Pet> pets = petRepo.listarDisponiveis();
        if (pets.isEmpty()) { System.out.println("Nenhum pet disponível."); return null; }
        System.out.println("\nPets disponíveis");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + " - " + pets.get(i).getNome()
                    + " | " + pets.get(i).getTipo()
                    + " | " + pets.get(i).getRaca()
                    + " | " + pets.get(i).getIdade() + " anos");
        }
        System.out.print("Escolha: ");
        int escolha = Integer.parseInt(input.nextLine()) - 1;
        if (escolha < 0 || escolha >= pets.size()) throw new IllegalArgumentException("Opção inválida.");
        return pets.get(escolha);
    }

    static Pet selecionarTodosPets() {
        List<Pet> pets = petRepo.listarTodos();
        if (pets.isEmpty()) { System.out.println("Nenhum pet cadastrado."); return null; }
        System.out.println("\nTodos os pets");
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + " - " + pets.get(i).getNome()
                    + " | " + pets.get(i).getTipo()
                    + " | ID: " + pets.get(i).getId());
        }
        System.out.print("Escolha: ");
        int escolha = Integer.parseInt(input.nextLine()) - 1;
        if (escolha < 0 || escolha >= pets.size()) throw new IllegalArgumentException("Opção inválida.");
        return pets.get(escolha);
    }

    // Menu Cliente

    static void menuCliente() {
        int opcao = 1;
        while (opcao != 0) {
            System.out.println("\nÁREA DO CLIENTE");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar pets disponíveis");
            System.out.println("3 - Solicitar adoção");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(input.nextLine());
                switch (opcao) {
                    case 1:
                        System.out.print("Nome: ");
                        String nome = input.nextLine();
                        if (nome.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio.");
                        System.out.print("Email: ");
                        String email = input.nextLine();
                        if (email.isBlank()) throw new IllegalArgumentException("Email não pode ser vazio.");
                        System.out.print("CPF: ");
                        String cpf = input.nextLine();
                        if (cpf.isBlank()) throw new IllegalArgumentException("CPF não pode ser vazio.");
                        clienteRepo.adicionar(new Cliente(0, nome, email, cpf));
                        break;

                    case 2:
                        List<Pet> disponiveis = petRepo.listarDisponiveis();
                        if (disponiveis.isEmpty()) System.out.println("Nenhum pet disponível.");
                        else for (Pet p : disponiveis) p.mostrarInfo();
                        break;

                    case 3:
                        Cliente clienteAdocao = selecionarCliente();
                        if (clienteAdocao == null) break;
                        Pet petAdocao = selecionarPetDisponivel();
                        if (petAdocao == null) break;
                        SolicitacaoAdocao solicitacao = clienteAdocao.solicitarAdotacao(petAdocao);
                        solicitacaoRepo.adicionar(solicitacao);
                        petAdocao.setDisponivel(false);
                        petRepo.editar(petAdocao);
                        break;

                    case 0: System.out.println("Voltando..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número válido!");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    // Menu Gerente

    static void menuGerente() {
        int opcao = 1;
        while (opcao != 0) {
            System.out.println("\nÁREA DO GERENTE");
            System.out.println("1 - Cadastrar pet");
            System.out.println("2 - Editar pet");
            System.out.println("3 - Remover pet");
            System.out.println("4 - Ver solicitações pendentes");
            System.out.println("5 - Listar todos os pets");
            System.out.println("6 - Listar clientes");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            try {
                opcao = Integer.parseInt(input.nextLine());
                switch (opcao) {
                    case 1:
                        System.out.println("Tipo: 1-Cachorro  2-Gato  3-Outro");
                        int tipo = Integer.parseInt(input.nextLine());
                        System.out.print("Nome: ");
                        String nomeP = input.nextLine();
                        if (nomeP.isBlank()) throw new IllegalArgumentException("Nome não pode ser vazio.");
                        System.out.print("Idade: ");
                        int idade = Integer.parseInt(input.nextLine());
                        if (idade < 0) throw new IllegalArgumentException("Idade não pode ser negativa.");
                        System.out.print("Peso (kg): ");
                        double peso = Double.parseDouble(input.nextLine());
                        if (peso <= 0) throw new IllegalArgumentException("Peso deve ser maior que zero.");
                        System.out.print("Raça: ");
                        String raca = input.nextLine();
                        Pet pet;
                        switch (tipo) {
                            case 1: pet = new Cachorro(0, nomeP, idade, peso, raca, null, true); break;
                            case 2: pet = new Gato(0, nomeP, idade, peso, raca, null, true); break;
                            default: pet = new Outropet(0, nomeP, idade, peso, raca, null, true); break;
                        }
                        gerente.cadastrarPet(pet, petRepo);
                        break;

                    case 2:
                        Pet petEditar = selecionarTodosPets();
                        if (petEditar == null) break;
                        System.out.print("Novo nome: ");
                        String novoNome = input.nextLine();
                        System.out.print("Nova idade: ");
                        int novaIdade = Integer.parseInt(input.nextLine());
                        System.out.print("Novo peso: ");
                        double novoPeso = Double.parseDouble(input.nextLine());
                        System.out.print("Nova raça: ");
                        String novaRaca = input.nextLine();
                        System.out.print("Disponível? (true/false): ");
                        boolean novaDisp = Boolean.parseBoolean(input.nextLine());
                        gerente.editarPet(petEditar, novoNome, novaIdade, novoPeso, novaRaca, novaDisp, petRepo);
                        break;

                    case 3:
                        Pet petRemover = selecionarTodosPets();
                        if (petRemover == null) break;
                        gerente.removerPet(petRemover.getId(), petRepo);
                        break;

                    case 4:
                        menuSolicitacoes();
                        break;

                    case 5:
                        List<Pet> todos = petRepo.listarTodos();
                        if (todos.isEmpty()) System.out.println("Nenhum pet cadastrado.");
                        else for (Pet p : todos) p.mostrarInfo();
                        break;

                    case 6:
                        List<Cliente> clientes = clienteRepo.listarTodos();
                        if (clientes.isEmpty()) System.out.println("Nenhum cliente cadastrado.");
                        else for (Cliente c : clientes) c.mostrarInfo();
                        break;

                    case 0: System.out.println("Voltando..."); break;
                    default: System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: digite um número válido!");
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Erro: " + e.getMessage());
            } catch (SecurityException e) {
                System.out.println("Acesso negado: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
    }

    // Menu Solicitações

    static void menuSolicitacoes() {
        List<SolicitacaoAdocao> pendentes = solicitacaoRepo.listarPendentes(clienteRepo, petRepo);
        if (pendentes.isEmpty()) {
            System.out.println("Nenhuma solicitação pendente.");
            return;
        }

        System.out.println("\nSolicitações pendentes");
        for (int i = 0; i < pendentes.size(); i++) {
            SolicitacaoAdocao s = pendentes.get(i);
            System.out.println((i + 1) + " - Cliente: " + s.getCliente().getNome()
                    + " | Pet: " + s.getPet().getNome()
                    + " | Data: " + s.getDataSolicitacao());
        }

        System.out.print("Escolha o número da solicitação (0 para voltar): ");
        try {
            int escolha = Integer.parseInt(input.nextLine()) - 1;
            if (escolha == -1) return;
            if (escolha < 0 || escolha >= pendentes.size()) {
                System.out.println("Opção inválida.");
                return;
            }

            SolicitacaoAdocao solicitacao = pendentes.get(escolha);
            solicitacao.mostrarInfo();

            System.out.println("\n1 - Aprovar");
            System.out.println("2 - Reprovar");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");

            int acao = Integer.parseInt(input.nextLine());
            switch (acao) {
                case 1:
                    boolean aprovado = gerente.aprovarAdocao(solicitacao);
                    if (aprovado) {
                        solicitacaoRepo.atualizarStatus(solicitacao.getId(), StatusSolicitacao.APROVADA);
                    }
                    break;

                case 2:
                    boolean reprovado = gerente.reprovarAdocao(solicitacao);
                    if (reprovado) {
                        solicitacaoRepo.atualizarStatus(solicitacao.getId(), StatusSolicitacao.REPROVADA);
                        solicitacao.getPet().setDisponivel(true);
                        petRepo.editar(solicitacao.getPet());
                    }
                    break;

                case 0:
                    System.out.println("Voltando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro: digite um número válido!");
        } catch (RuntimeException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}