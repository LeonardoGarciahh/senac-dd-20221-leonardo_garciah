package model.dto;

import java.time.LocalDate;

public class RelatorioDTO {
	private int idChamado;
	private String usuarioNome;
	private String descricao;
	private String titulo;
	private String tecnicoNome;
	private String dataabertura;
	private int totalChamados;
	private int totalAtendidos;
	private LocalDate dataInicial;
	private LocalDate dataFinal;
	private boolean solucionado = false;

	public RelatorioDTO() {
		super();
	}

	public RelatorioDTO(int idChamado, String usuarioNome, String descricao, String titulo, String dataabertura,
			int totalChamados, int totalAtendidos, LocalDate dataInicial, LocalDate dataFinal, boolean solucionado) {
		super();
		this.idChamado = idChamado;
		this.usuarioNome = usuarioNome;
		this.descricao = descricao;
		this.titulo = titulo;
		this.dataabertura = dataabertura;
		this.totalChamados = totalChamados;
		this.totalAtendidos = totalAtendidos;
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.solucionado = solucionado;
	}

	public String getTecnicoNome() {
		return tecnicoNome;
	}

	public void setTecnicoNome(String tecnicoNome) {
		this.tecnicoNome = tecnicoNome;
	}

	public int getTotalChamados() {
		return totalChamados;
	}

	public void setTotalChamados(int totalChamados) {
		this.totalChamados = totalChamados;
	}

	public int getTotalAtendidos() {
		return totalAtendidos;
	}

	public void setTotalAtendidos(int totalAtendidos) {
		this.totalAtendidos = totalAtendidos;
	}

	public LocalDate getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	public LocalDate getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isSolucionado() {
		return solucionado;
	}

	public void setSolucionado(boolean solucionado) {
		this.solucionado = solucionado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getIdChamado() {
		return idChamado;
	}

	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome) {
		this.usuarioNome = usuarioNome;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDataabertura() {
		return dataabertura;
	}

	public void setDataabertura(String dataabertura) {
		this.dataabertura = dataabertura;
	}

	public void imprimirString() {
		System.out.printf("%10s  %10s  %-20s  %-30s  %-10s\n", this.getIdChamado(), this.getUsuarioNome(),
				this.getTitulo(), this.getDescricao(), this.getDataabertura());
	}

	public void imprimir() {
		System.out.printf("%12s  %18s\n", this.getTecnicoNome(), this.getTotalAtendidos());

	}

}
