package br.unipam.locadora.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfPageHelper extends PdfPageEventHelper {

	
	//private String caminho;
	private PdfTemplate template;
	private BaseFont bf;
	private PdfContentByte cb;
    private String title;
    
    
	
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
		  try
          {
              bf = BaseFont.createFont(BaseFont.HELVETICA,BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
              cb = writer.getDirectContent();
              template = cb.createTemplate(50, 50);
          }
          catch (Exception ex)
          {
        	  ex.printStackTrace();
          }
	}


	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		try {
			  document.add(new Paragraph(" "));
	          document.add(new Paragraph(" "));
	          document.add(new Paragraph(" "));
	          document.add(new Paragraph(" "));
	
	    
	          super.onStartPage(writer, document);
	
	          Rectangle pageSize = document.getPageSize();
	
	
	          Image logo = Image.getInstance("http://localhost:8080/br.unipam.locadora/conteudo/imagem/logo.png");
	          logo.scaleToFit(20, 20);
	          
	          Font pageFont = new Font(bf, 8);
	          int pageN = writer.getPageNumber();
	          String textPagina = "Página " + pageN + "/";
	
	          SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	          String dataEmissao = dateFormat.format(new Date()); 
	
	
	          Paragraph rightCell = new Paragraph();
	          rightCell.setFont(pageFont);
	          rightCell.add(new Paragraph(textPagina));
	          rightCell.add(new Paragraph());
	          rightCell.add(new Paragraph(dataEmissao));
	
	          
	          PdfPTable headerTable = new PdfPTable(3);
	          headerTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
	          headerTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
	          headerTable.setTotalWidth(pageSize.getWidth() - 80) ;
	          headerTable.setWidthPercentage(new float[] { 17, 55, 18 }, pageSize);
	
	          PdfPCell headerLeftCell = new PdfPCell(logo);
	          headerLeftCell.setPadding(2);
	          headerLeftCell.setPaddingBottom(8);
	          headerLeftCell.setBorder(0);
	          headerTable.addCell(headerLeftCell);
	
	
	          PdfPCell headerMiddleCell = new PdfPCell();
	          Paragraph p1 = new Paragraph("Nome Empresa", new Font(bf, 12, Font.BOLD));
	          p1.setAlignment(Element.ALIGN_CENTER);
	          Paragraph p2 = new Paragraph(title, new Font(bf, 12));
	          p2.setAlignment(Element.ALIGN_CENTER);
	          headerMiddleCell.addElement(p1);
	          headerMiddleCell.addElement(p2);
	          headerMiddleCell.setPadding(2);
	          headerMiddleCell.setBorder(0);
	          
	          headerTable.addCell(headerMiddleCell);
	
	
	          PdfPCell headerRightCell = new PdfPCell(rightCell);
	          headerRightCell.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
	          headerRightCell.setPadding(2);
	          headerRightCell.setPaddingTop(15);
	          headerRightCell.setBorder(0);
	          headerTable.addCell(headerRightCell);
	
	          headerTable.writeSelectedRows(0, -1, pageSize.getLeft(40), pageSize.getTop(20), cb);
	          cb.addTemplate(template, pageSize.getRight(42), pageSize.getTop(43));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public void onCloseDocument(PdfWriter writer, Document document) {
		super.onCloseDocument(writer, document);

        template.beginText();
        template.setFontAndSize(bf, 8);
        template.setTextMatrix(0, 0);
        template.showText("" + (writer.getPageNumber() - 1));
        template.endText();
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

		

}
