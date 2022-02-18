package Filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filter)
			throws IOException, ServletException {

		HttpServletResponse httpRes = ((HttpServletResponse) res);
		
		httpRes.setHeader("cache-control", "no-cache");
		
		String context = req.getServletContext().getContextPath();
		
		try {
			HttpSession session = ((HttpServletRequest) req).getSession();
			String usuario = null;

			if (session != null) {
				usuario = (String) session.getAttribute("login");
			}

			if (usuario == null) {
				httpRes.sendRedirect(context + "/erro.html");
			}
			else
			{
				filter.doFilter(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
