package view;

import java.sql.SQLException;
import java.util.Scanner;

import model.vo.TipoUsuarioVO;
import model.vo.UsuarioVO;

public class Menu {
	Scanner teclado = new Scanner(System.in);
	public static final int OPCAO_MENU_CHAMADOS = 1;
	public static final int OPCAO_MENU_ATENDIMENTOS = 2;
	public static final int OPCAO_MENU_RELATORIOS = 3;
	public static final int OPCAO_MENU_USUARIOS = 4;
	public static final int OPCAO_MENU_SAIR = 5;

	public void apresentarMenu(UsuarioVO usuarioVO) throws SQLException {
		int opcao = this.apresentarOpcao(usuarioVO);
		while (opcao != OPCAO_MENU_SAIR) {
			switch (opcao) {
			case OPCAO_MENU_CHAMADOS:
				MenuChamado chamado = new MenuChamado();
				chamado.apresentarMenuChamado(usuarioVO);
				break;
			case OPCAO_MENU_ATENDIMENTOS:
				if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.USUARIO)) {
					MenuAtendimento menuAtendimento = new MenuAtendimento();
					menuAtendimento.apresentarMenuAtendimento(usuarioVO);
				}
				break;
			case OPCAO_MENU_RELATORIOS:
				if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.USUARIO)) {
					MenuRelatorio menuRelatorio = new MenuRelatorio();
					menuRelatorio.apresentarMenuRelatorio(usuarioVO);
				}
				break;
			case OPCAO_MENU_USUARIOS:
				if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {
					MenuUsuario menuUsuario = new MenuUsuario();
					menuUsuario.apresentarMenuUsuario(usuarioVO);
				}
				break;
			default: {
				System.out.println("Opção invalida!");
			}
			}
			opcao = this.apresentarOpcao(usuarioVO);
		}
	}

	private int apresentarOpcao(UsuarioVO usuarioVO) {
		System.out.println("\n---- Sistema Socrro Desk ----");
		System.out.println("\n---- Menu Principal ----");
		System.out.println("\nOpções:");
		System.out.println(OPCAO_MENU_CHAMADOS + "-- Chamados");
		if (!usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.USUARIO)) {
			System.out.println(OPCAO_MENU_ATENDIMENTOS + "-- Atendimentos");
			System.out.println(OPCAO_MENU_RELATORIOS + "-- Relatorios");

		}
		if (usuarioVO.getTipoUsuarioVO().equals(TipoUsuarioVO.ADMINISTRADOR)) {

			System.out.println(OPCAO_MENU_USUARIOS + "-- Usuarios");
		}
		System.out.println(OPCAO_MENU_SAIR + "-- Sair");
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
