package hanifi.siavash.iv1350.processSale.externalSystemCommunication;

import hanifi.siavash.iv1350.processSale.data.SaleDTO;
import hanifi.siavash.iv1350.processSale.model.Payment;
/**
 * Communicates with the external accounting system.
 * @author Siavash
 *
 */
public class ExternalAccountingSystemHandler {
	/**
	 * Represents a call to the external accounting system to add a sale transaction to the bookkeeping.
	 * @param loggedSale The logged and completed sale.
	 * @param payment
	 */
	public void addSaleTransaction(SaleDTO loggedSale, Payment payment) {
		System.out.println();
		System.out.println("External Accounting System: Sale transaction has been stored at date: " + "\n" + loggedSale.getDate().toString());
		System.out.println("Debit(CashAccount): " + payment.getTotalTaxInc() + " Credit(Sale): " + payment.getTotalTaxExcl() + " Credit(Tax): " + payment.getTaxAmount());
	}
	
}
