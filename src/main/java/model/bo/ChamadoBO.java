package model.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import model.dao.ChamadoDAO;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class ChamadoBO {

	public ArrayList<ChamadoVO> buscarChamadosBO(UsuarioVO usuarioVO, int opcaoDeBusca) {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		return chamadoDAO.buscarChamadosDAO(usuarioVO, opcaoDeBusca);

	}

	public void cadastrarNovoChamadoBO(UsuarioVO usuarioVO, ChamadoVO chamadoVO) {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		try {
			chamadoDAO.cadastrarNovoChamadoDAO(usuarioVO, chamadoVO);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean atualizarChamadoBO(ChamadoVO chamadoVO) {
		boolean resultado = false;
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		if (chamadoDAO.verificarExistenciaRegistroPorIDChamadoDAO(chamadoVO.getIdChamado())) {
			if (chamadoDAO.verificarFechamentoPorIDChamadoDAO(chamadoVO)) {
				System.out.println("\nChamado ja se encontra fechado na base de dados.");
			} else {
				resultado = chamadoDAO.atualizarChamadoDAO(chamadoVO);
			}
		} else {
			System.out.println("\n Chamado não existe na base de dados!");
		}
		return resultado;
	}

	public boolean excluirChamadoBO(ChamadoVO chamadoVO) {
		boolean resultado = false;
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		if (chamadoDAO.verificarIdUsuarioNoChamado(chamadoVO)) {
			if (chamadoDAO.verificarExistenciaRegistroPorIDChamadoDAO(chamadoVO.getIdChamado())) {
				if (chamadoDAO.verificarFechamentoPorIDChamadoDAO(chamadoVO)) {
					System.out.println("\nChamado ja se encontra fechado na base de dados.");
				} else {
					resultado = chamadoDAO.excluirChamadoDAO(chamadoVO);
				}
			} else {
				System.out.println("\nChamado não existe na base de dados!");
			}
		} else {
			System.out.println("\nVocê não possui um chamado com esse id!!");
		}
		return resultado;
	}

	public void atenderChamadoBO(ChamadoVO chamadoVO) {
		ChamadoDAO chamadoDAO = new ChamadoDAO();
		if (chamadoDAO.verificarFechamentoPorIDChamadoDAO(chamadoVO)) {
			System.out.println("\nChamado ja se encontra fechado na base de dados.");
		} else {
			chamadoDAO.atenderChamadoDAO(chamadoVO);
		}

	}

}
