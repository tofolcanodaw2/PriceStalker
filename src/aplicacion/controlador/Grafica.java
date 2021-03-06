package aplicacion.controlador;

import java.io.IOException;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import aplicacion.modelo.LogSingleton;
import aplicacion.modelo.ejb.GraficasEJB;
import aplicacion.modelo.ejb.ListasEJB;
import aplicacion.modelo.ejb.ProductosEJB;
import aplicacion.modelo.ejb.SesionesEJB;
import aplicacion.modelo.pojo.Precio;
import aplicacion.modelo.pojo.Producto;
import aplicacion.modelo.pojo.Usuario;

@WebServlet("/Grafica")
public class Grafica extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB
	SesionesEJB sesionesEJB;

	@EJB
	GraficasEJB graficasEJB;

	@EJB
	ListasEJB listasEJB;

	@EJB
	ProductosEJB productosEJB;

	@Override
	/****
	 * Método GET que muestra la página Gráfica.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);
		LogSingleton log = LogSingleton.getInstance();
		Usuario usuario = sesionesEJB.usuarioLogeado(session);
		String idContenido = request.getParameter("producto");
		if (usuario != null && idContenido != null) {
			boolean productoDeSuLista = listasEJB.productoEstaEnSuLista(idContenido, usuario.getId());
			if (productoDeSuLista) {
				ArrayList<Precio> precios = graficasEJB.obtenerPreciosDeProductoPorIdContenido(idContenido);
				Producto producto = productosEJB.obtenerProductoPorIdContenido(idContenido);
				if (precios != null & producto != null) {
					String labels = graficasEJB.fechasEnPreciosAJson(precios);
					String data = graficasEJB.costesEnPreciosAJson(precios);
					RequestDispatcher rs = getServletContext().getRequestDispatcher("/PaginaGrafica.jsp");
					request.setAttribute("usuario", usuario);
					request.setAttribute("labels", labels);
					request.setAttribute("data", data);
					request.setAttribute("producto", producto);
					try {
						rs.forward(request, response);
					} catch (ServletException | IOException e) {
						log.getLoggerGrafica().error("Se ha producido un error en GET Grafica: ", e);
					}
				} else {
					String nombreDelProducto = productosEJB.getNombrePorIdContenido(idContenido);
					if (nombreDelProducto != null) {
						if (nombreDelProducto.equals("Producto sin nombre")) {
							RequestDispatcher rs = getServletContext().getRequestDispatcher("/PaginaGrafica.jsp");
							request.setAttribute("usuario", usuario);
							request.setAttribute("error",
									"Todavía no hemos obtenido ningún dato de este producto, vuelve a intentarlo mañana.");
							try {
								rs.forward(request, response);
							} catch (ServletException | IOException e) {
								log.getLoggerGrafica().error("Se ha producido un error en GET Grafica: ", e);
							}
						} else {
							response.sendRedirect("Principal");
						}
					} else {
						response.sendRedirect("Principal");
					}
				}
			} else {
				response.sendRedirect("Principal");
			}
		} else {
			response.sendRedirect("Principal");
		}
	}

}
