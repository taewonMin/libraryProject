package pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import book.BookVO;
import bookLGU.BookLGUVO;
import bookLGU.IBookLGUService;
import bookLGU.IBookLGUServiceImpl;

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

public class BookPdf {
	public static String DEST = ".\\file\\";
	public static String SRC = ".\\file\\stamp.pdf";
	public static IBookLGUService ibls = IBookLGUServiceImpl.getInstance();
	
	
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

			doc.add(new Paragraph("도서리스트", cellTitleFont));
			doc.add(new Paragraph(" "));

			PdfPTable table1 = new PdfPTable(7);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.setTotalWidth(700f);
			table1.setLockedWidth(true);

			PdfPCell cell = null;

			PdfPTable table2 = new PdfPTable(7);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.setTotalWidth(700f);
			table2.setLockedWidth(true);

			// row 1
			cell = new PdfPCell(new Paragraph("도서코드", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("제목", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("작가", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("줄거리", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("출판사", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("도서분류", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("대여상태", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			for(BookVO bv : (List<BookVO>)params.get("bookList")){
				
				cell = new PdfPCell(new Paragraph(String.valueOf(bv.getBook_id()),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(bv.getBook_name(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(bv.getBook_author(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(bv.getBook_summary(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(
						bv.getBook_publisher(), cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				BookLGUVO blv = ibls.readBookLGU(bv.getBook_LGU());
				
				cell = new PdfPCell(new Paragraph(blv.getBook_theme(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);

				cell = new PdfPCell(new Paragraph(bv.getBook_state().equals("T") ? "대여 가능" : "대여중",
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