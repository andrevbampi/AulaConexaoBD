package curso;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import driver.ConnectionMariaDB;

public class Main {

	public static void main(String[] args) {
		//Testando a conexão com o banco de dados.
		try {
			//Iniciando a conexão com o banco de dados
			Connection connection = ConnectionMariaDB.conectar();
			System.out.println("Conectado com sucesso.");
			
			//Fechando a conexão com o banco de dados. Nunca esquecer de fazer isso após terminar de utilizar a conexão.
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Erro de conexão: " + e.getMessage());
		}
		
		Scanner scan = new Scanner(System.in);
		int opcao = 0;
		do {
			System.out.println("-- Menu de alunos --");
			System.out.println("1 - Inserir");
			System.out.println("2 - Listar todos");
			System.out.println("3 - Excluir");
			System.out.println("4 - Editar");
			System.out.println("0 - Sair");
			opcao = scan.nextInt();
			
			switch(opcao) {
			case 1: inserir(scan); break;
			case 2: AlunoBD.listarTodos(); break;
			case 3: System.out.print("Id do aluno: ");
				int id = scan.nextInt();
				AlunoBD.excluir(id); break;
			case 4: editar(scan); break;
			}
		
		} while (opcao != 0);
	}
	
	private static void inserir(Scanner scan) {
		System.out.print("Id: ");
		int id = scan.nextInt();
		System.out.print("Nome: ");
		String nome = scan.next();
		System.out.print("Nota 1: ");
		double nota1 = scan.nextDouble();
		System.out.print("Nota 2: ");
		double nota2 = scan.nextDouble();
		double media = (nota1 + nota2) / 2;
		
		Aluno aluno = new Aluno();
		aluno.setId(id);
		aluno.setNome(nome);
		aluno.setNota1(nota1);
		aluno.setNota2(nota2);
		aluno.setMedia(media);
		
		AlunoBD.inserir(aluno);
	}
	
	private static void editar(Scanner scan) {
		System.out.print("Id: ");
		int id = scan.nextInt();
		
		Aluno aluno = AlunoBD.buscarPorId(id);
		if (aluno != null) {
			System.out.print("Nome: ");
			String nome = scan.next();
			System.out.print("Nota 1: ");
			double nota1 = scan.nextDouble();
			System.out.print("Nota 2: ");
			double nota2 = scan.nextDouble();
			double media = (nota1 + nota2) / 2;
			
			aluno.setNome(nome);
			aluno.setNota1(nota1);
			aluno.setNota2(nota2);
			aluno.setMedia(media);
			
			AlunoBD.editar(aluno);
		}
	}

}
