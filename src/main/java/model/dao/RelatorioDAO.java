package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.dto.RelatorioDTO;

public class RelatorioDAO {

	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

	public ArrayList<RelatorioDTO> gerarRelatorioChamadosCadastradosDAO(RelatorioDTO relatorioDTO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "SELECT CHAMADOS.IDCHAMADO,USUARIO.NOME,CHAMADOS.TITULO,CHAMADOS.DATAABERTURA, CHAMADOS.DESCRICAO, CHAMADOS.DATAFECHAMENTO "
				+ "FROM " + "CHAMADOS " + "INNER JOIN USUARIO ON" + " USUARIO.IDUSUARIO = CHAMADOS.IDUSUARIO"
				+ " WHERE CHAMADOS.IDUSUARIO = USUARIO.IDUSUARIO" + " AND DATAABERTURA BETWEEN '"
				+ relatorioDTO.getDataInicial() + "' and '" + relatorioDTO.getDataFinal() + "'";
		ArrayList<RelatorioDTO> relatoriosDTO = new ArrayList<RelatorioDTO>();
		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				RelatorioDTO relatorio = new RelatorioDTO();
				relatorio.setIdChamado(resultado.getInt(1));
				relatorio.setUsuarioNome(resultado.getString(2));
				relatorio.setTitulo(resultado.getString(3));

				String dataabertura = resultado.getString(4).replaceAll("-", "/");
				String[] s = dataabertura.split("/");
				String novaData = s[2] + "/" + s[1] + "/" + s[0];
				relatorio.setDataabertura(novaData);

				relatorio.setDescricao(resultado.getString(5));
				if (resultado.getString(6) != null) {
					relatorio.setSolucionado(true);
				}
				relatoriosDTO.add(relatorio);
			}

		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que gera o relatorio de chamados abertos no ultimos 15 dias.");
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return relatoriosDTO;
	}

	public RelatorioDTO gerarRelatorioChamadosTotalAtendidosDAO(RelatorioDTO relatorioDTO) {
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "SELECT COUNT(IDCHAMADO), COUNT(DATAFECHAMENTO) FROM CHAMADOS WHERE DATAABERTURA BETWEEN '"
				+ relatorioDTO.getDataInicial() + "' and '" + relatorioDTO.getDataFinal() + "'";
		try {
			resultado = stmt.executeQuery(query);
			if (resultado.next()) {
				relatorioDTO.setTotalChamados(resultado.getInt(1));
				relatorioDTO.setTotalAtendidos(resultado.getInt(2));
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que contabiliza o total de chamados e chamados atendidos.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return relatorioDTO;
	}

	public ArrayList<RelatorioDTO> gerarPDFChamadosAtendidosPorTecnicoDAO(RelatorioDTO relatorioDTO) {
		ArrayList<RelatorioDTO> relatoriosDTO = new ArrayList<RelatorioDTO>();
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		String query = "select " + "usuario.idusuario, " + "usuario.nome, "
				+ "(SELECT COUNT(IDCHAMADO) FROM CHAMADOS) AS TOTALCHAMADO, "
				+ "count(chamados.idtecnico) AS CHAMADOSTECNICO " + "from " + "usuario " + "left join chamados on "
				+ "chamados.idtecnico = usuario.idusuario " + "where " + "usuario.idtipousuario = 2 "
				+ "GROUP BY " + "usuario.idusuario," + "usuario.nome";

		try {
			resultado = stmt.executeQuery(query);
			while (resultado.next()) {
				RelatorioDTO relatorio = new RelatorioDTO();
				relatorio.setTecnicoNome(resultado.getString(2));
				relatorio.setTotalAtendidos(resultado.getInt(4));
				relatoriosDTO.add(relatorio);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao executar a query que contabiliza o total de chamados e chamados atendidos.");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);

		}
		return relatoriosDTO;
	}

}
