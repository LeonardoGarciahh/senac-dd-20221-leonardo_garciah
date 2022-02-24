package controller;

import java.util.ArrayList;

import model.bo.RelatorioBO;
import model.dto.RelatorioDTO;

public class RelatorioController {

	public ArrayList<RelatorioDTO> gerarRelatorioChamadosCadastradosController(RelatorioDTO relatorioDTO) {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.gerarRelatorioChamadosCadastradosBO(relatorioDTO);

	}

	public RelatorioDTO gerarRelatorioChamadosTotalAtendidosController(RelatorioDTO relatorioDTO) {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.gerarRelatorioChamadosTotalAtendidosBO(relatorioDTO);
	}

	public ArrayList<RelatorioDTO> gerarPDFChamadosAtendidosPorTecnicoController(RelatorioDTO relatorioDTO) {
		RelatorioBO relatorioBO = new RelatorioBO();
		return relatorioBO.gerarPDFChamadosAtendidosPorTecnicoBO(relatorioDTO);
	}

}
