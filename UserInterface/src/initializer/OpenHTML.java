package initializer;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


public class OpenHTML {
	public static void openPage(String url) throws IOException{
		File htmlFile = new File(url);
		String filePath = htmlFile.getAbsolutePath();
		File finalFile = new File(filePath);
		Desktop.getDesktop().browse(finalFile.toURI());
	}
}
