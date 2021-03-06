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
import aplicacion.modelo.ejb.ProductosEJB;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.pojo.ProductoSinPrecio;
import aplicacion.modelo.pojo.Usuario;

@WebServlet("/Eliminar")
public class Eliminar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	ProductosEJB productosEJB;

	@Override
	/****
	 * Método GET que obtiene el usuario de la sesión y el ID de un producto por
	 * parámetro.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		String idContenido = request.getParameter("producto");
		/**
		 * Comprueba si ha conseguido los datos necesarios, si no redirige a Principal.
		 */
		if (usuario != null & idContenido != null) {
			ProductoSinPrecio producto = productosEJB.getProductoAEliminar(idContenido, usuario.getId());
			/**
			 * Si el producto existe y es accesible para el usuario muestra la página
			 * Eliminar. Si no, redirige a Principal.
			 */
			if (producto != null) {
				RequestDispatcher rs = getServletContext().getRequestDispatcher("/PaginaEliminar.jsp");
				request.setAttribute("usuario", usuario);
				request.setAttribute("producto", producto);
				try {
					rs.forward(request, response);
				} catch (ServletException | IOException e) {
					log.getLoggerEliminar().error("Se ha producido un error en GET Eliminar: ", e);
				}
			} else {
				response.sendRedirect("Principal");
			}
		} else {
			response.sendRedirect("Principal");
		}
	}

	@Override
	/****
	 * Método POST que elimina el producto de una lista.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		String idContenido = request.getParameter("idContenido");
		if (usuario != null & idContenido != null) {
			productosEJB.eliminarProductoDeLaLista(idContenido, usuario.getId());
			response.sendRedirect("Lista");
		} else {
			response.sendRedirect("Principal");
		}
	}

}
