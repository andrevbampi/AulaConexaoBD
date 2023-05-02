package curso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import driver.ConnectionMariaDB;

public class AlunoBD {
	
	public static void inserir(Aluno aluno) {
		try {
			//Iniciando a conexão com o banco de dados
			Connection connection = ConnectionMariaDB.conectar();
			
			String comandoSql = "insert into curso2023.aluno"
					+ " (id, nome, nota1, nota2, media)"
					+ " values (?, ?, ?, ?, ?)";
			
			//Preparando o comando SQL
			PreparedStatement ps = connection.prepareStatement(comandoSql);
			
			//Substituindo os "?" por valores válidos para serem utilizados no comando SQL
			ps.setInt(1, aluno.getId());
			ps.setString(2, aluno.getNome());
			ps.setDouble(3, aluno.getNota1());
			ps.setDouble(4, aluno.getNota2());
			ps.setDouble(5, aluno.getMedia());
			
			//Executando o comando SQL
			if (!ps.execute()) {
				System.out.println("Aluno inserido com sucesso.");
			}
			
			//Fechando a conexão com o banco de dados. Nunca esquecer de fazer isso após terminar de utilizar a conexão.
			connection.close();
			
		} catch (SQLException e) {
			System.out.println("Erro ao inserir aluno: " + e.getMessage());
		}
	}
	
	public static void listarTodos() {
		try {
			Connection connection = ConnectionMariaDB.conectar();
			String comandoSql = "select id, nome, nota1, nota2, media"
					+ " from curso2023.aluno order by id";
			
			PreparedStatement ps = connection.prepareStatement(comandoSql);
			
			//ResultSet é utilizado para comandos SQL que tenham resultado, como selects.
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				System.out.println("Id: " + rs.getInt("id")
						+ ", nome: " + rs.getString("nome")
						+ ", nota 1: " + rs.getDouble("nota1")
						+ ", nota 2: " + rs.getDouble("nota2")
						+ ", média: " + rs.getDouble("media"));
			}
			
			connection.close();
		} catch (SQLException e) {
			System.out.println("Erro ao listar alunos: " + e.getMessage());
		}
	}
	
	public static void excluir(int id) {
		try {
			Connection connection = ConnectionMariaDB.conectar();
			
			String comandoSql = "delete from curso2023.aluno"
					+ " where id = ?";
			
			PreparedStatement ps = connection.prepareStatement(comandoSql);
			ps.setInt(1, id);
			
			if (!ps.execute()) {
				System.out.println("Aluno excluído com sucesso.");
			}
			
			connection.close();
		} catch (SQLException e) {
			System.out.println("Erro ao excluir aluno: " + e.getMessage());
		}
	}
	
	public static Aluno buscarPorId(int id) {
		try {
			Connection connection = ConnectionMariaDB.conectar();
			
			String comandoSql = "select id, nome, nota1, nota2, media"
					+ " from curso2023.aluno where id = ?";
			
			PreparedStatement ps = connection.prepareStatement(comandoSql);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			Aluno aluno = null;
			if (rs.next()) {
				aluno = new Aluno();
				aluno.setId(rs.getInt("id"));
				aluno.setNome(rs.getString("nome"));
				aluno.setNota1(rs.getDouble("nota1"));
				aluno.setNota2(rs.getDouble("nota2"));
				aluno.setMedia(rs.getDouble("media"));
			} else {
				System.out.println("Não existe aluno com esse id.");
			}
			connection.close();
			return aluno;
		} catch (SQLException e) {
			System.out.println("Erro ao buscar aluno por id: " + e.getMessage());
		}
		return null;
	}
	
	public static void editar(Aluno aluno) {
		try {
			Connection connection = ConnectionMariaDB.conectar();
			String comandoSql = "update curso2023.aluno"
					+ " set nome = ?, nota1 = ?, nota2 = ?, media = ?"
					+ " where id = ?";
			
			PreparedStatement ps = connection.prepareStatement(comandoSql);
			
			ps.setString(1, aluno.getNome());
			ps.setDouble(2, aluno.getNota1());
			ps.setDouble(3, aluno.getNota2());
			ps.setDouble(4, aluno.getMedia());
			ps.setInt(5, aluno.getId());
			
			if (!ps.execute()) {
				System.out.println("Aluno alterado com sucesso.");
			} else {
				System.out.println("Não existe aluno com esse id.");
			}
			
			connection.close();
		} catch (SQLException e) {
			System.out.println("Erro ao inserir aluno: " + e.getMessage());
		}
	}
}
