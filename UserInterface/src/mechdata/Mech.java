package mechdata;

import java.util.ArrayList;

public interface Mech {
	public void addVariant(String var);
	
	public int getVarCount();
	public String getChassisName();
	public int getHashCode();
	public ArrayList<Variant> getVariants();
	public int getFaction();
	public int getWeightClass();
}
