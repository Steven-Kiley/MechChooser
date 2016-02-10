package mechdata;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
 * @author Steven Kiley
 *
 */

class MechImpl implements Mech, Serializable{
	private static final long serialVersionUID = -7851808017652121852L;
	private int faction;
	private int chassisNum;
	private String chassisName;
	private int weightClass;
	private int varCount;
	private ArrayList<Variant> vars;
	
	MechImpl(int f, int wc, String name, int cNum){
		this.faction = f;
		this.chassisNum = cNum;
		this.chassisName = name;
		this.weightClass = wc;
		this.varCount = 0;
		this.vars = new ArrayList<Variant>();
	}

	public void addVariant(String var) {
		Variant v = new Variant(var, varCount);
		this.vars.add(v);
		this.varCount++;
	}

	public int getVarCount() {return this.varCount;}

	public String getChassisName() {return this.chassisName;}

	public int getHashCode() {return this.hashCode();}

	public ArrayList<Variant> getVariants() {return this.vars;}
	
	@Override
    public int hashCode() {
		int accum = this.faction * 1000;
		accum += this.weightClass * 100;
		accum += this.chassisNum ;
		return accum;
	}
	
	@Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        else{
        	Mech testMe = (Mech)obj;
        	if(testMe.getChassisName().equals(this.getChassisName())){return true;}
        	else{return false;}
        }
	}

	public int getFaction() {return this.faction;}

	public int getWeightClass() {return this.weightClass;}
	
	
}
