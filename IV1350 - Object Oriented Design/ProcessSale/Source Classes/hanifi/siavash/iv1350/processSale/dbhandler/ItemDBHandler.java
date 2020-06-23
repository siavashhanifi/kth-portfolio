package hanifi.siavash.iv1350.processSale.dbhandler;
import hanifi.siavash.iv1350.processSale.data.ItemDB;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;

public class ItemDBHandler {

	/**
	 * Hard-coded simulation of the fetching of an item in the database containing items.
	 *  
	 * @param itemId The identifier for the searched item.
	 * @return The identified <code>Item</code>-object.
	 */
	public ItemDTO fetchItem(int itemId) throws ItemNotFoundException{
		if(itemId == 647474) {
			return  ItemDB.Banana;
		}
		if(itemId == 576483) {
			return ItemDB.Strawberry;
		}
		else throw new ItemNotFoundException(itemId);
	}
	
}
