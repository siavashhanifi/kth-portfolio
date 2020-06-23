package hanifi.siavash.iv1350.processSale.model;

/**
 * A digital representation of cash.
 * @author Siavash
 *
 */
public class CashAmount {
	
	private double cashAmount;
	
	private double twoDecimalPrecisionLimiter(double cashAmount) {
		return (double)Math.round(cashAmount*100.00)/100.00;
	}
	
	/**
	 * Stores an amount of money, represented as cash.
	 * @param cashAmount
	 */
	public CashAmount(double cashAmount){
		this.cashAmount = twoDecimalPrecisionLimiter(cashAmount);
	}
	
	public double getCashAmount(){
		return this.cashAmount;
	}
}
