package gameComponents;

import enums.TreasureEnum;
import enums.TreasureCardEnum;

public class TreasureCard extends AbstractTreasureCard {

	private TreasureEnum treasureType;
	
	public TreasureCard(TreasureCardEnum cardType, TreasureEnum treasureType) {
		
		super(cardType);
		this.treasureType = treasureType;
		
	}
	
	public TreasureEnum getTreasureType() {
		
		return treasureType;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append(super.toString());
		temp.append("\nTreasure: " + treasureType.toString());
		return temp.toString();
	
	}
	
}
