import java.time.LocalDate;
import java.util.*;

public class Projeto {

    ArrayList<Colaborador> colaboradoresAlunosDeGraduacao = new ArrayList<Colaborador>();
    ArrayList<Colaborador> colaboradoresAlunosDeMestrado = new ArrayList<Colaborador>();
    ArrayList<Colaborador> colaboradoresAlunosDeDoutorado = new ArrayList<Colaborador>();
    ArrayList<Colaborador> colaboradoresProfessores = new ArrayList<Colaborador>();
    ArrayList<Colaborador> colaboradoresPesquisadores = new ArrayList<Colaborador>();

    ProducaoAcademica producoesDoProjeto = new ProducaoAcademica();

    int status;
    String titulo;
    LocalDate dataDeInicio;
    LocalDate dataDeTermino;
    String agenciaFinanciadora;
    double valorFinanciado;
    String objetivo;
    String descricao;

    public Projeto(String titulo, LocalDate dataDeInicio, LocalDate dataDeTermino, String agenciaFinanciadora, double valorFinanciado, String objetivo, String descricao) {
        this.titulo = titulo;
        this.dataDeInicio = dataDeInicio;
        this.dataDeTermino = dataDeTermino;
        this.agenciaFinanciadora = agenciaFinanciadora;
        this.valorFinanciado = valorFinanciado;
        this.objetivo = objetivo;
        this.descricao = descricao;
    }

    public void ordenarProducoes() {

        Collections.sort(this.getProducoesDoProjeto().getOrientacoes(), Collections.reverseOrder(new Comparator<Orientacao>() {
            public int compare(Orientacao o1, Orientacao o2) {
                return Integer.compare(o1.getData(), o2.getData());
            }
        }));

        Collections.sort(this.getProducoesDoProjeto().getPublicacoes(), Collections.reverseOrder(new Comparator<Publicacao>() {
            public int compare(Publicacao o1, Publicacao o2) {
                return Integer.compare(o1.getAnoDePublicacao(), o2.getAnoDePublicacao());
            }
        }));

    }

    public void alterarStatus() {

        Scanner scanner = new Scanner(System.in);

        if(this.getStatus() == -1) {
            System.out.println("Deseja alterar o status para \"Em andamento\" ?");
            System.out.println("(1) - Sim\n" +
                    "(2) - Não");
            int escolha = scanner.nextInt();
            if(escolha == 1) {
                this.setStatus(0);
            }
        } else if(this.getStatus() == 0) {
            System.out.println("Deseja alterar o status para \"Concluído\" ?");
            System.out.println("(1) - Sim\n" +
                    "(2) - Não");
            int escolha = scanner.nextInt();
            if(escolha == 1) {
                this.setStatus(1);
                this.concluirParaTodos();
            }


        }else if (this.getStatus() == 1) {
            System.out.println("Projeto finalizado, não é possivel alterar.");
        }


    }

    public void concluirParaTodos() {

        for (Colaborador colaborador : this.getColaboradoresAlunosDeGraduacao()) {
            colaborador.getProjetosPertencentesAtualmente().remove(this);
        }
        for (Colaborador colaborador : this.getColaboradoresAlunosDeMestrado()) {
            colaborador.getProjetosPertencentesAtualmente().remove(this);
        }
        for (Colaborador colaborador : this.getColaboradoresAlunosDeDoutorado()) {
            colaborador.getProjetosPertencentesAtualmente().remove(this);
        }
        for (Colaborador colaborador : this.getColaboradoresProfessores()) {
            colaborador.getProjetosPertencentesAtualmente().remove(this);
        }
        for (Colaborador colaborador : this.getColaboradoresPesquisadores()) {
            colaborador.getProjetosPertencentesAtualmente().remove(this);
        }


    }
    
    public void detalharProjeto() {

        System.out.println("Título: " + this.getTitulo() +
                "\nStatus: " + (this.getStatus() == -1 ? "Em elaboração" : (this.getStatus() == 0 ? "Em andamento" : "Concluído")) +
                "\nData de início: " + this.getDataDeInicio().toString() +
                "\nData de término: " + this.getDataDeTermino().toString() +
                "\nAgência financiadora: " + this.getAgenciaFinanciadora() +
                "\nValor financiado: " + this.getValorFinanciado() +
                "\nObjetivo: " + this.getObjetivo() +
                "\nDescrição: " + this.getDescricao()
        );

        this.listarMembros();

        //TODO adicionar listagem de produção academica

        this.listarProducoesDoProjeto();

        System.out.println();
        
    }

    public void listarProducoesDoProjeto() {

        this.ordenarProducoes();

        System.out.println("Publicações:");
        for(Publicacao publicacao : this.getProducoesDoProjeto().getPublicacoes()) {

            System.out.println("\t" + publicacao.getTituloDaPublicacao() + " - " + publicacao.getAnoDePublicacao());

        }


        System.out.println("Orientações:");
        for(Orientacao orientacao : this.getProducoesDoProjeto().getOrientacoes()) {

            System.out.println("\t" + orientacao.getTituloDaOrientacao() + " - " + orientacao.getData());

        }

    }

