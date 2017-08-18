package br.unipam.locadora.dao;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.unipam.locadora.entity.Cliente;
import br.unipam.locadora.entity.Locacao;
import br.unipam.locadora.entity.LocacaoVeiculo;
import br.unipam.locadora.entity.Veiculo;
import br.unipam.locadora.util.GraficoLinha;
import br.unipam.locadora.util.PdfPageHelper;

@Stateless
public class LocacaoDAO extends GenericDAO<Locacao> {

	public LocacaoDAO() {
		super(Locacao.class);
	}

	@Override
	public String salvar(Locacao locacao) {
		try {
			
			Double total = 0.0;
			System.out.println(locacao.getLocacaoVeiculos().size());
			for(LocacaoVeiculo lv : locacao.getLocacaoVeiculos()){
				
				total+=lv.getValor() * lv.getQuantidadeDia();
				lv.setLocacao(locacao);
				
				em.persist(lv);

			}
			
			locacao.setValorTotal(total);
			
			em.persist(locacao);
			em.merge(locacao);
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "Erro: " + ex.getMessage();
		}
	}

	
	public List<Locacao> listar(Cliente cliente, Date data){
		System.out.println(cliente);
		TypedQuery<Locacao> query = super.em.createQuery("select a from Locacao a where "
				+ "a.cliente = :cliente or a.dataInicio = :data",Locacao.class);
		
		query.setParameter("cliente", cliente);
		query.setParameter("data", data,TemporalType.DATE);
		
		List<Locacao> locacoes= query.getResultList();

		return locacoes;
		
		
	}
	
	public List<GraficoLinha> listar(int ano,Veiculo veiculo){
	
		Query query = super.em.createQuery("select EXTRACT(MONTH from a.locacao.dataInicio) as y,SUM(a.valor * a.quantidadeDia) from LocacaoVeiculo a "
				+ "where EXTRACT(YEAR from a.locacao.dataInicio) =:ano and a.veiculo = :veiculo group by y");
		
		query.setParameter("ano", ano); 
		query.setParameter("veiculo", veiculo);
		
		List<GraficoLinha> locacoes= new ArrayList<GraficoLinha>();
				
		@SuppressWarnings("unchecked")
		List<Object[]> list=query.getResultList();
		
		for (Object[] objects : list) {
			locacoes.add(new GraficoLinha((Integer)objects[0], (Double)objects[1]));
		}

		System.out.println(locacoes.size());
		return locacoes;
		
		
	}

	
	public ByteArrayOutputStream relatorio(Cliente cliente, Date data) throws Exception{
		
		List<Locacao> locacoes = listar(cliente, data);
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		Document document = new Document(PageSize.A4.rotate());
		
		PdfWriter pdfWriter =PdfWriter.getInstance(document, output);
		PdfPageHelper helper = new PdfPageHelper();
		
		helper.setTitle("Relatório de locações");
		pdfWriter.setPageEvent(helper);
		
		
		document.open();
		
		Font fontCabecalho = new Font(FontFamily.TIMES_ROMAN,14,Font.BOLD);
		Font fontTexto = new Font(FontFamily.TIMES_ROMAN,12);
		
		PdfPTable table = new PdfPTable(4);
		table.setHeaderRows(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{20,40,20,20});
		String[] colunas = {"Número","Cliente","Valor","Data"};
		
		for (String coluna : colunas) {
			PdfPCell cell = new PdfPCell(new Phrase(coluna,fontCabecalho));
			cell.setBackgroundColor(new BaseColor(204, 204, 204));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER) ;
			table.addCell(cell);
		}
		
		
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
		
		double total = 0;
		for (Locacao locacao : locacoes) {
			PdfPCell cellNumero = new PdfPCell(new Phrase(locacao.getIdLocacao().toString(),fontTexto));
			
			PdfPCell cellCliente= new PdfPCell(new Phrase(locacao.getCliente().getNome(),fontTexto));
			
			
			PdfPCell cellValor = new PdfPCell(new Phrase(numberFormat.format(locacao.getValorTotal()),fontTexto));
			
			PdfPCell cellData = new PdfPCell(new Phrase(dateFormat.format(locacao.getDataInicio()),fontTexto));
		
			table.addCell(cellNumero);
			table.addCell(cellCliente);
			table.addCell(cellValor);
			table.addCell(cellData);
			
			total += total + locacao.getValorTotal();
		}
		
		Paragraph paragraphTotal = new Paragraph("Valor Total = "+numberFormat.format(total),fontCabecalho);
		paragraphTotal.setAlignment(Element.ALIGN_RIGHT);
		
		
		
		document.add(table);
		document.add(paragraphTotal);
		document.close();
		
		return output;
	}
	
	public ByteArrayOutputStream ficha(int idLocacao) throws Exception{
		
		Locacao locacao= super.em.find(Locacao.class, idLocacao);
		
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		
		Document document = new Document(PageSize.A4.rotate());
		
		PdfWriter pdfWriter =PdfWriter.getInstance(document, output);
		PdfPageHelper helper = new PdfPageHelper();
		
		helper.setTitle("Locação de "+locacao.getCliente().getNome());
		pdfWriter.setPageEvent(helper);
		
		
		document.open();
		
		Font fontCabecalho = new Font(FontFamily.TIMES_ROMAN,14,Font.BOLD);
		Font fontTexto = new Font(FontFamily.TIMES_ROMAN,12);
		
		PdfPTable table = new PdfPTable(5);
		table.setHeaderRows(1);
		table.setWidthPercentage(100);
		table.setWidths(new int[]{30,30,15,10,15});
		String[] colunas = {"Placa","Modelo","Valor","Dias","Total"};
		
		for (String coluna : colunas) {
			PdfPCell cell = new PdfPCell(new Phrase(coluna,fontCabecalho));
			cell.setBackgroundColor(new BaseColor(204, 204, 204));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER) ;
			table.addCell(cell);
		}
		
		
		NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
	
		for (LocacaoVeiculo locacaoVeiculo : locacao.getLocacaoVeiculos()) {
			PdfPCell cellPlaca = new PdfPCell(new Phrase(locacaoVeiculo.getVeiculo().getPlaca(),fontTexto));
			
			PdfPCell cellModelo= new PdfPCell(new Phrase(locacaoVeiculo.getVeiculo().getModelo().getDescricao(),fontTexto));
			
			
			PdfPCell cellValor = new PdfPCell(new Phrase(numberFormat.format(locacaoVeiculo.getValor()),fontTexto));
			
			PdfPCell cellDias = new PdfPCell(new Phrase(locacaoVeiculo.getQuantidadeDia().toString(),fontTexto));
			
			PdfPCell cellTotal= new PdfPCell(new Phrase(numberFormat.format(locacaoVeiculo.getValor() * locacaoVeiculo.getQuantidadeDia()),fontTexto));
		
			table.addCell(cellPlaca);
			table.addCell(cellModelo);
			table.addCell(cellValor);
			table.addCell(cellDias);
			table.addCell(cellTotal);
			
		}
		
		Paragraph paragraphTotal = new Paragraph("Valor Total = "+numberFormat.format(locacao.getValorTotal()),fontCabecalho);
		paragraphTotal.setAlignment(Element.ALIGN_RIGHT);
		
		
		
		document.add(table);
		document.add(paragraphTotal);
		document.close();
		
		return output;
	}


}
