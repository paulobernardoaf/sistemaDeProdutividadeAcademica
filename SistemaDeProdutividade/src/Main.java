import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Laboratorio lab = new Laboratorio();

        while(true) {

            System.out.println("Escolha a opção: \n" +
                    "(1) - Adicionar colaborador ao laboratório\n" +
                    "(2) - Listar colaboradores\n" +
                    "(3) - Criar um novo Projeto\n" +
                    "(4) - Listar todos os Projetos\n" +
                    "(5) - Criar nova produção acadêmica\n" +
                    "(6) - Consultar colaborador\n" +
                    "(7) - Consultar projeto\n" +
                    "(8) - Finalizar um projeto");

            int escolha = scanner.nextInt();
            scanner.nextLine();

            if(escolha == 1) {
                lab.adicionarParticipante();
            } else if (escolha == 2) {
                lab.listarColaboradores();
            } else if(escolha == 3) {
                lab.criarProjeto();
            } else if(escolha == 4) {
                lab.listarProjetos();
            } else if(escolha == 5) {
                lab.criarProducaoAcademica();
            } else if(escolha == 6) {
                lab.consultarColaborador();
            } else if(escolha == 7){
                lab.consultarProjeto();
            } else if(escolha == 8) {
                lab.finalizarProjeto();
            }

        }

    }
}
