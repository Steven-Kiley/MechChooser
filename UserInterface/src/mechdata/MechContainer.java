package mechdata;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class MechContainer implements Serializable{
	private static final long serialVersionUID = -6215869551534123183L;
	HashMap<Integer, Mech> mechs;
	int isLights;
	int clanLights;
	int isMeds;
	int clanMeds;
	int isHeavies;
	int clanHeavies;
	int isAss;
	int clanAss;
	 
	/** Constructor method used in creating the serialized file ALL_MECHS.SER. To be used again
	 *  when an update exists, reads from a simple text file containg one mech per line in the following 
	 *  format: 1*1*Commando*COM-1B+COM-3A+COM-2D+COM-TDK
	  * 
	  * @param fileLocation
	  * @throws FileNotFoundException
	  * @throws IOException
	  */
	MechContainer(String fileLocation) throws FileNotFoundException, IOException{
		this.mechs = new HashMap<Integer, Mech>();
		this.isLights = 0;
		this.clanLights = 0;
		this.isMeds = 0;
		this.clanMeds = 0;
		this.isHeavies = 0;
		this.clanHeavies = 0;
		this.isAss = 0;
		this.clanAss = 0;
		
		String everything = "";
		try(BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
			StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    while (line != null) {
				sb.append(line);
			    sb.append(System.lineSeparator());
				 line = br.readLine();
		    }
		    everything = sb.toString();
		}
		String[][] supersplit = splitter(everything);
		processMechs(supersplit);
	}
	
	public MechContainer(){
		this.mechs = new HashMap<Integer, Mech>();
		this.isLights = 0;
		this.clanLights = 0;
		this.isMeds = 0;
		this.clanMeds = 0;
		this.isHeavies = 0;
		this.clanHeavies = 0;
		this.isAss = 0;
		this.clanAss = 0;
	}
	
	/**
	 * Helper method for above constructor, only used to help create ALL_MECHS.SER
	 * @param a
	 */
	private void processMechs(String[][] a){
		for(int i = 0; i < a.length; i++){
			int useCount = 0;
			String[] useMe = a[i];
			int faction = Integer.parseInt(useMe[0]);
			int wc = Integer.parseInt(useMe[1]);
			String chassis = useMe[2];
			String[] vars = splitVars(useMe[3]);
			if(faction == 1){useCount = incrementISCounter(wc);}
			if(faction == 2){useCount = incrementClanCounter(wc);}
			MechImpl mech = new MechImpl(faction, wc, chassis, useCount);
			for(int j = 0; j < vars.length; j++){
				mech.addVariant(vars[j]);
			}
			mechs.put(mech.getHashCode(), mech);
		}
	}
	
	private int incrementClanCounter(int wc){
		if(wc == 1){this.clanLights++; return this.clanLights;}
		if(wc == 2){this.clanMeds++; return this.clanMeds;}
		if(wc == 3){this.clanHeavies++; return this.clanHeavies;}
		else{this.clanAss++; return this.clanAss;}
	}
	
	private int incrementISCounter(int wc){
		if(wc == 1){this.isLights++; return this.isLights;}
		if(wc == 2){this.isMeds++; return this.isMeds;}
		if(wc == 3){this.isHeavies++; return this.isHeavies;}
		else{this.isAss++; return this.isAss;}
	}
	
	/**
	 * Helper method for above contructor, used to help create ALL_MECHS.SER file.
	 * @param s
	 * @return
	 */
	private static String[][] splitter(String s){
		String[] split1 = s.split("\\n");
		String[][] split2 = new String[split1.length][];
		for(int i = 0; i < split1.length; i++){
			String splitMe = split1[i];
			String[] afterSplit = splitMe.split("\\*");
			split2[i] = afterSplit;
		}
		return split2;
	}
	
	/**
	 * Helper method for above constructor, used to help create ALL_MECHS.SER
	 * @param s
	 * @return
	 */
	private static String[] splitVars(String s){
		String[] a = s.split("\\+");
		return a;
	}
	
	/**
	 * Gets ChoiceData object based on an instance of MechContainer, resulting object to be passed 
	 * to MechChoose static method for choosing a random mech.
	 * @param faction
	 * @param wc
	 * @return
	 */
	public ChoiceData getRandomChoiceData(int[] faction, int[] wc){
		int[][] allChoices = choiceDataHelper();
		ChoiceData choiceData = new ChoiceData(faction, wc, allChoices);
		return choiceData;
	}
	
	/**
	 * Helper method for getRandomChoiceData. Constructs a 2dimensional array, the first dimension representing 
	 * the four classes of mechs and the second represeting the two factions.
	 * @return
	 */
	private int[][] choiceDataHelper(){
		int[] lights = new int[]{this.isLights, this.clanLights};
		int[] meds = new int[]{this.isMeds, this.clanMeds};
		int[] heavies = new int[]{this.isHeavies, this.clanHeavies};
		int[] assaults = new int[]{this.isAss, this.clanAss};
		int[][] all = new int[][]{lights,meds,heavies,assaults};
		return all;
	}
	
	/**
	 * Simple accessor method to return a given mech based on its hashcode.
	 * @param i
	 * @return
	 */
	public Mech getMech(Integer i){return this.mechs.get(i);}
	
	/**
	 * Method only used for first time run of application. Used in storing user responses that are then written to file.
	 * This method is not reliable for continuous use as the hashcode numbers will not match up to any information stored 
	 * in file. Do not use after first run. Using this method with a MechContainer that has existing information from
	 * file will result in unpredictable behavior.
	 * @param mech
	 * @param var
	 */
	public void addMech(Mech mech, Variant var){
		Mech temp = null;
		if(this.mechs.containsKey(mech.getHashCode())){
			temp = this.getMech(mech.getHashCode());
			temp.addVariant(var.getVarName());
		}
		if(!this.mechs.containsKey(mech.getHashCode())){
			int f = mech.getFaction();
			int wc = mech.getWeightClass();
			int c = 0;
			if(f == 1){c = incrementISCounter(wc);}
			if(f == 2){c = incrementClanCounter(wc);}
			temp = new MechImpl(f, wc, mech.getChassisName(), c);
			temp.addVariant(var.getVarName());
			this.mechs.put(mech.getHashCode(), temp);
		}
	}
	
	public Set getKeys(){
		return this.mechs.keySet();
	}
	
	
	public int getClanNum(String wc){
		if(wc.equals("light")){return this.clanLights;}
		if(wc.equals("medium")){return this.clanMeds;}
		if(wc.equals("heavy")){return this.clanHeavies;}
		if(wc.equals("assault")){return this.clanAss;}
		else{return 0;}
	}
	
	public int getISNum(String wc){
		if(wc.equals("light")){return this.isLights;}
		if(wc.equals("medium")){return this.isMeds;}
		if(wc.equals("heavy")){return this.isHeavies;}
		if(wc.equals("assault")){return this.isAss;}
		else{return 0;}
	}
	
}
