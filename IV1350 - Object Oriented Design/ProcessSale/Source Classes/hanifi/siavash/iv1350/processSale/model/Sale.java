package hanifi.siavash.iv1350.processSale.model;
import java.util.Vector;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;
import java.util.Date;
/**
 * Contains functionality and information of the ongoing sale.
 * @author Siavash
 *
 */
public class Sale {
	
	private Date date;
	private Vector<ItemDTO> listOfItems = new Vector<ItemDTO>();
	private double runningTotal = 0;
	
	private void calculateRunningTotal(){
		this.runningTotal += listOfItems.lastElement().getPrice();
	}
	/**
	 * Initializes and instantiates a new sale.
	 */
	public Sale() {
		this.date = new Date();
	}
	
	/**
	 * Registers an item to the ongoing sale.
	 * @param scannedItem
	 */
	public void registerItem(ItemDTO scannedItem) {
		listOfItems.addElement(scannedItem);
		calculateRunningTotal();
	}

	public double getRunningTotal() {
		// TODO Auto-generated method stub
		return this.runningTotal;
	}

	public Vector<ItemDTO> getListOfItems() {
		// TODO Auto-generated method stub
		return this.listOfItems;
	}
	
	public Date getDate() {
		return this.date;
	}
}
