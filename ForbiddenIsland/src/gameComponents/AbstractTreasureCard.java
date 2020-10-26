package gameComponents;

import enums.TreasureCardEnum;

public abstract class AbstractTreasureCard {

	/**
	 * Class which will maintain different treasure cards in Forbidden Island
	 * @author Daniel McManus
	 * @version 0.1
	 *
	 */
	
	private TreasureCardEnum cardType;
	
	public AbstractTreasureCard(TreasureCardEnum cardType) {
		
		this.cardType = cardType;
		
	}
	
	public TreasureCardEnum getCardType() {
		
		return cardType;
		
	}
	
	public String toString() {
		
		StringBuilder temp = new StringBuilder("");
		temp.append("Card: " + cardType.toString());
		return temp.toString();
		
	}
	
}
