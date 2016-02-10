package microserver;
/*--------------------------------------------------------

1. Steven Kiley / Date: 5/1/2015

2. Built using Java 8.

3. Compile using the following command line.

> javac MyWebServer.java


4. Do the following to run this program:

      After following previous compilation step, in a shell window:

> java MyWebServer

      To connect, open Mozilla Firefox web-browser and enter the following in the URL bar:

localhost:2540

      Use hotlinked text to navigate through available files and file structure.
      To utilize addnums feature, enter the following in the URL bar:
      
localhost:2540/addnums

      Enter valid information into form fields on resulting page and click "Submit Numbers" to activate server side processing.

      NOTE: If server is running on separate machine, entering that machines IP address in place of "localhost" will be necessary.

5. List of files needed for running the program.

a. MyWebServer.java
b. Valid installation of Mozilla Firefox.

6. Notes: 

Does not contain parent directory as part of the folder navigation, navigating back up from subfolders requires using the back button.

----------------------------------------------------------*/
import java.io.*; // Get the Input Output libraries
import java.net.*; // Get the Java networking libraries
import java.util.ArrayList;
import java.util.Iterator;
import serializer.Serializer;
import initializer.OpenHTML;
import initializer.PageBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import mechdata.Mech;
import mechdata.MechContainer;
import mechdata.Variant;
import serializer.Unserializer;
import userinterface.UserInterface;


