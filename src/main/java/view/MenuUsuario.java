package view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import controller.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class MenuUsuario {
	Scanner teclado = new Scanner(System.in);
	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private static final int OPCAO_MENU_CADASTRAR_USUARIO = 1;
	private static final int OPCAO_MENU_CONSULTAR_USUARIO = 2;
	private static final int OPCAO_MENU_ATUALIZAR_USUARIO = 3;
	private static final int OPCAO_MENU_EXCLUIR_USUARIO = 4;
	private static final int OPCAO_MENU_SAIR_USUARIO = 5;
	private static final int OPCAO_MENU_CONSULTA_TODOS = 1;
	private static final int OPCAO_MENU_CONSULTA_ESPECIFICO = 2;
	private static final int OPCAO_MENU_CONSULTA_VOLTAR = 3;

	public void apresentarMenuUsuario(UsuarioVO usuarioVO) throws SQLException {
		int opcao = this.apresentarOpcoesMenu();
		while (opcao != OPCAO_MENU_SAIR_USUARIO) {
			switch (opcao) {
			case OPCAO_MENU_CADASTRAR_USUARIO:
				this.cadastrarUsuario(usuarioVO);
				break;
			case OPCAO_MENU_CONSULTAR_USUARIO:
				this.consultarUsuario();
				break;
			case OPCAO_MENU_ATUALIZAR_USUARIO:
				this.atualizarUsuario();
				break;
			case OPCAO_MENU_EXCLUIR_USUARIO:
				this.excluirUsuario();
				break;
			default:
				System.out.println("\nOpção invalida!");
				break;
			}
			opcao = this.apresentarOpcoesMenu();
		}
	}

	private int apresentarOpcoesMenu() {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Cadastro de Usuario ----");
		System.out.println(OPCAO_MENU_CADASTRAR_USUARIO + "-- Cadastrar usuario");
		System.out.println(OPCAO_MENU_CONSULTAR_USUARIO + "-- Consultar usuario");
		System.out.println(OPCAO_MENU_ATUALIZAR_USUARIO + "-- Atualizar usuario");
		System.out.println(OPCAO_MENU_EXCLUIR_USUARIO + "-- Excluir");
		System.out.println(OPCAO_MENU_SAIR_USUARIO + "-- Sair");
		return this.validarOpcao();
	}

	private void cadastrarUsuario(UsuarioVO usuarioVO) throws SQLException {
		System.out.println(usuarioVO.getTipoUsuarioVO());
		if (usuarioVO.getTipoUsuarioVO() == TipoUsuarioVO.ADMINISTRADOR) {
			do {
				usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuario()));
			} while (usuarioVO.getTipoUsuarioVO() == null);
		}
		System.out.print("Insira seu nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.print("\nInsira seu cpf: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.print("\nInsira seu email: ");
		usuarioVO.setEmail(teclado.nextLine());
		usuarioVO.setDataCadastro(LocalDate.now());
		System.out.print("\nInsira seu login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("\nInsira sua senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			usuarioController.cadastrarUsuarioController(usuarioVO);
		}

	}

	private int apresentarOpcoesTipoUsuario() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<TipoUsuarioVO> tiposUsuariosVO = usuarioController.consultarTiposUsuariosController();
		System.out.println("\n---- Tipos de usuarios ----");
		System.out.println("Opções: ");
		for (int c = 0; c < tiposUsuariosVO.size(); c++) {
			System.out.println(tiposUsuariosVO.get(c).getValor() + " - " + tiposUsuariosVO.get(c));
		}
		return this.validarOpcao();
	}

	private boolean validarCamposCadastro(UsuarioVO usuarioVO) {
		boolean resultado = true;
		System.out.println();
		if (usuarioVO.getNome().isEmpty() || usuarioVO.getNome() == null) {
			System.out.println("Campo nome é obrigatorio!");
			resultado = false;
		}
		if (usuarioVO.getCpf().isEmpty() || usuarioVO.getCpf() == null) {
			System.out.println("Campo cpf é obrigatorio!");
			resultado = false;
		}
		if (usuarioVO.getEmail().isEmpty() || usuarioVO.getEmail() == null) {
			System.out.println("Campo email é obrigatorio!");
			resultado = false;
		}
		if (usuarioVO.getDataCadastro() == null) {
			System.out.println("Campo de data de cadastro é obrigatorio!");
			resultado = false;
		}
		if (usuarioVO.getLogin().isEmpty() || usuarioVO.getLogin() == null) {
			System.out.println("Campo login é obrigatorio!");
			resultado = false;
		}
		if (usuarioVO.getSenha().isEmpty() || usuarioVO.getSenha() == null) {
			System.out.println("Campo senha é obrigatorio!");
			resultado = false;
		}
		return resultado;
	}

	public void cadastrarNovoUsuario(UsuarioVO usuarioVO) throws SQLException {
		this.cadastrarUsuario(usuarioVO);

	}

	private void consultarUsuario() throws SQLException {
		int opcao = this.apresentarOpcoesDeConsulta();
		while (opcao != OPCAO_MENU_CONSULTA_VOLTAR) {
			switch (opcao) {
			case OPCAO_MENU_CONSULTA_TODOS:
				System.out.printf("%3s  %-13s  %-20s  %-11s  %-20s  %-15s  %-15s  %-10s  %-10s  ", "ID", "TIPO USUARIO",
						"NOME", "CPF", "E-MAIL", "DATA-CADASTRO", "DATA-EXPIRACAO", "LOGIN", "SENHA");
				System.out.println();
				this.consultarTodos();
				break;
			case OPCAO_MENU_CONSULTA_ESPECIFICO:
				this.consultarEspecifico();
				break;
			default:
				System.out.println("Opção invalida!");
			}
			opcao = this.apresentarOpcoesDeConsulta();
		}

	}

	private void consultarEspecifico() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<UsuarioVO> usuariosVO = new ArrayList<UsuarioVO>();
		System.out.print("\nInsira o id do usuario a ser consultado: ");
		int id = Integer.parseInt(teclado.nextLine());
		System.out.printf("%3s  %-13s  %-20s  %-11s  %-20s  %-15s  %-15s  %-10s  %-10s  ", "ID", "TIPO USUARIO", "NOME",
				"CPF", "E-MAIL", "DATA-CADASTRO", "DATA-EXPIRACAO", "LOGIN", "SENHA");
		System.out.println();
		usuariosVO = usuarioController.consultarUsuarioController(id);
		for (int c = 0; c < usuariosVO.size(); c++) {
			usuariosVO.get(c).imprimir();
		}

	}

	private void consultarTodos() {
		UsuarioController usuarioController = new UsuarioController();
		ArrayList<UsuarioVO> usuariosVO = new ArrayList<UsuarioVO>();
		usuariosVO = usuarioController.consultarUsuarioController(0);
		for (int c = 0; c < usuariosVO.size(); c++) {
			usuariosVO.get(c).imprimir();
		}

	}

	private int apresentarOpcoesDeConsulta() {
		System.out.println("\nInforme o tipo de consulta a ser realizada");
		System.out.println(OPCAO_MENU_CONSULTA_TODOS + " - Consultar todos os usuarios.");
		System.out.println(OPCAO_MENU_CONSULTA_ESPECIFICO + " - Consultar um usuario especifico.");
		System.out.println(OPCAO_MENU_CONSULTA_VOLTAR + " - Voltar");
		return this.validarOpcao();
	}

	private void atualizarUsuario() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.println("Insira o id do usuario: ");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		do {
			usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.getTipoUsuarioVOPorValor(this.apresentarOpcoesTipoUsuario()));
		} while (usuarioVO.getTipoUsuarioVO() == null);
		System.out.print("Insira seu nome: ");
		usuarioVO.setNome(teclado.nextLine());
		System.out.print("\nInsira seu cpf: ");
		usuarioVO.setCpf(teclado.nextLine());
		System.out.print("\nInsira seu email: ");
		usuarioVO.setEmail(teclado.nextLine());
		System.out.print("\nInsira a data de cadastro: ");
		usuarioVO.setDataCadastro(LocalDate.parse(teclado.nextLine(), formaterDate));
		System.out.print("\nInsira seu login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("\nInsira sua senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (validarCamposCadastro(usuarioVO)) {
			UsuarioController usuarioController = new UsuarioController();
			boolean resultado = usuarioController.atualizarUsuarioController(usuarioVO);
			if (resultado) {
				System.out.println("Usuario atualizado com sucesso");
			} else {
				System.out.println("Não foi possivel atualizar o usuario");
			}
		}
	}

	private void excluirUsuario() throws SQLException {
		this.consultarUsuario();

		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.print("\nDigite o ID do usuário que deseja excluir:");
		usuarioVO.setIdUsuario(Integer.parseInt(teclado.nextLine()));
		System.out.print("Digite a data de expiração no formato dd/MM/yyyy");
		usuarioVO.setDataExpiracao(LocalDate.parse(teclado.nextLine(), formaterDate));
		UsuarioController usuarioController = new UsuarioController();
		usuarioController.excluirUsuarioController(usuarioVO);

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
