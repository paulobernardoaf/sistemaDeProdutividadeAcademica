import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Colaborador {

    private String nome;
    private String email;
    private int tipo;
    ArrayList<Projeto> todosProjetos = new ArrayList<Projeto>();
    ArrayList<Projeto> projetosPertencentesAtualmente = new ArrayList<Projeto>();
    ProducaoAcademica producoesDoColaborador = new ProducaoAcademica();

    public Colaborador(String nome, String email, int tipo) {

        this.nome = nome;
        this.email = email;
        this.tipo = tipo;

    }

    public void ordenarProducoes() {

        Collections.sort(this.getProducoesDoColaborador().getOrientacoes(), Collections.reverseOrder(new Comparator<Orientacao>() {
            public int compare(Orientacao o1, Orientacao o2) {
                return Integer.compare(o1.getData(), o2.getData());
            }
        }));

        Collections.sort(this.getProducoesDoColaborador().getPublicacoes(), Collections.reverseOrder(new Comparator<Publicacao>() {
            public int compare(Publicacao o1, Publicacao o2) {
                return Integer.compare(o1.getAnoDePublicacao(), o2.getAnoDePublicacao());
            }
        }));

    }

    public void detalhar() {

        System.out.println("Nome: " + this.getNome() + "\nE-Mail: " + this.getEmail());

        Collections.sort(this.getTodosProjetos(), Collections.reverseOrder(new Comparator<Projeto>() {
            public int compare(Projeto o1, Projeto o2) {
                return o1.getDataDeTermino().compareTo(o2.getDataDeTermino());
            }
        }));

        this.ordenarProducoes();

        System.out.println("Projetos: ");
        for (Projeto projeto : this.getTodosProjetos()) {
            System.out.println("\t" + projeto.getTitulo() + " - " + projeto.getDataDeTermino());
        }

        System.out.println("Produção acadêmica: ");
        System.out.println("\tOrientações que participou: (orientador/orientado)");
        for(Orientacao orientacao : this.getProducoesDoColaborador().getOrientacoes()) {
            System.out.println("\t\t" + orientacao.getTituloDaOrientacao() + " - " + orientacao.getData());
        }
        System.out.println("\tPublicacões: ");
        for (Publicacao publicacao : this.getProducoesDoColaborador().getPublicacoes()) {
            System.out.println("\t\t" + publicacao.getTituloDaPublicacao() + " - " + publicacao.getAnoDePublicacao());
        }


    }

    public void adicionarAoProjeto(Projeto projeto) {

        this.getProjetosPertencentesAtualmente().add(projeto);
        this.getTodosProjetos().add(projeto);

    }

    public int quantidadeDeProjetosEmAndamento() {
        return this.getProjetosPertencentesAtualmente().size();
    }

    public ArrayList<Projeto> getTodosProjetos() {
        return todosProjetos;
    }

    public ArrayList<Projeto> getProjetosPertencentesAtualmente() {
        return projetosPertencentesAtualmente;
    }

    public ProducaoAcademica getProducoesDoColaborador() {
        return producoesDoColaborador;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public int getTipo() {
        return tipo;
    }
}
