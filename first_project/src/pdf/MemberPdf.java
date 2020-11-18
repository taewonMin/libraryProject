package pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import member.MemberVO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class MemberPdf {
	public static String DEST = ".\\file\\";
	public static String SRC = ".\\file\\stamp.pdf";
	
	public static void pdfRun(Map<String, Object> params){
		Document doc = new Document(PageSize.A4.rotate(), 20, 20, 30, 30); 
		
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(SRC));

			doc.open();
			doc.add(new Paragraph(" "));

			Image jpg = Image.getInstance(".\\file\\img.bmp");
			doc.add(jpg);

			doc.add(new Paragraph(" "));

			String resPath = ".\\file\\H2HDRM.TTF";

			BaseFont bf = BaseFont.createFont(resPath, BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font cellTitleFont = new Font(bf, 10, Font.BOLD);
			Font cellNormalFont = new Font(bf, 10, Font.NORMAL);

			doc.add(new Paragraph("회원리스트", cellTitleFont));
			doc.add(new Paragraph(" "));

			PdfPTable table1 = new PdfPTable(7);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.setTotalWidth(500f);
			table1.setLockedWidth(true);

			PdfPCell cell = null;

			PdfPTable table2 = new PdfPTable(7);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.setTotalWidth(800f);
			table2.setLockedWidth(true);

			// row 1
			cell = new PdfPCell(new Paragraph("아이디", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("비밀번호", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("이름", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("생일", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("이메일", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("휴대폰", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("대출횟수", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			for(MemberVO mv : (List<MemberVO>)params.get("memList")){
				cell = new PdfPCell(new Paragraph(mv.getMem_id(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(mv.getMem_pw(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(mv.getMem_name(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(mv.getMem_bir().substring(0,10),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(mv.getMem_email(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(mv.getMem_tel(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(String.valueOf(mv.getRent_count()),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
			}

			doc.add(table2);
			doc.close();
			
			DEST += params.get("fname") + ".pdf";
			
			new Watermark().manipulatePdf(SRC, DEST);

			File file = new File(DEST);
			file.getParentFile().mkdirs();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}