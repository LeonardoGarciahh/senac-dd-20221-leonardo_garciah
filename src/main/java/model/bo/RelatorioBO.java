package model.bo;

import java.time.LocalDate;
import java.util.ArrayList;

import model.dao.RelatorioDAO;
import model.dto.RelatorioDTO;

public class RelatorioBO {

	public ArrayList<RelatorioDTO> gerarRelatorioChamadosCadastradosBO(RelatorioDTO relatorioDTO) {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		return relatorioDAO.gerarRelatorioChamadosCadastradosDAO(relatorioDTO);
	}

	public RelatorioDTO gerarRelatorioChamadosTotalAtendidosBO(RelatorioDTO relatorioDTO) {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		return relatorioDAO.gerarRelatorioChamadosTotalAtendidosDAO(relatorioDTO);
	}

	public ArrayList<RelatorioDTO> gerarPDFChamadosAtendidosPorTecnicoBO(RelatorioDTO relatorioDTO) {
		RelatorioDAO relatorioDAO = new RelatorioDAO();
		return relatorioDAO.gerarPDFChamadosAtendidosPorTecnicoDAO(relatorioDTO);
	}

}
