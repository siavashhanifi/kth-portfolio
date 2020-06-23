package hanifi.siavash.iv1350.processSale.externalSystemCommunication;

/**
 * Creates references to handlers for the external systems.
 * @author Siavash
 *
 */
public class HandleCreator {
	private ExternalAccountingSystemHandler extSysHandler = null;
	private InventorySystemHandler invSysHandler = null;
	private PrinterHandler printerHandler = null;
	
	/**
	 * Instantiates a new object;
	 */
	public HandleCreator() {
		this.extSysHandler = new ExternalAccountingSystemHandler();
		this.invSysHandler = new InventorySystemHandler();
		this.printerHandler = new PrinterHandler();
	}
	
	public ExternalAccountingSystemHandler getExtSysHandler() {
		return this.extSysHandler;
	}
	
	public InventorySystemHandler getInvSysHandler() {
		return this.invSysHandler;
	}
	
	public PrinterHandler getPrinterHandler() {
		return this.printerHandler;
	}
}
