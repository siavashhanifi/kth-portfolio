package hanifi.siavash.iv1350.processSale.controller;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;
import hanifi.siavash.iv1350.processSale.data.SaleDTO;
import hanifi.siavash.iv1350.processSale.dbhandler.*;
import hanifi.siavash.iv1350.processSale.externalSystemCommunication.*;
import hanifi.siavash.iv1350.processSale.model.CashAmount;
import hanifi.siavash.iv1350.processSale.model.Change;
import hanifi.siavash.iv1350.processSale.model.Payment;
import hanifi.siavash.iv1350.processSale.model.Reciept;
import hanifi.siavash.iv1350.processSale.model.Sale;
import hanifi.siavash.iv1350.processSale.model.Tax;

/**
 * This is the application’s only controller class. All
 * calls to the model pass through here.
 * @author Siavash
 *
 */
public class Controller {
	private Sale sale = null;
	private ItemDBHandler itemDBHandler = null;
	private ExternalAccountingSystemHandler extSysHandler = null;
	private InventorySystemHandler invSysHandler = null;
	private PrinterHandler printerHandler = null;
	private Payment payment = null;
	
	/**
	 * Fetches the <code>Item</code>-object
	 * @param itemId the <code>integer</code> identifier for the item to be fetched.
	 * @return The found <code>Item</code>
	 * @throws ItemNotFoundException 
	 */
	private ItemDTO searchItem(int itemId) throws ItemNotFoundException{
		return itemDBHandler.fetchItem(itemId);
	}
	
	private Change calculateChange(double cashAmount) {
		return new Change(new CashAmount(cashAmount), this.payment);
	}
	
	private SaleDTO logSale(Sale sale) {
		return new SaleDTO(sale);
	}
	
	private Reciept generateReceipt(SaleDTO loggedSale, Change change) {
		return new Reciept(loggedSale, this.payment, change);
	}
	
	/**
	 * Creates a new instance.
	 * 
	 * @param itemDBHandler Reference to the item registry.
	 * @param handleCreator Contains the references to the handlers for the external systems.
	 */
	public Controller(ItemDBHandler itemDBHandler, HandleCreator handleCreator) {
		this.itemDBHandler = itemDBHandler;
		this.extSysHandler =  handleCreator.getExtSysHandler();
		this.invSysHandler =  handleCreator.getInvSysHandler();
		this.printerHandler = handleCreator.getPrinterHandler();
		
	}
	
	
	/**
	 * Initializes a new sale.
	 *  
	 */
	public void startSale() {
		this.sale = new Sale();
	}
	
	
	/**
	 * Registers an item to the ongoing sale.
	 * @param itemId 
	 * @return Reference to the registered<code>Item</code>.
	 */
	public ItemDTO registerItem(int itemId) throws ItemNotFoundException {
		sale.registerItem(searchItem(itemId));
		return searchItem(itemId);		
	}
	
	/**
	 * Calculates the total cost with and without taxes.
	 * @return A <code>Payment</code>-object containing information about the 
	 */
	public Payment allItemsRegistered(){
		Tax tax = new Tax(sale);
		this.payment = new Payment(tax, sale);
		return this.payment;
		
	}
	
	/**
	 * Finalizes the ongoing sale.
	 * @param cashAmount
	 * @return
	 */
	public Change completeSale(double cashAmount) {
		// TODO Auto-generated method stub
		Change change = this.calculateChange(cashAmount);
		SaleDTO loggedSale = this.logSale(sale);
		Reciept reciept = this.generateReceipt(loggedSale, change);
		this.printerHandler.printReceipt(reciept);
		this.extSysHandler.addSaleTransaction(loggedSale, this.payment);
		this.invSysHandler.updateInventory(loggedSale);
		return change;
	}
}
