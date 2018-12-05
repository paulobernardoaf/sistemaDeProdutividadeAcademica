import java.util.ArrayList;
import java.util.Scanner;

public class Publicacao {

    private String tituloDaPublicacao;
    private String nomeDaConferencia;
    private int anoDePublicacao;
    private ArrayList<Colaborador> autores = new ArrayList<Colaborador>();

    public void adicionarAutores() {

        while (true) {

            Scanner scanner = new Scanner(System.in);

            System.out.println("Escolha:\n" +
                    "(1) - Adicionar autor\n" +
                    "(2) - Sair");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            if(escolha == 1) {

                System.out.println("Digite o nome do autor: ");
                String nome = scanner.nextLine();
                Colaborador autor = Laboratorio.checarExistente(nome);
                while (autor == null) {
                    System.out.println("Autor não encontrado no laboratório, favor digite outro.");
                    nome = scanner.nextLine();
                    autor = Laboratorio.checarExistente(nome);
                }

                this.getAutores().add(autor);
                autor.getProducoesDoColaborador().getPublicacoes().add(this);


            } else {
                if(this.getAutores().size() < 1) {
                    System.out.println("Precisa teer pelo menos um autor");
                } else{
                    break;
                }
            }

        }

    }

    public String getTituloDaPublicacao() {
        return tituloDaPublicacao;
    }

    public void setTituloDaPublicacao(String tituloDaPublicacao) {
        this.tituloDaPublicacao = tituloDaPublicacao;
    }

    public void setNomeDaConferencia(String nomeDaConferencia) {
        this.nomeDaConferencia = nomeDaConferencia;
    }

    public int getAnoDePublicacao() {
        return anoDePublicacao;
    }

    public void setAnoDePublicacao(int anoDePublicacao) {
        this.anoDePublicacao = anoDePublicacao;
    }

    public ArrayList<Colaborador> getAutores() {
        return autores;
    }

}
