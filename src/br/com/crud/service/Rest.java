package br.com.crud.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.crud.connect.Conexao;
import br.com.crud.entity.Pessoa;

@Path("/pessoa")
public class Rest {

	// Rest para criar nova pessoa
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@POST
	public Response inserirPessoa(Pessoa pessoa) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		try {
			Conexao conexao = new Conexao();
			conexao.inserirPessoa(pessoa);
			conexao.fecharConexao();
			return Response.status(200).entity("cadastro de pessoa realizado.").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar pessoa.").build();
		}
	}

	// Rest para listar todas as pessoas
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response listarPessoas() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		List<Pessoa> pessoas = new ArrayList<>();

		try {
			Conexao conexao = new Conexao();
			pessoas = conexao.listarPessoas();
			conexao.fecharConexao();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar pessoas.").build();
		}

		GenericEntity<List<Pessoa>> entity = new GenericEntity<List<Pessoa>>(pessoas) {
		};
		return Response.status(Status.OK).entity(entity).build();
	}

	// Rest para atualizar dados da pessoa
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PUT
	public Response alterarPessoa(@PathParam("id") int id, Pessoa pessoa) {
		try {
			Conexao conexao = new Conexao();
			conexao.alterarPessoa(id, pessoa);
			conexao.fecharConexao();
			return Response.status(200).entity("alteração realizada.").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao alterar pessoa.").build();
		}
	}

	// Rest para listar dados de uma pessoa
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@GET
	public Response listarPessoa(@PathParam("id") int id) {
		Pessoa pessoa = new Pessoa();
		try {
			Conexao conexao = new Conexao();
			pessoa = conexao.listarPessoa(id);
			conexao.fecharConexao();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao buscar a pessoa.").build();
		}

		GenericEntity<Pessoa> entity = new GenericEntity<Pessoa>(pessoa) {
		};
		return Response.status(Status.OK).entity(entity).build();
	}

	// Rest para deletar uma pessoa
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@DELETE
	public Response deletarPessoa(@PathParam("id") int id) {
		try {
			Conexao conexao = new Conexao();
			conexao.deletarPessoa(id);
			conexao.fecharConexao();
			return Response.status(200).entity("Pessoa foi removida com sucesso.").build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Erro ao remover a pessoa.").build();
		}
	}

}
