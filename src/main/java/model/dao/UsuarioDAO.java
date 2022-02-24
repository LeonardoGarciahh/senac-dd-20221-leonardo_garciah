package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class UsuarioDAO {

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public UsuarioVO realizarLoginDao(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;

		String query = "SELECT U.IDUSUARIO,TIPO.DESCRICAO,U.NOME, U.CPF," + "U.EMAIL,U.DATACADASTRO,U.DATAEXPIRACAO "
				+ "FROM USUARIO U, TIPOUSUARIO TIPO " + "WHERE U.LOGIN = '" + usuarioVO.getLogin() + "' "
				+ "AND u.senha = '" + usuarioVO.getSenha() + "' " + "AND u.IDTIPOUSUARIO = tipo.IDTIPOUSUARIO";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				usuarioVO.setIdUsuario(Integer.parseInt(resultado.getString(1)));
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.valueOf(resultado.getString(2)));
				usuarioVO.setNome(resultado.getString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setDataCadastro(LocalDate.parse(resultado.getString(6), formaterDate));
				if (resultado.getString(7) != null) {
					usuarioVO.setDataExpiracao(LocalDate.parse(resultado.getString(7), formaterDate));
				}
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que realiza o login.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return usuarioVO;
	}

	public UsuarioVO cadastrarUsuarioDAO(UsuarioVO usuarioVO) throws SQLException {
		Connection conn = Banco.getConnection();
		String query = "INSERT INTO USUARIO (IDTIPOUSUARIO,NOME,CPF,EMAIL,DATACADASTRO,LOGIN,SENHA) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement pstm = Banco.getPreparedStatement(conn, query);
		pstm.setInt(1, usuarioVO.getTipoUsuarioVO().getValor());
		pstm.setString(2, usuarioVO.getNome());
		pstm.setString(3, usuarioVO.getCpf());
		pstm.setString(4, usuarioVO.getEmail());
		pstm.setObject(5, usuarioVO.getDataCadastro());
		pstm.setString(6, usuarioVO.getLogin());
		pstm.setString(7, usuarioVO.getSenha());

		try {
			pstm.execute();
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de cadastro de usuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closePreparedStatement(pstm);
			Banco.closeConnection(conn);
		}

		return usuarioVO;

	}

	public ArrayList<TipoUsuarioVO> consultarTiposUsuariosDAO() {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<TipoUsuarioVO> tipoUsuario = new ArrayList<TipoUsuarioVO>();

		String query = "SELECT descricao from tipousuario";
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				TipoUsuarioVO tipoUsuarioVO = TipoUsuarioVO.valueOf(resultado.getString(1));

				tipoUsuario.add(tipoUsuarioVO);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que busca os Tipo de Usuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return tipoUsuario;

	}

	public boolean verificarExistenciaRegistroPorCpf(UsuarioVO usuarioVO) throws SQLException {
		Connection conn = Banco.getConnection();
		ResultSet resultado = null;
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "SELECT cpf from usuario where cpf = '" + usuarioVO.getCpf() + "'";

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}

		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verificar usuario existente.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return retorno;
	}

	public ArrayList<UsuarioVO> consultarUsuarioDAO(int id) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query;
		ArrayList<UsuarioVO> usuariosVO = new ArrayList<UsuarioVO>();
		if (id > 0) {
			query = "SELECT * FROM USUARIO WHERE IDUSUARIO = " + id;
		} else {
			query = "SELECT * FROM USUARIO";
		}

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				UsuarioVO usuarioVO = new UsuarioVO();
				usuarioVO.setIdUsuario(resultado.getInt(1));
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(resultado.getInt(2)));
				usuarioVO.setNome(resultado.getNString(3));
				usuarioVO.setCpf(resultado.getString(4));
				usuarioVO.setEmail(resultado.getString(5));
				usuarioVO.setDataCadastro(LocalDate.parse(resultado.getString(6), formaterDate));
				if (resultado.getString(7) != null) {
					usuarioVO.setDataExpiracao(LocalDate.parse(resultado.getString(7), formaterDate));
				}
				usuarioVO.setLogin(resultado.getString(8));
				usuarioVO.setSenha(resultado.getString(9));
				usuariosVO.add(usuarioVO);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que busca os usuarios.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}

		return usuariosVO;
	}

	public boolean verificarExistenciaRegistroPorIDUsuarioDAO(int idUsuario) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT IDUSUARIO FROM USUARIO WHERE IDUSUARIO =" + idUsuario;

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verifica a existencia de usuario por idusuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarDesligamentoPorIDUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT DATAEXPIRACAO FROM USUARIO WHERE IDUSUARIO =" + usuarioVO.getIdUsuario()
				+ " AND dataexpiracao is not null";

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verifica o desligamento de usuario por idusuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE USUARIO SET DATAEXPIRACAO = '" + usuarioVO.getDataExpiracao() + "' WHERE IDUSUARIO = "
				+ usuarioVO.getIdUsuario();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que expira o usuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean atualizarUsuarioDAO(UsuarioVO usuarioVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE usuario SET idtipousuario = " + usuarioVO.getTipoUsuarioVO().getValor() + ", nome = '"
				+ usuarioVO.getNome() + "', cpf = '" + usuarioVO.getCpf() + "', email = '" + usuarioVO.getEmail()
				+ "', DATACADASTRO = '" + usuarioVO.getDataCadastro() + "', login = '" + usuarioVO.getLogin()
				+ "', senha = '" + usuarioVO.getSenha() + "' WHERE IDUSUARIO = " + usuarioVO.getIdUsuario();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de atualização do usuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}
}
