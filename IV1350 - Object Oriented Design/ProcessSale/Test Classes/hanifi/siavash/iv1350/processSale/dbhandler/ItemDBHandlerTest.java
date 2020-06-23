package hanifi.siavash.iv1350.processSale.dbhandler;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanifi.siavash.iv1350.processSale.data.ItemDB;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;

public class ItemDBHandlerTest {
	
	ItemDBHandler itemDBHandler;
	final int itemIDBanana = 647474;
	final int itemIDStrawberry = 576483;
	
	@Before
	public void setUp() throws Exception {
		itemDBHandler = new ItemDBHandler();
	}

	@After
	public void tearDown() throws Exception {
		itemDBHandler = null;
	}

	@Test
	public void testFetchItemInvalidId() {
		int invalidItemIDExample = 0;
		ItemDTO result =  this.itemDBHandler.fetchItem(invalidItemIDExample);
		assertNull("Invalid Id not returning null", result);	
	}
	
	@Test
	public void testFetchItemBanana() {
		ItemDTO expResult = ItemDB.Banana;
		ItemDTO result = this.itemDBHandler.fetchItem(itemIDBanana);
		assertEquals("Fetching of item banana failed", expResult, result);
		}
	
	@Test
	public void testFetchItemStrawberry() {
		ItemDTO expResult = ItemDB.Strawberry;
		ItemDTO result = this.itemDBHandler.fetchItem(itemIDStrawberry);
		assertEquals("Fetching of item strawberry failed", expResult, result);	
		}
}
