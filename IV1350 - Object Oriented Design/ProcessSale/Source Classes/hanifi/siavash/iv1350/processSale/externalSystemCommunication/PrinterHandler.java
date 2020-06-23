package hanifi.siavash.iv1350.processSale.externalSystemCommunication;

import hanifi.siavash.iv1350.processSale.model.Reciept;

/**
 * Communicates with the external printer.
 * (Since we have no information about the external printer at this stage,
 *  the designing of this class cannot be completed.)
 * @author Siavash
 *
 */
public class PrinterHandler {
	
	/**
	 * Simulation of the handler signaling the printer to print out a receipt.
	 * @param reciept
	 */
	public void printReceipt(Reciept reciept) {
		System.out.println();
		System.out.println("Printer: Reciept successfully printed.");
		
	}


}
