package br.com.javaparaweb.capitulo3.crudjdbc;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class ContatoCrudJDBC {

	public void salvar(Contato contato) {
		
		Connection conexao = this.geraConexao();
		PreparedStatement insereSt = null;
		String sql= "insert into contato (nome, telefone, email, dt_cad, obs) "
				+ "values(?,?,?,?,?)";
		
		try {
			insereSt = conexao.prepareStatement(sql);
			insereSt.setString(1, contato.getNome());
			insereSt.setString(2, contato.getTelefone());
			insereSt.setString(3, contato.getEmail());
			insereSt.setDate(4, contato.getDataCadastro());
			insereSt.setString(5, contato.getObservacao());
			insereSt.executeUpdate();		
		} catch(SQLException e) {
			System.out.println("Erro ao incluir contato. Mensagem: "+ e.getMessage());
		} finally {
			try {
				insereSt.close();
				conexao.close();
			} catch(Throwable e) {
				System.out.println("Erro ao fechar opera��o de inser��o. Mesagem: "+ e.getMessage());
			}
		}
		
	}
	public void atualizar(Contato contato) {}
	public void excluir(Contato contato) {}
	public List<Contato> listar() {
		Connection conexao = this.geraConexao();
		List<Contato> contatos = new ArrayList<Contato>();
		Statement consulta = null;
		ResultSet resultado = null;
		Contato contato = null;
		String sql="select * from contato";
		try {
			
			consulta = conexao.createStatement();
			resultado = consulta.executeQuery(sql);
			while (resultado.next()) {
				contato = new Contato();
				contato.setCodigo(resultado.getInt("codigo"));
				contato.setNome(resultado.getString("nome"));
				contato.setTelefone(resultado.getString("telefone"));
				contato.setEmail(resultado.getString("email"));
				contato.setDataCadastro(resultado.getDate("dt_cad"));
				contato.setObservacao(resultado.getString("obs"));
				contatos.add(contato);
			}
			
		} catch(SQLException e) {
			System.out.println("Erro ao buscar c�digo do contato. Mensagem: "+ e.getMessage());
		} finally {
			try {
				consulta.close();
				resultado.close();
				conexao.close();
			} catch(Throwable e){
				System.out.println("Erro ao fechar opera��o de consulta. Mensagem: "+ e.getMessage());
			}
			
		}
		return contatos;
		
	}
	/*
	public Contato buscarContato(int valor) {
		
		
		
	}
	*/
	public Connection geraConexao() {
Connection conexao = null;
		
		try {
			//Registrando a classe JDBC e os par�metros de conex�o em tempo de execu��o
	 String url = "jdbc:mysql://localhost/agenda";
	 String usuario = "mateus";
	 String senha = "123";
	 
	 conexao = DriverManager.getConnection(url, usuario, senha);
	 System.out.println("Conectou!");
	 
	 
	
		} catch(SQLException e){
			System.out.println("Ocorreu um erro ao criar a conex�o. Erro: "+e.getMessage());
			
		}
	 return conexao;
	} 
    public static void main(String[] args) {
    	
    	ContatoCrudJDBC contatoCRUDJDBC = new ContatoCrudJDBC();
    	
    // Criando um primero contato
    	
    	Contato beltrano = new Contato();
    	beltrano.setNome("Beltrano Solar");
    	beltrano.setTelefone("(47) 5555-3333");
    	beltrano.setEmail("beltrano@teste.com.br");
    	beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
    	beltrano.setObservacao("Novo cliente");
    	contatoCRUDJDBC.salvar(beltrano);
    	
   // Criando um segundo contato
    	
    	Contato fulano = new Contato();
    	beltrano.setNome("Fulano Lunar");
    	beltrano.setTelefone("(47) 7777-2222");
    	beltrano.setEmail("fulano@teste.com.br");
    	beltrano.setDataCadastro(new Date(System.currentTimeMillis()));
    	beltrano.setObservacao("Novo contato - poss�vel cliente");
    	contatoCRUDJDBC.salvar(fulano);
    	System.out.println("Contatos cadastrados: "+ contatoCRUDJDBC.listar().size());
    	
    }	
    
    
}
