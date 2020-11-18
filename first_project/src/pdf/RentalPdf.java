package pdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import rental.IRentalService;
import rental.IRentalServiceImpl;
import rental.RentalVO;
import book.BookVO;
import book.IBookService;
import book.IBookServiceImpl;

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

public class RentalPdf {
	public static String DEST = ".\\file\\";
	public static String SRC = ".\\file\\stamp.pdf";
	public static IBookService ibs = IBookServiceImpl.getInstance();
	public static IRentalService irts = IRentalServiceImpl.getInstance();
	
	public static void pdfRun(Map<String, String> params){
		List<RentalVO> rentalList = irts.readRentalList(params.get("mem_id"));
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

			doc.add(new Paragraph("대출리스트", cellTitleFont));
			doc.add(new Paragraph(" "));

			PdfPTable table1 = new PdfPTable(5);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);
			table1.setTotalWidth(500f);
			table1.setLockedWidth(true);

			PdfPCell cell = null;

			PdfPTable table2 = new PdfPTable(5);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			table2.setTotalWidth(500f);
			table2.setLockedWidth(true);

			// row 1
			cell = new PdfPCell(new Paragraph("대여번호", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("회원ID", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("도서명", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("대여시작일", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			cell = new PdfPCell(new Paragraph("대여종료일", cellTitleFont));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setFixedHeight(21);
			table2.addCell(cell);

			for(RentalVO rv : rentalList){
				BookVO bv = ibs.readBook(rv.getBook_id());
				
				cell = new PdfPCell(new Paragraph(String.valueOf(rv.getRental_id()),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(rv.getMem_id(),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(String.valueOf(bv.getBook_name()),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(rv.getRental_start().substring(0,10),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
				
				cell = new PdfPCell(new Paragraph(rv.getRental_end().substring(0,10),
						cellNormalFont));
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setFixedHeight(21);
				table2.addCell(cell);
			}

			doc.add(table2);
			doc.close();
			
			DEST += params.get("rPath") + ".pdf";
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