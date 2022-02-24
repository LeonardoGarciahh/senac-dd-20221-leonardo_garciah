package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ChamadoController;
import controller.UsuarioController;
import model.vo.ChamadoVO;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class MenuChamado {
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	Scanner teclado = new Scanner(System.in);
	public static final int OPCAO_CHAMADO_CADASTRAR = 1;
	public static final int OPCAO_CHAMADO_ALTERAR = 2;
	public static final int OPCAO_CHAMADO_VERCHAMADO = 3;
	public static final int OPCAO_CHAMADO_DELETAR = 4;
	public static final int OPCAO_CHAMADO_SAIR = 5;
	private static final int OPCAO_MENU_BUSCA_TODOS = 1;
	private static final int OPCAO_MENU_BUSCA_ABERTO = 2;
	private static final int OPCAO_MENU_BUSCA_FECHADO = 3;
	private static final int OPCAO_MENU_BUSCA_VOLTAR = 4;

	public static final int OPCAO_BUSCA_TODOS = 0;
	public static final int OPCAO_BUSCA_ABERTO = 1;
	public static final int OPCAO_BUSCA_FECHADO = 2;
	
	public void apresentarMenuChamado(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		int opcao = apresentarOpcaoChamado(usuarioVO);
		while (opcao != 5) {
			switch (opcao) {
			case OPCAO_CHAMADO_CADASTRAR:
				this.cadastrarChamado(usuarioVO);

				break;
			case OPCAO_CHAMADO_ALTERAR:
				this.buscarChamados(usuarioVO);
				this.alterarChamado();
				break;
			case OPCAO_CHAMADO_VERCHAMADO:
				this.buscarChamados(usuarioVO);

				break;
			case OPCAO_CHAMADO_DELETAR:
				this.deletarChamado(usuarioVO);
				break;
			default: {
				System.out.println("Opção invalida!");
			}
			}
			opcao = apresentarOpcaoChamado(usuarioVO);
		}
	}

	private void deletarChamado(UsuarioVO usuarioVO) {
		System.out.println("\n---- Deletar chamado ----");
		this.buscarChamados(usuarioVO);

		ChamadoVO chamadoVO = new ChamadoVO();
		System.out.print("\nDigite o ID do usuário que deseja excluir:");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		chamadoVO.setIDUSUARIO(usuarioVO.getIdUsuario());
		ChamadoController chamadoController = new ChamadoController();
		boolean resultado = chamadoController.excluirChamadoController(chamadoVO);
		if (resultado) {
			System.out.println("Chamado deletado!");
		}
	}

	private void alterarChamado() {
		System.out.println("\n---- Alterar chamado----");
		ChamadoVO chamadoVO = new ChamadoVO();
		System.out.println("Insira o id do chamado: ");
		chamadoVO.setIdChamado(Integer.parseInt(teclado.nextLine()));
		System.out.print("Titulo: ");
		chamadoVO.setTitulo(teclado.nextLine());
		System.out.print("\nDescrição: ");
		chamadoVO.setDescricao(teclado.nextLine());
		System.out.print("\nData de abertura: ");
		chamadoVO.setDATAABERTURA(LocalDate.parse(teclado.nextLine(), formaterDate));

		if (validarCamposChamado(chamadoVO)) {
			ChamadoController chamadoController = new ChamadoController();
			boolean resultado = chamadoController.atualizarChamadoController(chamadoVO);
			if (resultado) {
				System.out.println("Chamado atualizado com sucesso");
			} else {
				System.out.println("Não foi possivel atualizar o chamado");
			}
		}
	}

	private int apresentarOpcaoChamado(UsuarioVO usuarioVO) {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Chamados ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_CHAMADO_CADASTRAR + "-- Criar novo chamado");
		System.out.println(OPCAO_CHAMADO_ALTERAR + "-- Alterar chamado");
		System.out.println(OPCAO_CHAMADO_VERCHAMADO + "-- Ver chamados");
		System.out.println(OPCAO_CHAMADO_DELETAR + "-- Deletar chamado");
		System.out.println(OPCAO_CHAMADO_SAIR + "-- Sair");
		return this.validarOpcao();
	}

	private void cadastrarChamado(UsuarioVO usuarioVO) {
		System.out.println("\n---- Cadastro de chamado----");
		ChamadoVO chamadoVO = new ChamadoVO();
		chamadoVO.setIDUSUARIO(usuarioVO.getIdUsuario());
		System.out.print("Titulo: ");
		chamadoVO.setTitulo(teclado.nextLine());
		System.out.print("\nDescrição: ");
		chamadoVO.setDescricao(teclado.nextLine());
		chamadoVO.setDATAABERTURA(LocalDate.now());
		ChamadoController chamadoController = new ChamadoController();
		chamadoController.cadastrarNovoChamadoController(usuarioVO, chamadoVO);
	}

	private void buscarChamados(UsuarioVO usuarioVO) {
		int opcao = this.apresentarOpcoesDeBusca();
		while (opcao != OPCAO_MENU_BUSCA_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_BUSCA_TODOS:
				this.consultarTodos(usuarioVO);
				break;
			case OPCAO_MENU_BUSCA_ABERTO:
				this.consultarChamadosAbertos(usuarioVO);
				break;
			case OPCAO_MENU_BUSCA_FECHADO:
				this.consultarChamadosFechados(usuarioVO);
				break;
			default:
				System.out.println("Opção invalida!");
			}
			opcao = this.apresentarOpcoesDeBusca();
		}
	}

	private void consultarChamadosFechados(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		ArrayList<ChamadoVO> chamadoVO = new ArrayList<ChamadoVO>();
		chamadoVO = chamadoController.buscarChamadosController(usuarioVO, OPCAO_BUSCA_FECHADO);
		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s",
				"ID CHAMADO", "ID USUARIO", "ID TECNICO", "TITULO", "DESCRICAO", "DATA-ABERTURA",
				"SOLUÇÃO", "DATA-FECHAMENTO");
		System.out.println();
		if (chamadoVO != null) {
			for (int c = 0; c < chamadoVO.size(); c++) {
				chamadoVO.get(c).imprimir();
			}
		} else {
			System.out.println("Nenhum chamado encontrado!");
		}
		
	}

	private void consultarChamadosAbertos(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		ArrayList<ChamadoVO> chamadoVO = new ArrayList<ChamadoVO>();
		chamadoVO = chamadoController.buscarChamadosController(usuarioVO, OPCAO_BUSCA_ABERTO);
		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s",
				"ID CHAMADO", "ID USUARIO", "ID TECNICO", "TITULO", "DESCRICAO", "DATA-ABERTURA",
				"SOLUÇÃO", "DATA-FECHAMENTO");
		System.out.println();
		if (chamadoVO != null) {
			for (int c = 0; c < chamadoVO.size(); c++) {
				chamadoVO.get(c).imprimir();
			}
		} else {
			System.out.println("Nenhum chamado encontrado!");
		}
		
	}

	private int apresentarOpcoesDeBusca() {
		System.out.println("\nInforme o tipo de consulta a ser realizada");
		System.out.println(OPCAO_MENU_BUSCA_TODOS + " - Consultar todos os chamados.");
		System.out.println(OPCAO_MENU_BUSCA_ABERTO + " - Consultar chamados aberto.");
		System.out.println(OPCAO_MENU_BUSCA_FECHADO + " - Consultar chamados fechado.");
		System.out.println(OPCAO_MENU_BUSCA_VOLTAR + " - Voltar");
		return this.validarOpcao();
	}

	private void consultarTodos(UsuarioVO usuarioVO) {
		ChamadoController chamadoController = new ChamadoController();
		ArrayList<ChamadoVO> chamadoVO = new ArrayList<ChamadoVO>();
		chamadoVO = chamadoController.buscarChamadosController(usuarioVO, OPCAO_BUSCA_TODOS);
		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s",
				"ID CHAMADO", "ID USUARIO", "ID TECNICO", "TITULO", "DESCRICAO", "DATA-ABERTURA",
				"SOLUÇÃO", "DATA-FECHAMENTO");
		System.out.println();
		if (chamadoVO != null) {
			for (int c = 0; c < chamadoVO.size(); c++) {
				chamadoVO.get(c).imprimir();
			}
		} else {
			System.out.println("Nenhum chamado encontrado!");
		}

	}

	private boolean validarCamposChamado(ChamadoVO chamadoVO) {
		boolean resultado = true;
		System.out.println();
		if (chamadoVO.getTitulo().isEmpty() || chamadoVO.getTitulo() == null) {
			System.out.println("Campo titulo é obrigatorio!");
			resultado = false;
		}
		if (chamadoVO.getDescricao().isEmpty() || chamadoVO.getDescricao() == null) {
			System.out.println("Campo descricao é obrigatorio!");
			resultado = false;
		}
		if (chamadoVO.getDATAABERTURA() == null) {
			System.out.println("Campo data de abertura é obrigatorio!");
			resultado = false;
		}
		return resultado;

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
