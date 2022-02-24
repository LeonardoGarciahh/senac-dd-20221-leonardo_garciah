package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.RelatorioController;
import model.dto.RelatorioDTO;
import model.pdf.GeradorPDF;
import model.vo.UsuarioVO;

public class MenuRelatorio {
	// Menu relatorio
	public static final int OPCAO_RELATORIO_CHAMADOS_CADASTRADOS = 1;
	public static final int OPCAO_RELATORIO_CHAMADOS_ATENDIDOS = 2;
	public static final int OPCAO_RELATORIO_CHAMADOS_TECNICOS = 3;
	public static final int OPCAO_RELATORIO_SAIR = 4;

	// Opções mostrar resultado
	public static final int OPCAO_RELATORIO_ATENDIDOS_CONSOLE = 1;
	public static final int OPCAO_RELATORIO_ATENDIDOS_PDF = 2;
	public static final int OPCAO_RELATORIO_ATENDIDOS_SAIR = 3;

	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	// Apresenta opções de relatorio
	public void apresentarMenuRelatorio(UsuarioVO usuarioVO) {

		int opcao = this.apresentarOpcao(usuarioVO);
		RelatorioDTO relatorioDTO = null;
		while (opcao != OPCAO_RELATORIO_SAIR) {
			switch (opcao) {
			case OPCAO_RELATORIO_CHAMADOS_CADASTRADOS:// Gerar relatorio chamados cadastrados entre data
				GeradorPDF pdfModel = new GeradorPDF();
				relatorioDTO = new RelatorioDTO();
				relatorioDTO.setDataInicial(validarData("Insira a data inicial (dd/MM/yyyy): "));
				relatorioDTO.setDataFinal(validarData("Insira a data final (dd/MM/yyyy): "));
				int opcaoImpressao = ApresentarOpcoesDeImpressao();
				while (opcaoImpressao != OPCAO_RELATORIO_ATENDIDOS_SAIR) {
					switch (opcaoImpressao) {
					case OPCAO_RELATORIO_ATENDIDOS_CONSOLE:
						this.imprimirChamadosCadastrados(relatorioDTO);
						break;
					case OPCAO_RELATORIO_ATENDIDOS_PDF:

						pdfModel.gerarPDFChamadosCadastrados(relatorioDTO);
						break;
					}
					opcaoImpressao = ApresentarOpcoesDeImpressao();
				}
				break;
			case OPCAO_RELATORIO_CHAMADOS_ATENDIDOS: // Gerar relatorio total chamados e total de chamados atendidos
				pdfModel = new GeradorPDF();
				relatorioDTO = new RelatorioDTO();
				relatorioDTO.setDataInicial(validarData("Insira a data inicial (dd/MM/yyyy): "));
				relatorioDTO.setDataFinal(validarData("Insira a data final (dd/MM/yyyy): "));
				opcaoImpressao = ApresentarOpcoesDeImpressao();
				while (opcaoImpressao != OPCAO_RELATORIO_ATENDIDOS_SAIR) {
					switch (opcaoImpressao) {
					case OPCAO_RELATORIO_ATENDIDOS_CONSOLE:
						this.imprimirTotalChamadosAtendidos(relatorioDTO);
						break;
					case OPCAO_RELATORIO_ATENDIDOS_PDF:

						pdfModel.gerarPDFTotalAtendidos(relatorioDTO);

						break;
					}
					opcaoImpressao = ApresentarOpcoesDeImpressao();
				}
				break;
			case OPCAO_RELATORIO_CHAMADOS_TECNICOS: // Gerar relatorio quantos chamados cada tecnico atendeu
				pdfModel = new GeradorPDF();
				relatorioDTO = new RelatorioDTO();
				opcaoImpressao = ApresentarOpcoesDeImpressao();
				while (opcaoImpressao != OPCAO_RELATORIO_ATENDIDOS_SAIR) {
					switch (opcaoImpressao) {
					case OPCAO_RELATORIO_ATENDIDOS_CONSOLE:
						this.imprimirTotalChamadosPorTecnico(relatorioDTO);
						break;
					case OPCAO_RELATORIO_ATENDIDOS_PDF:
						pdfModel.gerarPDFChamadosAtendidosPorTecnico(relatorioDTO);
						break;
					}
					opcaoImpressao = ApresentarOpcoesDeImpressao();
				}
				break;
			default:
				System.out.println("Opção invalida!");
				break;
			}
			opcao = this.apresentarOpcao(usuarioVO);
		}

	}

