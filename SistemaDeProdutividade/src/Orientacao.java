import java.util.ArrayList;
import java.util.Scanner;

public class Orientacao {

    private String tituloDaOrientacao;
    private Colaborador orientando = null;
    private int data;
    private ArrayList<Colaborador> orientados = new ArrayList<Colaborador>();

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getTituloDaOrientacao() {
        return tituloDaOrientacao;
    }

    public ArrayList<Colaborador> getOrientados() {
        return orientados;
    }

    public void setTituloDaOrientacao(String tituloDaOrientacao) {
        this.tituloDaOrientacao = tituloDaOrientacao;
    }

    public void setOrientando(Colaborador orientando) {
        this.orientando = orientando;
    }

    public void adicionarOrientados() {

        while (true) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolha:\n" +
                    "(1) - Adicionar orientado\n" +
                    "(outro) - Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            if(escolha == 1) {

                System.out.println("Digite o nome do orientado: ");
                String nome = scanner.nextLine();
                Colaborador orientado = Laboratorio.checarExistente(nome);
                while (orientado == null) {
                    System.out.println("Orientado não encontrado no laboratório, favor digite outro.");
                    nome = scanner.nextLine();
                    orientado = Laboratorio.checarExistente(nome);
                }

                if(orientado.getTipo() == 4) {
                    System.out.println("Um professor não pode ser orientado.");
                } else {

                    this.getOrientados().add(orientado);
                    orientado.getProducoesDoColaborador().getOrientacoes().add(this);

                }

            } else {
                break;
            }

        }

    }

}
