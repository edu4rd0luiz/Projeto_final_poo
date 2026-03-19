

public class Cliente extends Pessoa {

    public Cliente(int id, String nome, String email, String cpf) {
        super(id, nome, email, cpf);
    }

    @Override
    public String getTipo() {
        return "Cliente";
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
    }
    public void doarPet(){}
    public void solicitarAdotacao(){}
    public void cancelarSolicitacao(){}

}