package model.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import model.dao.UsuarioDAO;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class UsuarioBO {

	public UsuarioVO realizarLoginBO(UsuarioVO usuarioVO) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.realizarLoginDao(usuarioVO);
	}

	public UsuarioVO cadastrarUsuarioBO(UsuarioVO usuarioVO) throws SQLException {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarExistenciaRegistroPorCpf(usuarioVO)) {
			System.out.println("Usuario ja existente!");
		} else {
			usuarioVO = usuarioDAO.cadastrarUsuarioDAO(usuarioVO);
		}

		return usuarioVO;
	}

	public ArrayList<TipoUsuarioVO> consultarTiposUsuariosBO() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		return usuarioDAO.consultarTiposUsuariosDAO();
	}

	public ArrayList<UsuarioVO> consultarUsuarioBO(int id) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		ArrayList<UsuarioVO> usuariosVO = new ArrayList<UsuarioVO>();
		usuariosVO = usuarioDAO.consultarUsuarioDAO(id);
		return usuariosVO;
	}

	public boolean excluirUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarExistenciaRegistroPorIDUsuarioDAO(usuarioVO.getIdUsuario())) {
			if (usuarioDAO.verificarDesligamentoPorIDUsuarioDAO(usuarioVO)) {
				System.out.println("\nUsuario ja se encontra desligado na base de dados.");
			} else {
				resultado = usuarioDAO.excluirUsuarioDAO(usuarioVO);
			}
		} else {
			System.out.println("\nUsuario não existe na base de dados!");
		}
		return resultado;
	}

	public boolean atualizarUsuarioBO(UsuarioVO usuarioVO) {
		boolean resultado = false;
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		if (usuarioDAO.verificarExistenciaRegistroPorIDUsuarioDAO(usuarioVO.getIdUsuario())) {
			if (usuarioDAO.verificarDesligamentoPorIDUsuarioDAO(usuarioVO)) {
				System.out.println("\nUsuario ja se encontra desligado na base de dados.");
			} else {
				resultado = usuarioDAO.atualizarUsuarioDAO(usuarioVO);
			}
		} else {
			System.out.println("\nUsuario não existe na base de dados!");
		}
		return resultado;
	}

}