class WebWorker extends Thread { // Class definition
	Socket mySocket; // Java API writeup calls the socket a communication endpoint. Streamlines sending data between two processes or systems
	MechContainer user;
	MechContainer all;
	SynchronizedCounter count;
	WebWorker (Socket s, MechContainer u, MechContainer a, SynchronizedCounter c) {mySocket = s; user=u; all = a; count=c;} // WebWorker does all handling for incoming requests.
	public void run(){
		// Here comes the interesting work of figuring out precisely what the HTTP request from the browser is meant to grab.
		PrintStream output = null;
		BufferedReader input = null;
		try {
			input = new BufferedReader
					(new InputStreamReader(mySocket.getInputStream()));
			output = new PrintStream(mySocket.getOutputStream());
			// Read first line of HTTP request to get relevant information
			try {
				String response;
				response = input.readLine();
				if(response.equals("GET /?finisher= HTTP/1.1")){
                                        user = Unserializer.readFile("src\\initializer\\USER_MECHS.SER");
					UserInterface ui = new UserInterface(user);
                                        count.incrementCounter();
                                        ui.run();
				}
				if(response.equals("GET /?starter= HTTP/1.1")){
					count.incrementCounter();
					sendHtml(output, "light");
				}
				if(!response.equals("GET /favicon.ico HTTP/1.1") && !response.equals("GET /?starter= HTTP/1.1")){
					count.incrementCounter();
					response = getResults(response); 
					String[] splitResponse = response.split("&");
					if(splitResponse[splitResponse.length - 1].equals("light")){processResponse(splitResponse); sendHtml(output, "medium");}
					if(splitResponse[splitResponse.length - 1].equals("medium")){processResponse(splitResponse); sendHtml(output, "heavy");}
					if(splitResponse[splitResponse.length - 1].equals("heavy")){processResponse(splitResponse); sendHtml(output, "assault");}
					if(splitResponse[splitResponse.length - 1].equals("assault")){
						processResponse(splitResponse); 
						OpenHTML.openPage("src\\initializer\\Finisher_Page.html");
						writeUserMechsToFile();
					}
				}
				
			} catch (IOException x) {
				System.out.println("Server read error");
				x.printStackTrace ();
			} catch (LineUnavailableException ex) {
                        Logger.getLogger(WebWorker.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(WebWorker.class.getName()).log(Level.SEVERE, null, ex);
                    }
			mySocket.close(); // Close the communication endpoint, a non-persistent connection;
		} catch (IOException ioe) {System.out.println(ioe);}
	}//END OF RUN METHOD
	
	//Starter method for writing user responses to file for future runs of this application.
	private void writeUserMechsToFile() throws IOException {
		MechContainer correctedMechs = new MechContainer();
		Iterator keys = this.user.getKeys().iterator();
		while(keys.hasNext()){
			Mech m = this.user.getMech((Integer) keys.next());
			ArrayList<Variant> vars = m.getVariants();
			for(int i = 0; i < vars.size(); i++){
				Variant v = vars.get(i);
				correctedMechs.addMech(m, v);
			}
		}
		String fileLocation ="src\\initializer\\USER_MECHS.SER";
		File file = new File(fileLocation);
		if(file.exists()){file.delete();}
		Serializer.serializeMechContainer(fileLocation, correctedMechs);
	}
	
	//Helper method to process a form response
	private void processResponse(String[] response){
		for(int i = 0; i < response.length; i++){
			String[] singleMech = response[i].split("-");
			for(int k = 0; k < singleMech.length; k++){
			}
			processResponseHelper(singleMech);
		}
	}
	
	
	private void processResponseHelper(String[] singleMech){
		if(singleMech.length == 2){	
			int mechNum = Integer.parseInt(singleMech[0]);
			String[] isolateNum = singleMech[1].split("=");
			int varNum = Integer.parseInt(isolateNum[0]);
			Mech mech = this.all.getMech(mechNum);
			Variant var = mech.getVariants().get(varNum);
			this.user.addMech(mech, var);
		}		
	}
	
	//Helper method for returning a .html file to user
	private void sendHtml(PrintStream output, String wc) throws FileNotFoundException{
		output.println("HTTP/1.1 200 OK");
		output.println("Content-length: 60000");
		output.println("Content-Type: text/" + "html" + "\n\n");
		int clanNum = 2000;
		int isNum = 1000;
		if(wc.equals("light")){clanNum += 100; isNum +=100;}
		if(wc.equals("medium")){clanNum += 200; isNum +=200;}
		if(wc.equals("heavy")){clanNum += 300; isNum +=300;}
		if(wc.equals("assault")){clanNum += 400; isNum +=400;}
		int clanLimit = this.all.getClanNum(wc);
		int isLimit = this.all.getISNum(wc);
		ArrayList<Mech> isMechs = new ArrayList<Mech>();
		ArrayList<Mech> clanMechs = new ArrayList<Mech>();
		Mech temp = null;
		int key = 0;
		
		//Add all IS mechs of appropriate weight class to the list
		for(int i = 1; i <= isLimit; i++){
			key = isNum + i;
			temp = all.getMech(key);
			isMechs.add(temp);
		}
		
		//Add all Clan mechs of appropriate weight class to the list
		for(int i = 1; i <= clanLimit; i++){
			key = clanNum + i;
			temp = all.getMech(key);
			clanMechs.add(temp);
		}
		
		PageBuilder.buildPage(output, isMechs, clanMechs, wc);
	}
	
	
	//Splits up first line of HTTP request in order to locate the name of the requested file or folder
	private String getResults(String parseMe){
		String details = null;
		String[] chars = parseMe.split("\\s");
		String z = chars[1];
		int b = z.length();
		details = z.substring(2, b-1);
		return details;
	}
	
	
	
}//END OF WEBWORKER

//Simple class that waits for connections and spawns WebWorker threads to handle them as they come in.
public class SmallWebServer extends Thread{
	    int q_len;
	    int myPort;
	    Socket secondSocket;
	    ServerSocket servsock;
	    MechContainer userMechs;
	    MechContainer allMechs;
	    SynchronizedCounter count;
	    
	public SmallWebServer(MechContainer u, MechContainer a){this.userMechs = u; this.allMechs = a;}
	    
	public void run() {
		q_len = 6; /* Limits number of simultaneous requests to six */
		myPort = 8080;
		secondSocket = new Socket();  // <--Communication endpoint
		count = new SynchronizedCounter();
		try {
			servsock = new ServerSocket(myPort, q_len);
		                        // Synchronous activities below:
			
			while (count.getCountValue() < 6) {
				secondSocket = servsock.accept();
				new WebWorker(secondSocket, userMechs, allMechs, count).start(); // Spawn worker to handle it
				Thread.sleep(100);
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} // wait for the next client connection
			
	}
}
