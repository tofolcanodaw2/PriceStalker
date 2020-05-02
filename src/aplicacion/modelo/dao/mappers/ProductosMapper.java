package aplicacion.modelo.dao.mappers;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import aplicacion.modelo.pojo.Producto;
import aplicacion.modelo.pojo.ProductoSinPrecio;

public interface ProductosMapper {

	public ArrayList<Producto> getProductosPorUserId(@Param("id") Integer id);

	public Producto getProductoPorIdContenido(@Param("idContenido") String idContenido);

	public Integer existeProducto(@Param("enlace") String enlace);

	public void insertProducto(@Param("nombre") String nombre, @Param("enlace") String enlace,
			@Param("imgLink") String imgLink);

	public ArrayList<ProductoSinPrecio> productosSinPrecioPorUserId(@Param("idUsuario") Integer idUsuario);

	public String getNombrePorIdContenido(@Param("contenidoId") int contenidoId);

	public ProductoSinPrecio getProductoAEliminar(@Param("contenidoId") int contenidoId);

	public void eliminarProductoDeLaLista(@Param("contenidoId") int contenidoId, @Param("idUsuario") Integer idUsuario);

}
