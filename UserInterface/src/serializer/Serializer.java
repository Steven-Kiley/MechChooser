package serializer;

/**
 * 
 * @author Steven Kiley
 *
 */

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import mechdata.MechContainer;

public class Serializer {
	public static boolean serializeMechContainer(String filePath, MechContainer mechs){
		   boolean attempt = false;
		   try{
			   FileOutputStream fout = new FileOutputStream(filePath);
			   ObjectOutputStream oos = new ObjectOutputStream(fout);
			   Object obj = (Object)mechs;
			   oos.writeObject(obj);
			   oos.close();
			   attempt = true;
			   return attempt;
		   }catch(Exception ex){
			   ex.printStackTrace();
			   return attempt;
		   }
	   	}
}
