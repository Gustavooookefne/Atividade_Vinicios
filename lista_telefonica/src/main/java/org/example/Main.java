package org.example;


import org.example.dao.ContatoDAO;
import org.example.model.Contato;
import org.example.utils.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {
        inicio();
    }
    public static void inicio(){
        boolean sair = false;
        System.out.println("""
                ---------------------Lista telefonica---------------------
                1. Cadastrar contato: Nome, Telefone, Email, Observação. \s
                2. Listar todos os contatos cadastrados. \s
                3. Buscar contato por nome. \s
                4. Atualizar dados de um contato (telefone, email, observação). \s
                5. Remover contato. \s
                6. Sair do sistema. \s
                """);
        int opcao = SC.nextInt();
        SC.nextLine();

        switch (opcao){
            case 1:{
                cadastrarContato();
                break;
            }
            case 2:{
                listarContatos();
                break;
            }
            case 3:{
                printarContatos();
                break;
            }
            case 4: {
                editarContato();
                break;
            }
            case 5: {
                removerContato();
                break;
            }
            case 6:{
                sair = true;
                System.out.println("Tchau!");
                break;
            }
        }
        if(!sair){
            inicio();
        }
    }
    public static void cadastrarContato(){
        System.out.println("-------Cadastrar Contato-------\n");
        System.out.println("Digite o nome do contato: ");
        String nome = SC.nextLine();

        System.out.println("Digite o telefone do contato: ");
        String telefone = SC.nextLine();

        System.out.println("Digite o email do contato: ");
        String email = SC.nextLine();

        System.out.println("Digite a observação sobre o contato: ");
        String observacao = SC.nextLine();

        var contato = new Contato(nome, telefone, email, observacao);
        var contatoDao = new ContatoDAO();
        try{
            contatoDao.inserirContato(contato);
            System.out.println("O usuário foi inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro no banco de dados!");
            e.printStackTrace();
        }
    }
    public static void listarContatos(){
        System.out.println("-----Lista de Contatos-----");
        List<Contato> contatos = new ArrayList<>();
        var contatoDao = new ContatoDAO();
        try {
            contatos = contatoDao.listarContato();
        }catch (SQLException e){
            System.out.println("Ocorreu um erro no banco de dados!");
            e.printStackTrace();
        }
        Utils.exibirContatos(contatos);
   }

   public static void printarContatos(){
        List<Contato> contatos = Utils.buscarContatoPorNome();

        Utils.exibirContatos(contatos);
   }

   public static void editarContato(){
        ContatoDAO dao = new ContatoDAO();

        List<Contato> contatos = Utils.buscarContatoPorNome();
        Utils.exibirContatos(contatos);
        System.out.println(" - Digite o ID do contato desejado: ");
        int idContato = SC.nextInt();
        SC.nextLine();

        String valorEditar = null;

        if(Utils.validarID(contatos, idContato) == true){
            do{
               System.out.println("""
                       1 - Editar Telefone
                       2 - Editar E-mail
                       3 - Editar Observação
                       0 - Voltar ao menu Principal
                       """);
                System.out.print(" ? - Sua escolha: ");
               int opcao = SC.nextInt();
               SC.nextLine();

               String coluna = "";
               switch (opcao){
                   case 1 -> {
                       coluna = "telefone";
                   }
                   case 2 -> {
                       coluna = "email";
                   }
                   case 3 -> {
                       coluna = "observacao";
                   }
                   case 0 -> {
                       return;
                   }
                   default -> {
                       System.out.println("Escolha inválida!");
                   }
               }
               System.out.println("\n - Digite o novo valor do " + coluna +": ");
               valorEditar = SC.nextLine();
               try{
                   dao.editarContato(idContato, coluna, valorEditar);
               } catch (SQLException e) {
                   e.printStackTrace();
               }
            }while(true);
        }else{
            System.out.println("\n=== Contato não encontrado! ===\n");
        }
   }

   public static void removerContato() {

        ContatoDAO contatoDAO = new ContatoDAO();
       List<Contato> contatos = Utils.buscarContatoPorNome();
       Utils.exibirContatos(contatos);

       System.out.println(" - Digite o ID do contato desejado: ");
       int idContato = SC.nextInt();
       SC.nextLine();

       // Poderia haver uma validação! Assim, a escolha do usuário caso realmente queira remover contato.s

       if (Utils.validarID(contatos, idContato)) {

            try {

                contatoDAO.removerContato(idContato);

            } catch (SQLException e) {
                System.out.println("=== Erro ao remover Contato ===");

                e.printStackTrace();
            }

       } else {
           System.out.println("\n=== Contato não encontrado! ===");
       }


   }



}