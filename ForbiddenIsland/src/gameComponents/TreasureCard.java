package gameComponents;

import enums.TreasureCardEnum;

public class TreasureCard {
	
	/**
	 * Class which will maintain different treasure cards in Forbidden Island
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */
	
	private TreasureCardEnum type;
	
	public TreasureCard(TreasureCardEnum type) {
		
		this.type = type;
		
	}
	
	public TreasureCardEnum getType() {
		
		return type;
		
	}
	
}
