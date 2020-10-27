package gameComponents;

import enums.TreasureEnum;
import enums.TreasureCardEnum;

public class TreasureCard extends AbstractTreasureCard {

	private TreasureEnum treasureType;
	
	public TreasureCard(TreasureEnum treasureType) {
		
		super(TreasureCardEnum.TREASURE);
		this.treasureType = treasureType;
		
	}
	
	public TreasureEnum getTreasureType() {
		
		return treasureType;
		
	}
	
	@Override
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append(super.toString());
		temp.append("; Treasure: " + treasureType.toString());
		return temp.toString();
	
	}
	
}
