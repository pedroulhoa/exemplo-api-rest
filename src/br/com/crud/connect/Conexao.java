package br.com.crud.connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.crud.entity.Pessoa;

public class Conexao {

	Connection con = null;

	// Metodo para realizar coneção com o banco de dados
	public Conexao() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3306/pessoas";
		String user = "root";
		String password = "root";

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		con = DriverManager.getConnection(url, user, password);
	}

	// Metodo para fechar a conexão
	public void fecharConexao() throws SQLException {
		con.close();
	}

	// Metodo para inserir pessoa no banco
	public void inserirPessoa(Pessoa pessoa) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("insert pessoas (nome, endereco, email) values(?,?,?)");

			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getEndereco());
			preparedStatement.setString(3, pessoa.getEmail());
			preparedStatement.execute();

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
	}

	// Metodo para alterar dados de uma pessoa no banco de dados
	public void alterarPessoa(int id, Pessoa pessoa) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("update pessoas set nome = ?, endereco = ?, email = ? where id = ?");
			preparedStatement.setString(1, pessoa.getNome());
			preparedStatement.setString(2, pessoa.getEndereco());
			preparedStatement.setString(3, pessoa.getEmail());
			preparedStatement.setInt(4, id);
			preparedStatement.execute();
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

	// Metodo para listar todas as pessoas do banco
	public List<Pessoa> listarPessoas() {

		List<Pessoa> pessoas = new ArrayList<>();
		Pessoa pessoa = null;

		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from pessoas");
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				pessoa = new Pessoa();
				pessoa.setId(rs.getInt(1));
				pessoa.setNome(rs.getString(2));
				pessoa.setEndereco(rs.getString(3));
				pessoa.setEmail(rs.getString(4));
				pessoas.add(pessoa);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
		return pessoas;
	}

	// Metodo para listar uma pessoa do banco
	public Pessoa listarPessoa(int id) {

		Pessoa pessoa = null;
		try {
			PreparedStatement preparedStatement = con.prepareStatement("select * from pessoas where id = ?");
			preparedStatement.setInt(1, id);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				pessoa = new Pessoa();
				pessoa.setId(rs.getInt(1));
				pessoa.setNome(rs.getString(2));
				pessoa.setEndereco(rs.getString(3));
				pessoa.setEmail(rs.getString(4));
			}
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
		return pessoa;
	}

	// Metodo para deletar uma pessoa do banco
	public void deletarPessoa(int id) {
		try {
			PreparedStatement preparedStatement = con.prepareStatement("delete from pessoas where id = ?");
			preparedStatement.setInt(1, id);
			preparedStatement.execute();
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
		}
	}

}
