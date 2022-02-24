package controller;

import java.sql.SQLException;
import java.util.ArrayList;

import model.bo.UsuarioBO;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class UsuarioController {

	public UsuarioVO realizarLoginController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.realizarLoginBO(usuarioVO);
	}

	public UsuarioVO cadastrarUsuarioController(UsuarioVO usuarioVO) throws SQLException {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.cadastrarUsuarioBO(usuarioVO);
	}

	public ArrayList<TipoUsuarioVO> consultarTiposUsuariosController() {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.consultarTiposUsuariosBO();
	}

	public ArrayList<UsuarioVO> consultarUsuarioController(int id) {
		UsuarioBO usuarioBO = new UsuarioBO();
		ArrayList<UsuarioVO> usuariosVO = new ArrayList<UsuarioVO>();
		usuariosVO = usuarioBO.consultarUsuarioBO(id);
		return usuariosVO;

	}

	public boolean excluirUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.excluirUsuarioBO(usuarioVO);

	}

	public boolean atualizarUsuarioController(UsuarioVO usuarioVO) {
		UsuarioBO usuarioBO = new UsuarioBO();
		return usuarioBO.atualizarUsuarioBO(usuarioVO);
	}

}
