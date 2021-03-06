package aplicacion.controlador;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.ListasEJB;
import aplicacion.modelo.ejb.ProductosEJB;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.pojo.Usuario;

@WebServlet("/Lista")
public class Lista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	ProductosEJB productosEJB;

	@EJB
	ListasEJB listasEJB;

	@Override
	/****
	 * Método GET que muestra la página Lista de productos.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		if (usuario != null) {
			RequestDispatcher rs = getServletContext().getRequestDispatcher("/PaginaLista.jsp");
			request.setAttribute("usuario", usuario);
			request.setAttribute("nombreLista", listasEJB.getNombreLista(usuario.getId()));
			request.setAttribute("productos", productosEJB.productosPorUserId(usuario));
			request.setAttribute("productosSinPrecio", productosEJB.productosSinPrecioPorUserId(usuario));
			try {
				rs.forward(request, response);
			} catch (ServletException | IOException e) {
				log.getLoggerLista().error("Se ha producido un error en GET Lista: ", e);
			}
		} else {
			response.sendRedirect("Principal");
		}
	}

}
