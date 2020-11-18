package pdf;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class Watermark {

	public void manipulatePdf(String src, String DEST) {
		try {
			PdfReader reader = new PdfReader(src);
			reader.getPageSize(1);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					DEST));
			Font f = new Font(FontFamily.HELVETICA, 20);
			int page = reader.getNumberOfPages();
			int num = 0;
			for (int i = 1; i <= page; i++) {
				num = i;

				PdfContentByte over = stamper.getOverContent(num);
				Phrase p = new Phrase("J-J-U-G-U-R-I", f);
				over.saveState();
				PdfGState gs = new PdfGState();
				gs.setFillOpacity(0.5f);
				over.setGState(gs);
				ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, 300,
						400, 0);
				over.restoreState();
			}
				stamper.close();
				reader.close();
		} catch (IOException e) {
		} catch (com.itextpdf.text.DocumentException e2) {
		}
	}
}