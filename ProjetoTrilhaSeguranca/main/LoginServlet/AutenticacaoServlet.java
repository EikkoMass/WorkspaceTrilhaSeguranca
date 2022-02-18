package LoginServlet;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.Base64;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ArmaDadosUsuario.ArmazenaDadosUsuario;
import AutenticacaoJdbcDAO.JDBCAutenticaDAO;
import Conexao.Conexao;

public class AutenticacaoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			final String salt = "salted";
			byte[] decodedPassword = Base64.getDecoder().decode(request.getParameter("senha"));
			
			
			String password = new String(decodedPassword) + salt;
			String senmd5 = "";
			MessageDigest md = null;
			try
			{
				md = MessageDigest.getInstance("MD5");
			}
			catch(NoSuchAlgorithmException ex)
			{
				ex.printStackTrace();
			}
			
			BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
			
			senmd5 = hash.toString(16);
			
			String username = request.getParameter("nome");
			
			
			ArmazenaDadosUsuario dadosUsuario = new ArmazenaDadosUsuario(username, senmd5);

			Conexao conexao = new Conexao();
			Connection connection = (Connection) conexao.abrirConexao();
			JDBCAutenticaDAO jdbc = new JDBCAutenticaDAO(connection);
			boolean retorno = jdbc.consultar(dadosUsuario);

			
			conexao.fecharConexao();
			
			HttpSession session = request.getSession();
			
			String loginCookie = "";
			String sessionCookie = "";
			String redirection = "erro.html";
			
			if(retorno)
			{
				session.setAttribute("login", username);
				loginCookie = username;
				sessionCookie = request.getSession().getId();
				redirection = "Acesso/principal.html";
				
			}
			else
			{
				session.removeAttribute("login");
			}
							
			response.addCookie(new Cookie("login", loginCookie));
			response.addCookie(new Cookie("sessionId", sessionCookie));
			response.sendRedirect(redirection);				
			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		process(request, response);
	}

}