	// Imprimir total chamados atendidos por tecnico no console
	private void imprimirTotalChamadosPorTecnico(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> relatorios = relatorioController
				.gerarPDFChamadosAtendidosPorTecnicoController(relatorioDTO);
		System.out.printf("%12s  %18s", "Nome Técnico", "Chamados Atendidos");
		System.out.println();
		for (int c = 0; c < relatorios.size(); c++) {
			relatorios.get(c).imprimir();
		}

	}

	// Imprimir total chamados atendidos no console
	private void imprimirTotalChamadosAtendidos(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		RelatorioDTO relatorio = relatorioController.gerarRelatorioChamadosTotalAtendidosController(relatorioDTO);
		System.out
				.println("\n---- Total chamados atendidos a partir da data " + formatData(relatorioDTO.getDataInicial())
						+ " até " + formatData(relatorioDTO.getDataFinal()) + " ----\n");
		System.out.printf("%14s  %10s", "TOTAL CHAMADOS", "ATENDIDOS");
		System.out.println();
		System.out.printf("%14s  %10s", relatorio.getTotalChamados(), relatorio.getTotalAtendidos() + "\n");

	}

	// Imprimir chamados cadastrados no console
	private void imprimirChamadosCadastrados(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> relatoriosDTO = new ArrayList<RelatorioDTO>();
		relatoriosDTO = relatorioController.gerarRelatorioChamadosCadastradosController(relatorioDTO);
		System.out.println("\n---- Chamados cadastrados da data " + formatData(relatorioDTO.getDataInicial()) + " até "
				+ formatData(relatorioDTO.getDataFinal()) + " ----\n");
		System.out.printf("%10s  %10s  %-20s  %-30s  %-20s", "Id Chamado", "Usuário", "Título", "Descrição",
				"Data Abertura");
		System.out.println();
		for (int i = 0; i < relatoriosDTO.size(); i++) {
			relatoriosDTO.get(i).imprimirString();
		}

	}

	// Opções de relatorios
	private int apresentarOpcao(UsuarioVO usuarioVO) {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Atendimento ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_RELATORIO_CHAMADOS_CADASTRADOS + "-- Chamados cadastrados entre data");
		System.out.println(OPCAO_RELATORIO_CHAMADOS_ATENDIDOS + "-- Total chamados atendidos entre data");
		System.out.println(OPCAO_RELATORIO_CHAMADOS_TECNICOS + "-- Total de atendimento dos tecnicos");
		System.out.println(OPCAO_RELATORIO_SAIR + "-- Sair");
		return this.validarOpcao();
	}

	// Opções de impressão
	public int ApresentarOpcoesDeImpressao() {
		System.out.println(OPCAO_RELATORIO_ATENDIDOS_CONSOLE + "-- Imprimir no console.");
		System.out.println(OPCAO_RELATORIO_ATENDIDOS_PDF + "-- Gerar pdf.");
		System.out.println(OPCAO_RELATORIO_ATENDIDOS_SAIR + "-- Sair.");
		return this.validarOpcao();
	}

	// Validar data
	private LocalDate validarData(String message) {
		Exception error = null;
		LocalDate dataInicial = null;
		do {
			error = null;
			try {
				System.out.print(message);
				String dataInicialString = teclado.nextLine();
				dataInicial = LocalDate.parse(dataInicialString, formaterDate);
			} catch (Exception e) {
				System.out.println("Formato da data invalida!");
				error = e;
			}
		} while (error != null);
		return dataInicial;
	}

	// Formatar data
	private String formatData(LocalDate data) {
		String dataabertura = data.toString().replaceAll("-", "/");
		String[] s = dataabertura.split("/");
		String novaData = s[2] + "/" + s[1] + "/" + s[0];
		return novaData;
	}

	// Validar opção
	private int validarOpcao() {
		Exception error = null;
		Integer opcao = null;
		do {
			error = null;
			try {
				System.out.print("Insira um opção: ");
				opcao = Integer.parseInt(teclado.nextLine());
			} catch (Exception e) {
				System.out.println("Insira o numero da opção!");
				error = e;
			}
		} while (error != null);

		return opcao;
	}

}
