package initializer;

import java.io.PrintStream;
import java.util.ArrayList;
import mechdata.Mech;
import mechdata.Variant;

public class PageBuilder {
	public static void buildPage(PrintStream output, ArrayList<Mech> isMechs, ArrayList<Mech> clanMechs, String wc){
		String returnMe = getHTMLStart(wc) + buildBody(isMechs, clanMechs, wc) + getFooter();
		output.println(returnMe);
		
	}
	
	private static String buildBody(ArrayList<Mech> isMechs, ArrayList<Mech> clanMechs, String wc){
		Mech temp = null;
		String bodyStart = "<div class=\"wrapper\">\n"
				+ "<form action=\"http://localhost:8080\">\n";
		
		//Create the dynamic HTML to be returned to client
		String bodyMain = "<table border=\"1\" style=\"width:70%\"><caption><h3><strong> CHECK OFF ANY INNER SPHERE " + wc.toUpperCase() + "S YOU OWN" + "</strong></h3></caption>\n";
		
		//Loop for Inner Sphere mechs
		for(int i = 0; i < isMechs.size(); i++){
			temp = isMechs.get(i);
			bodyMain = bodyMain + " " + "<tr><td align=right><strong><font color=#000000>" + temp.getChassisName() + "</font></strong></td>\n\n";
			ArrayList<Variant> vars = temp.getVariants();
			for(int j = 0; j < vars.size(); j++){
				Variant v = vars.get(j);
				String mechNum = temp.getHashCode() + "-" + j;
				bodyMain = bodyMain + " " + "<td align=left><input type=\"checkbox\" name=\"" + mechNum + "\"><font color=#980909>" + v.getVarName() + "</td>\n";
			}
			bodyMain = bodyMain + " " + "</tr>\n";
		}
		bodyMain = bodyMain + " " + "</table>\n<br>\n<br>\n";
		bodyMain = bodyMain + " " + "<table border=\"1\" style=\"width:70%\"><caption><h3><strong>CHECK OFF ANY CLAN " + wc.toUpperCase() + "S YOU OWN" + "</strong></h3></caption>\n";
    
    	//Loop for Clan mechs
    	for(int i = 0; i < clanMechs.size(); i++){
    		temp = clanMechs.get(i);
    		bodyMain = bodyMain + " " + "<tr><td align=right><strong><font color=#000000>" + temp.getChassisName() + "</font></strong></td>\n\n";
    		ArrayList<Variant> vars = temp.getVariants();
    		for(int j = 0; j < vars.size(); j++){
    			Variant v = vars.get(j);
    			String mechNum = temp.getHashCode() + "-" + j;
    			bodyMain = bodyMain + " " + "<td align=left><input type=\"checkbox\" name=\"" + mechNum + "\"><font color=#980909>" + v.getVarName() + "</td>\n";
    		}
    		bodyMain = bodyMain + " " + "</tr>\n";
    	}
    	bodyMain = bodyMain + " " + "</table>\n<br>\n<br>\n";
		
		String bodyFinish =	"<button name=\"" + wc + "\" type=\"submit\">SUBMIT " + wc.toUpperCase() + "!</button>\n"
				+"</form></div>";
		
		String finalResult = bodyStart + " " + bodyMain + " " + bodyFinish;
		return finalResult;
	}//END buildBody() METHOD
	
	
	
	private static String getHTMLStart(String wc){
		String start = "<!DOCTYPE html>"
				+ "<html>\n"
				+ "<head>\n"
				+ "<title>Mech Chooser</title>\n"
				+ "</head>\n"
				+ "<body background=\"" + getBackgroundPic(wc) + "\" bgproperties=\"fixed\">\n" 
				+ "<style>\n"
				+ "html {\n"
				+ "height: 100%;\n"
				+ "box-sizing: border-box;\n"
				+ "}\n"
				+ "*,\n"
				+ "*:before,\n"
				+ "*:after {\n"
				+ "box-sizing: inherit;"
				+ "}\n\n"
				+ "body {\n"
				+ "position: relative;\n"
				+ "margin: 0;\n"
				+ "padding-bottom: 6rem;\n"
				+ "min-height: 100%;\n"
				+ "font-family: \"Helvetica Neue\", Arial, sans-serif;\n"
				+ "}\n"
				+ ".demo {\n"
				+ "margin: 0 auto;\n"
				+ "padding-top: 64px;\n"
				+ "max-width: 640px;\n"
				+ "width: 94%;\n"
				+ "}\n"
				+ ".demo h1 {\n"
				+ "margin-top: 0;\n"
				+ "}\n"
				+ ".footer {\n"
				+ "position: absolute;\n"
				+ "right: 0;\n"
				+ "bottom: 0;\n"
				+ "left: 0;\n"
				+ "padding: 1rem;\n"
				+ "background-color: #151515;\n"
				+ "text-align: center;\n"
				+ "}\n"
				+ ".wrapper {\n"
				+ "text-align: center;\n"
				+ "}\n"
				+ ".button {\n"
				+ "position: absolute;\n"
				+ "top: 50%;\n"
				+ "}\n"
				+ "</style>\n\n";
		return start;
	}
	
	private static String getFooter(){
		String footer = "<div class=\"footer\"><font color = \"red\">MechWarrior Online is a registered trademark of Piranha Games Inc.\n"
				+ "<img src=\"https://upload.wikimedia.org/wikipedia/en/thumb/c/c4/PGI_MechWarrior_Online_Logo.png/250px-PGI_MechWarrior_Online_Logo.png\" alt=\"Mechs\" align=\"center\">\n"
				+ "<font color = \"red\">Mech Chooser is a free product made by Steven Kiley.</div>\n";
		return footer;
	}
	
	private static String getBackgroundPic(String wc){
		if(wc.equals("light")){return "http://www.dsogaming.com/wp-content/uploads/2013/01/deaths-knell.jpg";}
		if(wc.equals("medium")){return "http://images9.gry-online.pl/galeria/galeria_duze3/429988328.jpg";}
		if(wc.equals("heavy")){ return "http://i.imgur.com/WJIaM.jpg";}
		else{return "http://i45.tinypic.com/15fr406.png";}
	}
}
