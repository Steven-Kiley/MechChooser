package mechdata;

import java.io.Serializable;

/**
 * 
 * @author Steven Kiley
 *
 */

 public class Variant implements Serializable{
	private static final long serialVersionUID = 1L;
	private String varName;
	private int varCount;
	
	Variant(String name, int count){
		this.varName = name;
		this.varCount = count;
	}
	
	public int getVarCount(){
		return this.varCount;
	}
	
	public String getVarName(){
		return this.varName;
	}
}
