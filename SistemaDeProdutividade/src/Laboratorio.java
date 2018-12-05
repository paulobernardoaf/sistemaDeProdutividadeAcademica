import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Laboratorio {

    private static ArrayList<Colaborador> listaDeColaboradores = new ArrayList<Colaborador>();
    private static ArrayList<Projeto> listaDeProjetos = new ArrayList<Projeto>();

    public void adicionarParticipante() {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Digite o nome do colaborador: ");
        String nome = scanner.nextLine();
        System.out.println("Digite o email do colaborador: ");
        String email = scanner.nextLine();

        System.out.println("Escolha o tipo do colaborador:\n" +
                "(1) - Aluno de graduação\n" +
                "(2) - Aluno de mestrado\n" +
                "(3) - Aluno de doutorado\n" +
                "(4) - Professor\n" +
                "(5) - Pesquisador");

        int tipo = scanner.nextInt();
        scanner.nextLine();

        if(tipo > 5 || tipo < 1) {
            System.out.println("Tipo inválido.");
            return;
        }

        Colaborador colaborador = new Colaborador(nome, email, tipo);

        Laboratorio.getListaDeColaboradores().add(colaborador);

    }

    public void listarColaboradores() {

        for (Colaborador colaborador : Laboratorio.getListaDeColaboradores()) {

            System.out.println(
                    "Nome: " + colaborador.getNome() +
                    "\nEmail: " + colaborador.getEmail() +
                    "\nTipo: " + colaborador.getTipo()
            );
            System.out.println();

        }

    }

    public void criarProjeto() {

        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.print("Digite o título do projeto: ");
        String nomeProjeto = scanner.nextLine();
        System.out.println("Digite a data de início (format: dd/MM/yyyy) :");
        String dataEmString = scanner.nextLine();
        LocalDate dataInicio = LocalDate.parse(dataEmString, formato);
        System.out.println("Digite a data de término (format: dd/MM/yyyy) :");
        dataEmString = scanner.nextLine();
        LocalDate dataTermino = LocalDate.parse(dataEmString, formato);
        System.out.print("Digite o nome da Agência Financiadora: ");
        String agenciaFinanciadora = scanner.nextLine();
        System.out.print("Digite o valor financiado: ");
        double valorFinanciado = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Digite o objetivo do projeto: ");
        String objetivo = scanner.nextLine();
        System.out.println("Digite a descrição do projeto: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o nome do professor responsável: ");
        String professor = scanner.nextLine();

        Colaborador colaborador = null;
        while(true) {
            colaborador = checarProfessorExistente(professor);
            if(colaborador == null) {
                System.out.println("Professor não encontrado no sistema, digite um existente: ");
                professor = scanner.nextLine();
            } else {
                break;
            }
        }

        Projeto novoProjeto = new Projeto(nomeProjeto, dataInicio, dataTermino, agenciaFinanciadora, valorFinanciado, objetivo, descricao);

        novoProjeto.getColaboradoresProfessores().add(colaborador);
        colaborador.getTodosProjetos().add(novoProjeto);
        colaborador.getProjetosPertencentesAtualmente().add(novoProjeto);
        novoProjeto.alocarMembro();

        while(novoProjeto.getStatus() == -1) {
            System.out.println("Para finalizar a criação tens que alterar o status para em andamento.");
            novoProjeto.alterarStatus();
        }

        Laboratorio.getListaDeProjetos().add(novoProjeto);
    }

    public static Projeto checarProjetoExistente(String nome) {

        for (Projeto projeto : Laboratorio.getListaDeProjetos()) {
            if(projeto.getTitulo().equals(nome)) {
                return projeto;
            }
        }

        return null;

    }

    public void listarProjetos() {

        for (Projeto projeto : Laboratorio.getListaDeProjetos()) {

            projeto.detalharProjeto();

        }

    }

    public void criarProducaoAcademica() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Que tipo de produção deseja registrar?\n" +
                "(1) - Publicação\n" +
                "(2) - Orientação");

        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Deseja associar essa produção a algum projeto em andamento?\n" +
                "(1) - Sim\n" +
                "(outro) - Não");

        int escolha = scanner.nextInt();
        scanner.nextLine();

        if(escolha == 1) {
            System.out.print("Digite o Título do projeto de pesquisa associado: ");
            String nome = scanner.nextLine();
            Projeto projeto = Laboratorio.checarProjetoExistente(nome);
            while(true) {
                if(projeto == null) {
                    System.out.println("Projeto não existente no laboratório, digite outro:");
                    nome = scanner.nextLine();
                    projeto = Laboratorio.checarProjetoExistente(nome);
                } else {
                    break;
                }
            }

            if(projeto.getStatus() != 0) {
                System.out.println("Projeto não está \"Em andamento\", não podes adicionar uma produção acadêmica a ele.");
                return;
            }

            projeto.adicionarProducao(tipo);
            projeto.ordenarProducoes();

        } else {

            if(tipo == 1) {

                Publicacao novaPublicacao = new Publicacao();
                System.out.print("Digite o título da publicação: ");
                novaPublicacao.setTituloDaPublicacao(scanner.nextLine());
                System.out.print("Digite o ano de publicação: ");
                novaPublicacao.setAnoDePublicacao(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Digite o nome da conferência onde foi publicada: ");
                novaPublicacao.setNomeDaConferencia(scanner.nextLine());

                novaPublicacao.adicionarAutores();

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

                orientando.getProducoesDoColaborador().getOrientacoes().add(novaOrientacao);


            }

        }


    }

    public void consultarProjeto() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do projeto:");
        String nome = scanner.nextLine();

        Projeto projeto = Laboratorio.checarProjetoExistente(nome);

        if(projeto != null) {

            projeto.detalharProjeto();

        } else {
            System.out.println("Projeto não existente.");
        }

    }

    public void finalizarProjeto() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do projeto que deseja finalizar:");
        String nome = scanner.nextLine();
        Projeto projeto = Laboratorio.checarProjetoExistente(nome);

        if(projeto != null) {

            if(projeto.getProducoesDoProjeto().getPublicacoes().size() == 0 && projeto.getProducoesDoProjeto().getOrientacoes().size() == 0) {
                System.out.println("Não  podes finalizar um projeto sem publicações.");
                return;
            } else {
                projeto.alterarStatus();
            }

        }

    }

    public void consultarColaborador() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do colaborador: ");
        String nome  = scanner.nextLine();

        Colaborador colaborador = Laboratorio.checarExistente(nome);

        if(colaborador != null) {

            colaborador.detalhar();

        } else {
            System.out.println("Colaborador não registrado no laboratório.");
        }


    }

    public static Colaborador checarExistente(String nome) {

        for (Colaborador colaborador : Laboratorio.getListaDeColaboradores()) {
            if(colaborador.getNome().equals(nome)) {
                return colaborador;
            }
        }

        return null;

    }

    public static Colaborador checarProfessorExistente(String nome) {

        for (Colaborador colaborador : Laboratorio.getListaDeColaboradores()) {
            if(colaborador.getTipo() == 4 && colaborador.getNome().equals(nome)) {
                return colaborador;
            }
        }

        return null;

    }


    public static ArrayList<Colaborador> getListaDeColaboradores() {
        return listaDeColaboradores;
    }

    public static ArrayList<Projeto> getListaDeProjetos() {
        return listaDeProjetos;
    }

}
