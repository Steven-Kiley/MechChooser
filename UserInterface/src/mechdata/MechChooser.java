package mechdata;

import java.util.Random;

/**
 * 
 * @author Steven Kiley
 *
 */

public class MechChooser {
	
	public static Integer chooseRandomMech(ChoiceData cd){
		Random rand = new Random();
		int thousands = cd.faction[rand.nextInt(cd.faction.length)];
		int hundreds = cd.weightClass[rand.nextInt(cd.weightClass.length)];
		int tensChoices = cd.numChoices[hundreds - 1][thousands - 1];
		int tens = rand.nextInt(tensChoices) + 1;
		int result = (thousands * 1000) + (hundreds * 100) + tens;
		return result;
	}

}