    public void adicionarProducao(int tipo) {

        Scanner scanner = new Scanner(System.in);

        if(tipo == 1) {

            Publicacao novaPublicacao = new Publicacao();
            System.out.print("Digite o título da publicação: ");
            novaPublicacao.setTituloDaPublicacao(scanner.nextLine());
            System.out.print("Digite o ano de publicação: ");
            novaPublicacao.setAnoDePublicacao(scanner.nextInt());
            scanner.nextLine();
            System.out.print("Digite o nome da conferência onde foi publicada: ");
            novaPublicacao.setNomeDaConferencia(scanner.nextLine());

            System.out.println("Digite o nome do autor: ");
            String nome = scanner.nextLine();
            Colaborador autor = Laboratorio.checarExistente(nome);
            while (autor == null) {
                System.out.println("Autor não encontrado no laboratório, favor digite outro.");
                nome = scanner.nextLine();
                autor = Laboratorio.checarExistente(nome);
            }

            novaPublicacao.getAutores().add(autor);
            autor.getProducoesDoColaborador().getPublicacoes().add(novaPublicacao);

            novaPublicacao.adicionarAutores();

            this.getProducoesDoProjeto().getPublicacoes().add(novaPublicacao);

        } else if(tipo == 2) {

            System.out.println("Digite o título da orientação: ");
            String titulo  = scanner.nextLine();
            System.out.println("Digite o ano da orientação:");
            int data = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Digite o nome do orientando: ");
            String nome = scanner.nextLine();
            Colaborador orientando = Laboratorio.checarProfessorExistente(nome);

            while(orientando == null){

                System.out.println("Colaborador não registrado no laboratório ou não é um professor, digite outro:");
                nome = scanner.nextLine();
                orientando = Laboratorio.checarProfessorExistente(nome);

            }

            Orientacao novaOrientacao = new Orientacao();

            novaOrientacao.setTituloDaOrientacao(titulo);
            novaOrientacao.setOrientando(orientando);
            novaOrientacao.adicionarOrientados();
            novaOrientacao.setData(data);

            this.getProducoesDoProjeto().getOrientacoes().add(novaOrientacao);

            orientando.getProjetosPertencentesAtualmente().add(this);
            orientando.getTodosProjetos().add(this);

        }

    }

    public void alocarMembro() {

        Scanner scanner = new Scanner(System.in);

        while(true) {

            System.out.println("Escolha: \n" +
                    "(1) - Alocar participante para o projeto\n" +
                    "(outro) - Sair da alocação");

            int escolha = scanner.nextInt();
            scanner.nextLine();
            if(escolha == 1) {
                System.out.print("Digite o nome do colaborador: ");
                String nome = scanner.nextLine();
                Colaborador colaborador = Laboratorio.checarExistente(nome);

                if(colaborador != null) {

                    if(colaborador.getTipo() == 1) {
                        if(colaborador.quantidadeDeProjetosEmAndamento() < 2) {
                            this.getColaboradoresAlunosDeGraduacao().add(colaborador);
                        } else {
                            System.out.println("Colaborador está no limite de projetos.");
                        }
                    } else if (colaborador.getTipo() == 2) {
                        this.getColaboradoresAlunosDeMestrado().add(colaborador);
                    } else if (colaborador.getTipo() == 3) {
                        this.getColaboradoresAlunosDeDoutorado().add(colaborador);
                    } else if(colaborador.getTipo() == 4) {
                        this.getColaboradoresProfessores().add(colaborador);
                    } else if(colaborador.getTipo() == 5) {
                        this.getColaboradoresPesquisadores().add(colaborador);
                    }

                    colaborador.adicionarAoProjeto(this);

                } else {
                    System.out.println("Colaborador não pertence ao laboratório.");
                }

            } else {
                break;
            }
        }
    }
    
    public void listarMembros() {
        System.out.println("Colaboradores: ");

        System.out.println("\tAlunos de Graduacao:");
        for (Colaborador colaborador : this.getColaboradoresAlunosDeGraduacao()) {
            System.out.println("\t\t" + colaborador.getNome());
        }

        System.out.println("\tAlunos de Mestrado:");
        for (Colaborador colaborador : this.getColaboradoresAlunosDeMestrado()) {
            System.out.println("\t\t" + colaborador.getNome());
        }

        System.out.println("\tAlunos de Doutorado:");
        for (Colaborador colaborador : this.getColaboradoresAlunosDeDoutorado()) {
            System.out.println("\t\t" + colaborador.getNome());
        }

        System.out.println("\tProfessores:");
        for (Colaborador colaborador : this.getColaboradoresProfessores()) {
            System.out.println("\t\t" + colaborador.getNome());
        }

        System.out.println("\tPesquisadores:");
        for (Colaborador colaborador : this.getColaboradoresPesquisadores()) {
            System.out.println("\t\t" + colaborador.getNome());
        }
    }


    public ArrayList<Colaborador> getColaboradoresAlunosDeGraduacao() {
        return colaboradoresAlunosDeGraduacao;
    }

    public ArrayList<Colaborador> getColaboradoresAlunosDeMestrado() {
        return colaboradoresAlunosDeMestrado;
    }

    public ArrayList<Colaborador> getColaboradoresAlunosDeDoutorado() {
        return colaboradoresAlunosDeDoutorado;
    }

    public ArrayList<Colaborador> getColaboradoresProfessores() {
        return colaboradoresProfessores;
    }

    public ArrayList<Colaborador> getColaboradoresPesquisadores() {
        return colaboradoresPesquisadores;
    }

    public ProducaoAcademica getProducoesDoProjeto() {
        return producoesDoProjeto;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getDataDeInicio() {
        return dataDeInicio;
    }

    public LocalDate getDataDeTermino() {
        return dataDeTermino;
    }

    public String getAgenciaFinanciadora() {
        return agenciaFinanciadora;
    }

    public double getValorFinanciado() {
        return valorFinanciado;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getDescricao() {
        return descricao;
    }

}
