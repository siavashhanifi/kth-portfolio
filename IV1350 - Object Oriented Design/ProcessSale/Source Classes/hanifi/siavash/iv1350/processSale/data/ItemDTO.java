package hanifi.siavash.iv1350.processSale.data;
/**
 * Stores information about a specific merchandise 
 * @author Siavash
 *
 */
public class ItemDTO {

	private int id;
	private double price;
	private String itemDescription;
	
	public ItemDTO(int id, double price, String itemDescription) {
		this.id = id;
		this.price = price;
		this.itemDescription = itemDescription;
	}
	
	public int getId() {
		return this.id;
	}

	public double getPrice() {
	return this.price;
	}

	public String toString() {
		return this.itemDescription;
	}
	

}
