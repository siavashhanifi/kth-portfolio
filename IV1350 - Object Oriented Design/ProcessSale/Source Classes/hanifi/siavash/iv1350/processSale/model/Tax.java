package hanifi.siavash.iv1350.processSale.model;

/**
 * Calculates and stores the amount of tax that is to be added to a sale.
 * @author Siavash
 *
 */
public class Tax {
	
	private double taxAmount = 0;
	
	private double applyTaxFormula(Sale sale) {
		double taxAmount = 0;
		taxAmount += sale.getRunningTotal() * 0.12;
		return	taxAmount;
	}
	
	/**
	 * Calculates the tax on the ongoing sale.
	 * @param sale The <code>Sale</code>-object which the tax is to be calculated upon.
	 */
	public Tax(Sale sale){
	this.taxAmount = applyTaxFormula(sale);
	}


	public double getTaxAmount(){
		return this.taxAmount;
	}

}
