package model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class ChamadoDAO {

	public static final int OPCAO_BUSCA_ABERTO = 1;
	public static final int OPCAO_BUSCA_FECHADO = 2;
	public static final int OPCAO_BUSCA_ATENDIMENTO_ABERTO = 3;
	public static final int OPCAO_BUSCA_ATENDIMENTO_FECHADO = 4;

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public ArrayList<ChamadoVO> buscarChamadosDAO(UsuarioVO usuarioVO, int opcaoDeBusca) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String queryValidacao = "";
		String query = "";
		ArrayList<ChamadoVO> chamados = new ArrayList<ChamadoVO>();
		switch (opcaoDeBusca) {
		case OPCAO_BUSCA_ABERTO:
			queryValidacao = " AND C.DATAFECHAMENTO IS NULL";
			break;
		case OPCAO_BUSCA_FECHADO:
			queryValidacao = " AND C.DATAFECHAMENTO IS NOT NULL";
			break;
		}

		if (opcaoDeBusca != OPCAO_BUSCA_ATENDIMENTO_ABERTO && opcaoDeBusca != OPCAO_BUSCA_ATENDIMENTO_FECHADO) {
			query = "SELECT C.IDCHAMADO,C.IDUSUARIO,C.IDTECNICO, C.TITULO,"
					+ "C.DESCRICAO,C.DATAABERTURA,C.SOLUCAO,C.DATAFECHAMENTO " + "FROM CHAMADOS C,USUARIO U "
					+ "WHERE C.IDUSUARIO = U.IDUSUARIO AND C.IDUSUARIO = " + usuarioVO.getIdUsuario() + queryValidacao;
		} else {
			if (opcaoDeBusca == OPCAO_BUSCA_ATENDIMENTO_ABERTO) {

				query = "SELECT C.IDCHAMADO,C.IDUSUARIO,C.IDTECNICO, C.TITULO,"
						+ "C.DESCRICAO,C.DATAABERTURA,C.SOLUCAO,C.DATAFECHAMENTO " + "FROM CHAMADOS C "
						+ "WHERE C.DATAFECHAMENTO IS NULL";
			} else if (opcaoDeBusca == OPCAO_BUSCA_ATENDIMENTO_FECHADO) {
				query = "SELECT C.IDCHAMADO,C.IDUSUARIO,C.IDTECNICO, C.TITULO,"
						+ "C.DESCRICAO,C.DATAABERTURA,C.SOLUCAO,C.DATAFECHAMENTO " + "FROM CHAMADOS C "
						+ "WHERE C.DATAFECHAMENTO IS NOT NULL";
			}
		}

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				ChamadoVO chamadoVO = new ChamadoVO();
				chamadoVO.setIdChamado(Integer.parseInt(resultado.getString(1)));
				chamadoVO.setIDUSUARIO(Integer.parseInt(resultado.getString(2)));
				if (resultado.getString(3) != null) {
					chamadoVO.setIDTECNICO(Integer.parseInt(resultado.getString(3)));
				}
				chamadoVO.setTitulo(resultado.getString(4));
				chamadoVO.setDescricao(resultado.getString(5));
				chamadoVO.setDATAABERTURA(LocalDate.parse(resultado.getString(6), formaterDate));
				if (resultado.getString(7) != null) {
					chamadoVO.setSOLUCAO(resultado.getString(7));
				} else {
					chamadoVO.setSOLUCAO("");
				}
				if (resultado.getString(8) != null) {
					chamadoVO.setDATAFECHAMENTO(LocalDate.parse(resultado.getString(8), formaterDate));
				}
				chamados.add(chamadoVO);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que busca o chamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}

		return chamados;
	}

	public void cadastrarNovoChamadoDAO(UsuarioVO usuarioVO, ChamadoVO chamadoVO) throws SQLException {
		Connection conn = Banco.getConnection();
		// Statement stmt = Banco.getStatement(conn);
		String query = "INSERT INTO CHAMADOS (IDUSUARIO,TITULO,DESCRICAO,DATAABERTURA) VALUES(?,?,?,?)";
		PreparedStatement pstm = Banco.getPreparedStatement(conn, query);
		pstm.setInt(1, usuarioVO.getIdUsuario());
		pstm.setString(2, chamadoVO.getTitulo());
		pstm.setString(3, chamadoVO.getDescricao());
		Date date = Date.valueOf(chamadoVO.getDATAABERTURA());
		pstm.setDate(4, date);

		try {
			pstm.execute();
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de cadastro de chamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			// Banco.closeStatement(stmt);
			Banco.closePreparedStatement(pstm);
			Banco.closeConnection(conn);
			System.out.println("Chamado cadastrado!");
		}

	}

	public boolean verificarExistenciaRegistroPorIDChamadoDAO(int idChamado) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT IDCHAMADO FROM CHAMADOS WHERE IDCHAMADO =" + idChamado;

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verifica a existencia de chamado por idchamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarFechamentoPorIDChamadoDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;

		String query = "SELECT DATAFECHAMENTO FROM CHAMADOS WHERE IDCHAMADO =" + chamadoVO.getIdChamado()
				+ " AND DATAFECHAMENTO is not null";

		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verifica o fechamento do chamado por idchamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean atualizarChamadoDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "UPDATE chamados set titulo = '" + chamadoVO.getTitulo() + "', descricao = '"
				+ chamadoVO.getDescricao() + "', dataabertura = '" + chamadoVO.getDATAABERTURA()
				+ "' WHERE IDCHAMADO = " + chamadoVO.getIdChamado();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query de atualização do chamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean excluirChamadoDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		boolean retorno = false;
		String query = "DELETE FROM CHAMADOS WHERE IDCHAMADO = " + chamadoVO.getIdChamado() + " AND IDUSUARIO = "
				+ chamadoVO.getIDUSUARIO();
		try {
			if (stmt.executeUpdate(query) == 1) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que deleta o usuario.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public boolean verificarIdUsuarioNoChamado(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		boolean retorno = false;
		String query = "SELECT IDCHAMADO FROM CHAMADOS WHERE IDUSUARIO = " + chamadoVO.getIDUSUARIO()
				+ " AND IDCHAMADO = " + chamadoVO.getIdChamado();
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				retorno = true;
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que verifica idusuario no chamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return retorno;
	}

	public void atenderChamadoDAO(ChamadoVO chamadoVO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		Date date = Date.valueOf(chamadoVO.getDATAFECHAMENTO());
		String query = "UPDATE CHAMADOS SET IDTECNICO = '" + chamadoVO.getIDTECNICO() + "', SOLUCAO = '"
				+ chamadoVO.getSOLUCAO() + "', DATAFECHAMENTO = '" + date + "' WHERE IDCHAMADO = "
				+ chamadoVO.getIdChamado();
		try {
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que realiza o atendimento do chamado.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
	}

}
