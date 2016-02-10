package serializer;

/**
 * 
 * @author Steven Kiley
 *
 */

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import mechdata.MechContainer;

public class Unserializer {

	
	public static MechContainer readFile(String fileName) throws IOException, ClassNotFoundException{
		boolean test = true;
		Object obj = null;
		MechContainer temp = null;
		try{
			FileInputStream fileIn = new FileInputStream(fileName);
			ObjectInputStream ois = new ObjectInputStream(fileIn);  
			while(test == true){
				obj = ois.readObject();
				temp = (MechContainer)obj;
			}
			ois.close();
			fileIn.close();
		   }catch(EOFException ex){
			   test = false;
		   }
		return temp;
	}
}
