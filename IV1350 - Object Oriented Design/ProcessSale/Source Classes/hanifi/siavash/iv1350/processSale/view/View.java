package hanifi.siavash.iv1350.processSale.view;
import hanifi.siavash.iv1350.processSale.controller.Controller;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;
import hanifi.siavash.iv1350.processSale.dbhandler.ItemNotFoundException;
import hanifi.siavash.iv1350.processSale.model.Change;
import hanifi.siavash.iv1350.processSale.model.Payment;
/**
* This program has no view, instead, this class is a
* placeholder for the entire view.
*/
public class View {
	
	private Controller controller;
	
	private void presentChangeInfo(Change change) {
		System.out.println();
		System.out.println("Change: " + change.getChangeAmount().getCashAmount());
	}

	private void presentDescription(ItemDTO itemDTO) {
		System.out.println();
		System.out.println("(Item registered)" + itemDTO.toString() + ", " + itemDTO.getPrice() + ":-");
	}
	
	private void presentPaymentInfo(Payment payment) {
		System.out.println();
		System.out.println("Price(tax included): " + payment.getTotalTaxInc());
	}	
	
	private void notifyNewSaleStarted() {
		System.out.println("New sale started.");
	}	
	
	/**
	 * Creates a new instance
	 * 
	 * @param controllerParam The controller that is used for all operations.
	 */
	public View(Controller controller) {
		this.controller = controller;
	}
	
	/**
	 * Starts a new sale and notifies cashier. 
	 */
	private void cashierStartsNewSale() {
		controller.startSale();
		notifyNewSaleStarted();
	}
	
	/**
	 * Register an Item and presents description.
	 * 
	 * @param itemId The item-id
	 * @throws OperationFailedException
	 */
	private void cashierScansItem(int itemId) throws OperationFailedException {
		try {
		presentDescription(controller.registerItem(itemId));
		}
		catch(ItemNotFoundException itemNotFoundException){
			throw new OperationFailedException("Could not register Item with id: " + itemId, itemNotFoundException);
		}
	}
	
	/**
	 * Completes the sale and presents information about the amount required from the customer.
	 */
	private void cashierSignalsAllItemsRegistered(){
		presentPaymentInfo(controller.allItemsRegistered());
	}
	
	/**
	 * Completes the sale and presents the information about the amount
	 * of change the cashier is to give back to the customer
	 * 
	 * @param cashAmount The amount of cash received from the customer.
	 */
	private void cashierSignalsCompleteSale(double cashAmount) {	
		presentChangeInfo(controller.completeSale(cashAmount));
	}
	
	/**
	 *	Hard-coded input simulating the cashiers interaction with the view.
	 * @throws OperationFailedException If failed to register item
	 */
	public void sampleExecution() throws OperationFailedException{
		int itemId = 0;
			this.cashierStartsNewSale();
			itemId=647474; //itemId for a banana
			this.cashierScansItem(itemId);
			itemId=576483; //itemId for a strawberry
			this.cashierScansItem(itemId);
			itemId=123415; //example of an invalid itemId
			this.cashierScansItem(itemId);
			this.cashierSignalsAllItemsRegistered();
			this.cashierSignalsCompleteSale(50);
		}
	
}
