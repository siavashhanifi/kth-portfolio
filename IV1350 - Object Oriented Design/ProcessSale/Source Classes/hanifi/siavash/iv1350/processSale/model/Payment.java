package hanifi.siavash.iv1350.processSale.model;
/**
 * Stores information of the required payment for the sale.
 * @author Siavash
 */
public class Payment {
	
	private double totalTaxExcl = 0;
	private double taxAmount;
	
	/**
	 * Copies cost information of the tax and sale.
	 * @param tax The <code>Tax</code>-object
	 * @param sale Reference to the ongoing <code>Sale</code>
	 */
	public Payment(Tax tax, Sale sale) {
		// TODO Auto-generated constructor stub
		this.totalTaxExcl = sale.getRunningTotal();
		this.taxAmount = tax.getTaxAmount();
	}
	
	public double getTotalTaxExcl() {
		return this.totalTaxExcl;
	}
	
	public double getTaxAmount() {
		return this.taxAmount;
	}
	
	/**
	 * Adds <code>totalTaxExcl</code>and <code>taxAmount</code> together.
	 * @return The sum.
	 */
	public double getTotalTaxInc() {
		return this.totalTaxExcl + this.taxAmount;
	}
	

}
