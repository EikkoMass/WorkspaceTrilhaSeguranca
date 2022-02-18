package AutenticacaoJdbcDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import ArmaDadosUsuario.ArmazenaDadosUsuario;

public class JDBCAutenticaDAO {

	private Connection conexao;
	
	public JDBCAutenticaDAO(Connection conexao)
	{
		this.conexao = conexao;
	}
	
	public boolean consultar(ArmazenaDadosUsuario dadosautentica)
	{
		
		try {
			String comando = "SELECT * FROM dadosUsuario WHERE usuario = '" + dadosautentica.getUsuario() + 
					"' AND senha = '" + dadosautentica.getSenha() + "'";
			
			Statement stmt = conexao.createStatement();
			ResultSet rs = stmt.executeQuery(comando);
			
			if(rs.next())
			{
				return true;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
