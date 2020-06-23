package hanifi.siavash.iv1350.processSale.data;
import java.util.Date;
import java.util.Vector;

import hanifi.siavash.iv1350.processSale.model.Sale;

/**
 * Holds information about a completed sale.
 * @author Siavash
 *
 */
public class SaleDTO {
	
	private Date date;
	private Vector<ItemDTO> listOfItems = null;
	private double runningTotal;

	/**
	 * Copies information from the ongoing sale and stores them into a new <code>SaleDTO</code>-object.
	 * @param sale Reference to the ongoing <code>Sale</code>
	 */
	public SaleDTO(Sale sale) {
		this.date = sale.getDate();
		this.listOfItems = sale.getListOfItems();
		this.runningTotal = sale.getRunningTotal();
	}
	
	public Vector<ItemDTO> getListOfItems(){
		return this.listOfItems;
	}
	
	public double getRunningTotal() {
		return this.runningTotal;
	}
	
	public Date getDate() {
		return this.date;
	}
}
