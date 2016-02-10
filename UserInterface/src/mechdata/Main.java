package mechdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import serializer.Serializer;
import serializer.Unserializer;
import initializer.OpenHTML;
import javax.sound.sampled.LineUnavailableException;
import userinterface.UserInterface;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException, URISyntaxException, ClassNotFoundException, LineUnavailableException {
		String runFile = "src\\initializer\\USER_MECHS.SER";
		File hasRun = new File(runFile);
        MechContainer userMechs = null;
		if (hasRun.exists()) {
			userMechs = Unserializer.readFile(runFile);
                        UserInterface ui = new UserInterface(userMechs);
                        ui.run();
		}
		if (!hasRun.exists()) {
			String fileLoc = ".\\src\\initializer\\ALL_MECHS.SER";
			MechContainer allMechs = Unserializer.readFile(fileLoc);
			userMechs = new MechContainer();
			new microserver.SmallWebServer(userMechs, allMechs).start();
			OpenHTML.openPage("src\\initializer\\Starter_Page.html");
		}
		//newMechsFromPGI(); 
	}
	
        
        
	/**
	 * Method for adding new mechs to Mech Chooser as new mechs are added to the game.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private static void newMechsFromPGI() throws FileNotFoundException, IOException{
		String textMechs = ".\\src\\initializer\\ALL_MECHS.TXT";
		String serialMechs = "src\\initializer\\ALL_MECHS.SER";
		MechContainer allMechs = new MechContainer(textMechs); 
		Serializer.serializeMechContainer(serialMechs, allMechs);
	}

}
