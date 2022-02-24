package model.pdf;

import java.awt.Desktop;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import com.itextpdf.awt.DefaultFontMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import controller.RelatorioController;
import model.dto.RelatorioDTO;
import view.MenuRelatorio;

public class GeradorPDF {

	// Opções mostrar resultado
	public static final int OPCAO_RELATORIO_ATENDIDOS_CONSOLE = 1;
	public static final int OPCAO_RELATORIO_ATENDIDOS_PDF = 2;
	public static final int OPCAO_RELATORIO_ATENDIDOS_SAIR = 3;

	DateTimeFormatter formaterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	Scanner teclado = new Scanner(System.in);

	// Gerar PDF Chamados cadastrados
	public void gerarPDFChamadosCadastrados(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> relatoriosDTO = new ArrayList<RelatorioDTO>();

		relatoriosDTO = relatorioController.gerarRelatorioChamadosCadastradosController(relatorioDTO);

		Document document = new Document();

		try {
			PdfWriter.getInstance(document, new FileOutputStream("documento.pdf"));
			document.open();
			PdfPTable tabela = new PdfPTable(6);
			PdfPCell cabecalho = new PdfPCell(new Paragraph("Relatórios chamados cadastrados ("+formatData(relatorioDTO.getDataInicial())+" - "
			+formatData(relatorioDTO.getDataFinal())+")"));

			cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
			cabecalho.setBorder(PdfPCell.NO_BORDER);
			cabecalho.setBackgroundColor(new BaseColor(226, 226, 226));
			cabecalho.setColspan(6);
			tabela.addCell(cabecalho);
			tabela.addCell("ID");
			tabela.addCell("Autor");
			tabela.addCell("Titulo");
			tabela.addCell("Descricao");
			tabela.addCell("Data Abertura");
			tabela.addCell("Atendido");
			for (int c = 0; c < relatoriosDTO.size(); c++) {
				tabela.addCell(Integer.toString(relatoriosDTO.get(c).getIdChamado()));
				tabela.addCell(relatoriosDTO.get(c).getUsuarioNome());
				tabela.addCell(relatoriosDTO.get(c).getTitulo());
				tabela.addCell(relatoriosDTO.get(c).getDescricao());
				tabela.addCell(relatoriosDTO.get(c).getDataabertura().toString());
				if (relatoriosDTO.get(c).isSolucionado() == true) {
					tabela.addCell("Sim");
				} else {
					tabela.addCell("Não");
				}
			}
			document.add(tabela);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (DocumentException e) {

			e.printStackTrace();
		} finally {
			document.close();
		}

		try {
			Desktop.getDesktop().open(new File("documento.pdf"));
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// Gerar PDF total atendidos
	public void gerarPDFTotalAtendidos(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		RelatorioDTO relatorio = relatorioController.gerarRelatorioChamadosTotalAtendidosController(relatorioDTO);

		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("documento.pdf"));
			document.open();
			PdfPTable tabela = new PdfPTable(2);
			PdfPCell cabecalho = new PdfPCell(new Paragraph("Relatório chamados atendidos ("+formatData(relatorioDTO.getDataInicial())+" - "
					+formatData(relatorioDTO.getDataFinal())+")"));
			cabecalho.setHorizontalAlignment(Element.ALIGN_CENTER);
			cabecalho.setBorder(PdfPCell.NO_BORDER);
			cabecalho.setColspan(2);
			cabecalho.setBackgroundColor(new BaseColor(226, 226, 226));
			tabela.addCell(cabecalho);
			tabela.addCell("Total chamados");
			tabela.addCell("Total atendidos");
			tabela.addCell(Integer.toString(relatorio.getTotalChamados()));
			tabela.addCell(Integer.toString(relatorio.getTotalAtendidos()));
			document.add(tabela);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}

		try {
			Desktop.getDesktop().open(new File("documento.pdf"));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Gerar PDF Total de chamados atendidos por tecnico
	public void gerarPDFChamadosAtendidosPorTecnico(RelatorioDTO relatorioDTO) {
		RelatorioController relatorioController = new RelatorioController();
		ArrayList<RelatorioDTO> relatorios = relatorioController
				.gerarPDFChamadosAtendidosPorTecnicoController(relatorioDTO);

		DefaultPieDataset dados = new DefaultPieDataset();

		for (int c = 0; c < relatorios.size(); c++) {
			dados.setValue(relatorios.get(c).getTecnicoNome() + " = " + relatorios.get(c).getTotalAtendidos(),
					relatorios.get(c).getTotalAtendidos());
		}

		JFreeChart grafico = ChartFactory.createPieChart("Gráfico total de atendimentos por técnico", dados, true, true,
				false);

		escreverPDFChamadosAtendidosPorTecnico(grafico, 500, 400);

	}

	// Imprimir o grafico dentro do PDF
	public static void escreverPDFChamadosAtendidosPorTecnico(JFreeChart chart, int largura, int altura) {
		PdfWriter escritor = null;

		Document document = new Document();

		try {
			escritor = PdfWriter.getInstance(document, new FileOutputStream("documento.pdf"));
			document.open();
			PdfContentByte conteudo = escritor.getDirectContent();
			PdfTemplate template = conteudo.createTemplate(largura, altura);
			Graphics2D graphics2d = template.createGraphics(largura, altura, new DefaultFontMapper());
			java.awt.geom.Rectangle2D rectangle2d = new java.awt.geom.Rectangle2D.Double(0, 0, largura, altura);

			chart.draw(graphics2d, rectangle2d);

			graphics2d.dispose();
			conteudo.addTemplate(template, 50, 400);

		} catch (Exception e) {
			e.printStackTrace();
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("documento.pdf"));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	//Formatar data
	private String formatData(LocalDate data) {
		String dataabertura = data.toString().replaceAll("-", "/");
		String[] s = dataabertura.split("/");
		String novaData = s[2] + "/" + s[1] + "/" + s[0];
		return novaData;
	}

}
