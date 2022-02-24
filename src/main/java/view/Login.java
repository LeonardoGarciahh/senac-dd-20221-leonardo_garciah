package view;

import java.sql.SQLException;
import java.util.Scanner;

import controller.UsuarioController;
import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class Login {
	Scanner teclado = new Scanner(System.in);
	private static final int OPCAO_MENU_LOGIN = 1;
	private static final int OPCAO_MENU_CADASTRO = 2;
	private static final int OPCAO_MENU_SAIR = 3;

	public void apresentarMenuLogin() throws SQLException {
		Integer opcao = 0;
		opcao = apresentarOpcaoMenu();
		while (opcao != OPCAO_MENU_SAIR) {
			switch (opcao) {
			case OPCAO_MENU_LOGIN:
				UsuarioVO usuarioVO = this.realizarLogin();
				if (usuarioVO.getIdUsuario() != 0 && usuarioVO.getDataExpiracao() == null) {
					System.out.println("Usuario Logado: " + usuarioVO.getLogin());
					System.out.println("Perfil: " + usuarioVO.getTipoUsuarioVO());

					Menu menu = new Menu();
					menu.apresentarMenu(usuarioVO);
				}
				System.out.println("\nRealizando o login...");
				break;
			case OPCAO_MENU_CADASTRO:
				this.cadastrarUsuario();
				break;
			default:
				System.out.println("\nOpção invalida!");
				break;
			}
			opcao = this.apresentarOpcaoMenu();
		}
	}

	private void cadastrarUsuario() throws SQLException {
		System.out.println("\n---- Cadastro de Usuario----");
		UsuarioVO usuarioVO = new UsuarioVO();
		usuarioVO.setTipoUsuarioVO(TipoUsuarioVO.USUARIO);
		MenuUsuario menuUsuario = new MenuUsuario();
		menuUsuario.cadastrarNovoUsuario(usuarioVO);
	}

	private int apresentarOpcaoMenu() {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_MENU_LOGIN + "-- Entrar");
		System.out.println(OPCAO_MENU_CADASTRO + "-- Criar conta");
		System.out.println(OPCAO_MENU_SAIR + "-- Sair");
		return this.validarOpcao();
	}

	private UsuarioVO realizarLogin() {
		UsuarioVO usuarioVO = new UsuarioVO();
		System.out.println("\n---- Informações ----\n");
		System.out.print("Login: ");
		usuarioVO.setLogin(teclado.nextLine());
		System.out.print("Senha: ");
		usuarioVO.setSenha(teclado.nextLine());

		if (usuarioVO.getLogin().isEmpty() || usuarioVO.getSenha().isEmpty()) {
			System.out.println("Os campos de login e senha são Obrigatórios");
		} else {
			UsuarioController usuarioController = new UsuarioController();
			usuarioVO = usuarioController.realizarLoginController(usuarioVO);

			if (usuarioVO.getNome() == null || usuarioVO.getNome().isEmpty()) {
				System.out.println("\nUsuario não encotrado!");
			}
			if (usuarioVO.getDataExpiracao() != null) {
				System.out.println("\nUsuario expirado!");
			}
		}
		return usuarioVO;
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
