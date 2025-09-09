package org.example.utils;

import org.example.dao.ContatoDAO;
import org.example.model.Contato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    static Scanner SC = new Scanner(System.in);

    public static void exibirContatos(List<Contato> contatos){
        if(!contatos.isEmpty()){
            for(Contato contato:contatos){
                System.out.println("\n----------------------------------------");
                System.out.println("ID: " + contato.getId());
                System.out.println("NOME: " + contato.getNome());
                System.out.println("TELEFONE: " + contato.getTelefone());
                System.out.println("EMAIL: " + contato.getEmail());
                System.out.println("OBSERVACAO: " + contato.getObservacao());
            }
        }else{
            System.out.println("Nenhum contato encontrado!");
        }
    }

    public static List<Contato> buscarContatoPorNome(){
        System.out.println("---Buscar Contato por Nome---");
        System.out.println("Digite o nome do contato que deseja buscar: ");
        String nome = SC.nextLine();
        List<Contato> contatos = new ArrayList<>();
        try{
            var contatoDao = new ContatoDAO();
            contatos = contatoDao.buscarContatosPorNome(nome);
        }catch (SQLException e){
            System.out.println("Ocorreu um erro no banco de dados!");
            e.printStackTrace();
        }

        return contatos;
    }

    public static boolean validarID(List<Contato> contatos, int id){
        boolean encontrado = false;
        for(Contato c : contatos){
            if(c.getId() == id){
                encontrado = true;
                return encontrado;
            }
        }
        return encontrado;
    }
}
