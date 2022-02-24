
package model.vo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ChamadoVO {
	private int idChamado;
	private int IDUSUARIO;
	private int IDTECNICO;
	private String descricao;
	private String titulo;
	private LocalDate DATAABERTURA;
	private LocalDate DATAFECHAMENTO;
	private String SOLUCAO;

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public ChamadoVO(int idChamado, int iDUSUARIO, int iDTECNICO, String descricao, String titulo,
			LocalDate dATAABERTURA, LocalDate dATAFECHAMENTO, String sOLUCAO) {
		super();
		this.idChamado = idChamado;
		IDUSUARIO = iDUSUARIO;
		IDTECNICO = iDTECNICO;
		this.descricao = descricao;
		this.titulo = titulo;
		DATAABERTURA = dATAABERTURA;
		DATAFECHAMENTO = dATAFECHAMENTO;
		SOLUCAO = sOLUCAO;
	}

	public ChamadoVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getIdChamado() {
		return idChamado;
	}

	public void setIdChamado(int idChamado) {
		this.idChamado = idChamado;
	}

	public int getIDUSUARIO() {
		return IDUSUARIO;
	}

	public void setIDUSUARIO(int iDUSUARIO) {
		IDUSUARIO = iDUSUARIO;
	}

	public int getIDTECNICO() {
		return IDTECNICO;
	}

	public void setIDTECNICO(int iDTECNICO) {
		IDTECNICO = iDTECNICO;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public LocalDate getDATAABERTURA() {
		return DATAABERTURA;
	}

	public void setDATAABERTURA(LocalDate dATAABERTURA) {
		DATAABERTURA = dATAABERTURA;
	}

	public LocalDate getDATAFECHAMENTO() {
		return DATAFECHAMENTO;
	}

	public void setDATAFECHAMENTO(LocalDate dATAFECHAMENTO) {
		DATAFECHAMENTO = dATAFECHAMENTO;
	}

	public String getSOLUCAO() {
		return SOLUCAO;
	}

	public void setSOLUCAO(String sOLUCAO) {
		SOLUCAO = sOLUCAO;
	}

	public void imprimir() {

		System.out.printf("%10s  %10s  %10s  %-30s  %-50s  %-15s  %-30s  %-15s", this.getIdChamado(),
				this.getIDUSUARIO(), this.getIDTECNICO(), this.getTitulo(), this.getDescricao(), this.getDATAABERTURA(),
				this.getSOLUCAO(), this.getDATAFECHAMENTO());
		System.out.println();
	}

	private String validaData(LocalDate data) {
		String resultado = "";
		if (data != null) {
			resultado = data.format(formaterDate);
		}
		return resultado;
	}

}
