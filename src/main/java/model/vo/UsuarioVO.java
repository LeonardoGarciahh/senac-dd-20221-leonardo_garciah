package model.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class UsuarioVO {

	private int idUsuario;
	private TipoUsuarioVO tipoUsuarioVO; // Tabela propria Usuario/Tecnico/Admin
	private String nome;
	private String cpf;
	private String email;
	private LocalDate dataCadastro;
	private LocalDate dataExpiracao;
	private String login;
	private String senha;

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public UsuarioVO(int idUsuario, TipoUsuarioVO tipoUsuarioVO, String nome, String cpf, String email,
			LocalDate dataCadastro, LocalDate dataExpiracao, String login, String senha) {
		super();
		this.idUsuario = idUsuario;
		this.tipoUsuarioVO = tipoUsuarioVO;
		this.nome = nome;
		this.cpf = cpf;
		this.email = email;
		this.dataCadastro = dataCadastro;
		this.dataExpiracao = dataExpiracao;
		this.login = login;
		this.senha = senha;
	}

	public UsuarioVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public TipoUsuarioVO getTipoUsuarioVO() {
		return tipoUsuarioVO;
	}

	public void setTipoUsuarioVO(TipoUsuarioVO tipoUsuarioVO) {
		this.tipoUsuarioVO = tipoUsuarioVO;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDate dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDate getDataExpiracao() {
		return dataExpiracao;
	}

	public void setDataExpiracao(LocalDate dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public void imprimir() {
		System.out.printf("%3s  %-13s  %-20s  %-11s  %-20s  %-15s  %-15s  %-10s  %-10s  \n", this.getIdUsuario(),
				this.getTipoUsuarioVO(), this.getNome(), this.getCpf(), this.getEmail(),
				this.validaData(this.getDataCadastro()), this.validaData(this.getDataExpiracao()), this.getLogin(),
				this.getSenha());
	}

	private String validaData(LocalDate data) {
		String resultado = "";
		if (data != null) {
			resultado = data.format(formaterDate);
		}
		return resultado;
	}

}
