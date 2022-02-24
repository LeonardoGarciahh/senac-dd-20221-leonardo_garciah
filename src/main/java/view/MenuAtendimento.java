package view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ChamadoController;
import model.vo.ChamadoVO;
import model.vo.UsuarioVO;

public class MenuAtendimento {
	Scanner teclado = new Scanner(System.in);
	public static final int OPCAO_ATENDIMENTO_LISTAR_CHAMADO = 1;
	public static final int OPCAO_ATENDIMENTO_ATENDER_CHAMADO = 2;
	public static final int OPCAO_ATENDIMENTO_SAIR = 3;

	public static final int OPCAO_LISTAR_CHAMADOS_ABERTOS = 1;
	public static final int OPCAO_LISTAR_CHAMADOS_FECHADOS = 2;
	public static final int OPCAO_LISTAR_CHAMADOS_SAIR = 3;

	public static final int OPCAO_BUSCA_ATENDIMENTO_ABERTO = 3;
	public static final int OPCAO_BUSCA_ATENDIMENTO_FECHADO = 4;

	public void apresentarMenuAtendimento(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcao(usuarioVO);
		while (opcao != OPCAO_ATENDIMENTO_SAIR) {
			switch (opcao) {
			case OPCAO_ATENDIMENTO_LISTAR_CHAMADO:
				this.listarChamados(usuarioVO);
				break;
			case OPCAO_ATENDIMENTO_ATENDER_CHAMADO:
				this.atenderChamado(usuarioVO);
				break;
			default:
				System.out.println("Opção invalida!");
			}
			opcao = this.apresentarOpcao(usuarioVO);
		}
	}

	private void listarChamados(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpçõesListar();
		while (opcao != OPCAO_LISTAR_CHAMADOS_SAIR) {
			switch (opcao) {
			case OPCAO_LISTAR_CHAMADOS_ABERTOS:
				this.listarChamadosAbertos(usuarioVO);
				break;
			case OPCAO_LISTAR_CHAMADOS_FECHADOS:
				this.atenderChamadosFechados(usuarioVO);
				break;
			default:
				System.out.println("Opção invalida!");
			}
			opcao = this.apresentarOpçõesListar();
		}
	}

	private void atenderChamadosFechados(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		ArrayList<ChamadoVO> chamados = chamadoController.buscarChamadosController(usuarioVO,
				OPCAO_BUSCA_ATENDIMENTO_FECHADO);
		System.out.println("Resultado da busca por chamados fechados\n");
		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s", "ID CHAMADO", "ID USUARIO",
				"ID TECNICO", "TITULO", "DESCRICAO", "DATA-ABERTURA", "SOLUÇÃO", "DATA-FECHAMENTO");
		System.out.println();
		for (int c = 0; c < chamados.size(); c++) {
			chamados.get(c).imprimir();
		}
		if (chamados.size() == 0) {
			System.out.println("Nenhum chamado encontrado!");
		}

	}

	private void listarChamadosAbertos(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		ArrayList<ChamadoVO> chamados = chamadoController.buscarChamadosController(usuarioVO,
				OPCAO_BUSCA_ATENDIMENTO_ABERTO);
		System.out.println("Resultado da busca por chamados abertos\n");
		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s", "ID CHAMADO", "ID USUARIO",
				"ID TECNICO", "TITULO", "DESCRICAO", "DATA-ABERTURA", "SOLUÇÃO", "DATA-FECHAMENTO");
		System.out.println();
		for (int c = 0; c < chamados.size(); c++) {
			chamados.get(c).imprimir();
		}
		if (chamados.size() == 0) {
			System.out.println("Nenhum chamado encontrado!");
		}

	}

	private int apresentarOpçõesListar() {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Atendimento ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_LISTAR_CHAMADOS_ABERTOS + "-- Listar chamado abertos");
		System.out.println(OPCAO_LISTAR_CHAMADOS_FECHADOS + "-- Listar chamados fechados");
		System.out.println(OPCAO_LISTAR_CHAMADOS_SAIR + "-- Sair");
		return this.validarOpcao();
	}

	private void atenderChamado(UsuarioVO usuarioVO) {

		ChamadoVO chamadoVO = new ChamadoVO();
		System.out.print("\nInsira o id do chamado a ser atendido: ");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		System.out.print("\nInsira a resposta: ");
		chamadoVO.setSOLUCAO(teclado.nextLine());
		chamadoVO.setIDTECNICO(usuarioVO.getIdUsuario());
		chamadoVO.setDATAFECHAMENTO(LocalDate.now());
		if (chamadoVO.getIdChamado() == 0 || chamadoVO.getSOLUCAO().isEmpty()) {
			System.out.println("Os campos codigo de chamado e solução são obrigatorios!");
		} else {
			ChamadoController chamadoController = new ChamadoController();
			chamadoController.atenderChamadoController(chamadoVO);
			if (chamadoVO.getDATAFECHAMENTO() != null) {
				System.out.println("Chamado fechado com sucesso!");
			} else {
				System.out.println("Não foi possivel fechar o chamado!");
			}
		}

	}

	private int apresentarOpcao(UsuarioVO usuarioVO) {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Atendimento ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_ATENDIMENTO_LISTAR_CHAMADO + "-- Listar chamado");
		System.out.println(OPCAO_ATENDIMENTO_ATENDER_CHAMADO + "-- Atender chamado");
		System.out.println(OPCAO_ATENDIMENTO_SAIR + "-- Sair");
		return this.validarOpcao();
	}
	
	//Validar opção
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
