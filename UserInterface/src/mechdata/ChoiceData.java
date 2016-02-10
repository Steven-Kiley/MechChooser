package mechdata;

/**
 * 
 * @author Steven Kiley
 *
 */

public class ChoiceData {
	int[] faction;
	int[] weightClass;
	int[][] numChoices;
	
	ChoiceData(int[] f, int[] wc, int[][] num){
		this.faction = f;
		this.weightClass = wc;
		this.numChoices = num;
	}
}
