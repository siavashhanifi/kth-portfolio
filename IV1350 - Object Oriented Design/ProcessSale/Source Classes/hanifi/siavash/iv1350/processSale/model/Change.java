package hanifi.siavash.iv1350.processSale.model;
/**
 * Represents the change that is to be given back to the customer.
 * @author Siavash
 */
public class Change {
	
	private CashAmount changeAmount;
	
	private CashAmount calculateChange(CashAmount cashAmount, Payment payment) {
		return new CashAmount(cashAmount.getCashAmount()-payment.getTotalTaxInc());
	}
	
	/**
	 * Stores information of the amount of change.
	 * @param cashAmount The amount of money received from the customer.
	 * @param payment The required payment.
	 */
	public Change(CashAmount cashAmount, Payment payment) {
		// TODO Auto-generated constructor stub
		this.changeAmount = calculateChange(cashAmount, payment);
	}
	
	public CashAmount getChangeAmount() {
		return this.changeAmount;
	}
}
//STOPPED HERE, PICKUP QUESTION: HOW TO IMPLEMENT CashAmount and Change in the program?
