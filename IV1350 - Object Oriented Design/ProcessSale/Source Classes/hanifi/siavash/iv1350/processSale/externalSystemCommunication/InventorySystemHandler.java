package hanifi.siavash.iv1350.processSale.externalSystemCommunication;

import hanifi.siavash.iv1350.processSale.data.SaleDTO;

public class InventorySystemHandler {

	public void updateInventory(SaleDTO loggedSale) {
		System.out.println();
		System.out.println("Inventory System: inventory Successfully updated.");
		for(int i = 0; i<loggedSale.getListOfItems().size();i++) {
			System.out.println("One item with id: " + loggedSale.getListOfItems().get(i).getId() + " has been removed from the inventory.");
		}	
	}
}
