package hanifi.siavash.iv1350.processSale.dbhandler;

public class ItemNotFoundException extends Exception{
	private int itemId;
	
	public ItemNotFoundException(int itemId) {
		super("Item with id: " + itemId + " could not be retrived from database");
		this.itemId	= itemId;
	}
	
	public int getItemId(){
		return this.itemId;
	}
}
