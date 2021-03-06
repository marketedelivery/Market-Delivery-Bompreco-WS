package br.com.bomprecosupermercado.negocio;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.bomprecosupermercado.classesBasicas.Produto;
import br.com.bomprecosupermercado.dados.factory.DAOFactory;
import br.com.bomprecosupermercado.interfaces.dados.IProdutoDAO;
import br.com.bomprecosupermercado.interfaces.negocio.IControladorProduto;
import br.com.bomprecosupermercado.util.Mensagens;

@Path("/produto/bompreco")
public class ControladorProduto implements IControladorProduto
{
	// Atributos
	private IProdutoDAO produtoDAO;

	Mensagens msg = new Mensagens();

	// Métodos
	@POST
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/cadastrarProduto")
	public Response cadastrarProduto(Produto produto)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		String mensagem = "";
		try
		{
			produtoDAO.inserir(produto);
			
			mensagem = msg.getMsg_produto_cadastrado_com_sucesso();
			return Response.ok(new Gson().toJson(produto)).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(new Gson().toJson(new Produto())).build();
	}

	@PUT
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/alterarProduto")
	public Response alterarProduto(Produto produto)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		String mensagem = "";
		try
		{
			produtoDAO.alterar(produto);
			mensagem = msg.getMsg_produto_alterado_com_sucesso();
			return Response.ok(new Gson().toJson(produto)).build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return Response.ok(new Gson().toJson(new Produto())).build();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/consultarTodosProdutos")
	public Response consultarTodosProdutos()
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		List<Produto> lista = produtoDAO.consultarTodos();
		if(lista == null || lista.isEmpty())
		{
			return Response.ok(new Gson().toJson(new Produto())).build();
		}
			
			return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/pesquisarProdutoPorNome/{nome}")
	public Response pesquisarProdutoPorNome(@PathParam("nome") String nome)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		Produto p = produtoDAO.pesquisarProdutoPorNome(nome);
		if(p != null)
		{
			return Response.ok(new Gson().toJson(p)).build();
		}
			return Response.ok(new Gson().toJson(new Produto())).build();
	}
	
	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/consultarProdutoPorId/{id}")
	public Response consultarProdutoPorId(@PathParam("id") int produtoID)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		Produto produtoRetorno = produtoDAO.consultarPorId(produtoID);
		if (produtoRetorno == null) {
			return Response.ok().build();
		}
		return Response.ok(produtoRetorno).build();

	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/consultarProdutosPorTipo/{tipo}")
	public Response consultarProdutosPorTipo(@PathParam("tipo") String tipo)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		List<Produto> lista = produtoDAO.consultarProdutosPorTipo(tipo);
		if (lista == null || lista.isEmpty())
		{
			
			return Response.ok(new Gson().toJson(new ArrayList<>())).build();
		}
			return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/consultarProdutosPorSupermercado/{supermercado}")
	public Response consultarProdutosPorSupermercado(@PathParam("supermercado") int supermercado)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		List<Produto> lista = produtoDAO.pesquisarProdutoPorSupermercado(supermercado);
		if (lista == null || lista.isEmpty())
		{	
			return Response.ok(new Gson().toJson(new ArrayList<>())).build();
		}
			return Response.ok(new Gson().toJson(lista)).build();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/pesquisarProdutoComParametros/{nome}, {tipo}, {marca}")
	@Override
	public Produto pesquisarProdutoComParametros(@PathParam("nome") String nome, @PathParam("tipo") String tipo,
			@PathParam("marca") String marca)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		Produto p = produtoDAO.pesquisarProdutoComParametros(nome, tipo, marca);
		if (p == null)
		{
			return new Produto();
		}
		return p;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/pesquisarProdutoComParametrosLista/{nome}, {tipo}, {marca}")
	@Override
	public List<Produto> pesquisarProdutoComParametrosLista(@PathParam("nome") String nome,
			@PathParam("tipo") String tipo, @PathParam("marca") String marca)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		List<Produto> lista = produtoDAO.pesquisarProdutoComParametrosLista(nome, tipo, marca);
		if (!lista.isEmpty())
		{
			return lista;
		}
		return new ArrayList<>();
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	@Consumes("application/json; charset=UTF-8")
	@Path("/retornarProdutoPorNome/{nome}")
	public Response retornarProdutoPorNome(@PathParam("nome") String nome)
	{
		produtoDAO = DAOFactory.getProdutoDAO();
		List<Produto> lista = produtoDAO.retornarProdutoPorNome(nome);
		if (!lista.isEmpty())
		{
			return Response.ok(new Gson().toJson(lista)).build();
		}else
		{
			return Response.ok(new Gson().toJson(new ArrayList<>())).build();
		}
	}
}