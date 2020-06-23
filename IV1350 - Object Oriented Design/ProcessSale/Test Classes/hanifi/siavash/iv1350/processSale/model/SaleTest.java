package hanifi.siavash.iv1350.processSale.model;
import java.util.Vector;
import hanifi.siavash.iv1350.processSale.data.ItemDB;
import hanifi.siavash.iv1350.processSale.data.ItemDTO;
import java.util.Date;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SaleTest {
	Sale sale;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		sale = new Sale();
	}

	@After
	public void tearDown() throws Exception {
		sale = null;
	}
	
	@Test
	public void testGetLastRegisteredItem(){
		this.sale.registerItem(ItemDB.Banana);
		ItemDTO expResult = ItemDB.Banana;
		ItemDTO result = this.sale.getListOfItems().lastElement();
		assertEquals("Last registered item not at the end of the list", expResult, result);
	}

}
