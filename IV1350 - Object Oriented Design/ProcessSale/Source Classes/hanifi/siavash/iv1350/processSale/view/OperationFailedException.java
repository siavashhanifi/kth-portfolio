package hanifi.siavash.iv1350.processSale.view;

import hanifi.siavash.iv1350.processSale.dbhandler.ItemDBHandlerException;
import hanifi.siavash.iv1350.processSale.dbhandler.ItemNotFoundException;
/**
 * Thrown when a cashier-made operation fails
 * @author Siavash
 *
 */
public class OperationFailedException extends Exception {
	private ItemNotFoundException itemNotFoundException;
	Exception cause;
	
	/**
	 * Creates an exception for the scenario in which the registration 
	 * of an item fails because the item could not be found.
	 * @param message
	 * @param itemNotFoundException
	 */
	OperationFailedException(String message, ItemNotFoundException itemNotFoundException){
		super(message);
		this.itemNotFoundException = itemNotFoundException;
		this.cause = itemNotFoundException;
	}
	
	OperationFailedException(ItemDBHandlerException itemDBHandlerException){
		super(itemDBHandlerException.getMessage());
		
	}
	
	public ItemNotFoundException getCause(){
		return this.cause;
	}
}
