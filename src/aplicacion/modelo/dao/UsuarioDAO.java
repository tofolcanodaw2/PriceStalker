package aplicacion.modelo.dao;

import org.apache.ibatis.session.SqlSession;

import aplicacion.modelo.dao.mappers.UsuariosMapper;
import aplicacion.modelo.pojo.Usuario;

public class UsuarioDAO {

	public static Usuario loginUsuario(String correo, String password) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		Usuario usuario = null;
		try {
			UsuariosMapper usuariosMapper = sqlSession.getMapper(UsuariosMapper.class);
			if (correo != null && password != null) {
				usuario = usuariosMapper.loginUsuario(correo, password);
			}
			return usuario;
		} finally {
			sqlSession.close();
		}
	}

	public static boolean existeUsuario(String correo) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		Usuario usuario = null;
		try {
			if (correo != null) {
				UsuariosMapper usuariosMapper = sqlSession.getMapper(UsuariosMapper.class);
				usuario = usuariosMapper.existeUsuario(correo);
			}
		} finally {
			sqlSession.close();
		}
		return usuario != null;
	}

	public static void registrarUsuario(String nombre, String correo, String password) {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			if (nombre != null & correo != null & password != null) {
				UsuariosMapper usuariosMapper = sqlSession.getMapper(UsuariosMapper.class);
				usuariosMapper.registrarUsuario(nombre, correo, password);
			}
		} finally {
			sqlSession.close();
		}
	}
}
