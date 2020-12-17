package ut.watermeter;

import static org.junit.Assert.*;

import org.junit.Test;

import fi.watermeter.WaterMeter;

public class WaterMeterTest {

	private static int MIN_WATER_LEVEL;
	private static int MAX_WATER_LEVEL;
	private static int initialWaterLevel;
	private WaterMeter waterMeter;
	
	
	@org.junit.BeforeClass
	public static void BeforeClass() {
		MIN_WATER_LEVEL = 1;
		MAX_WATER_LEVEL = 4;
		initialWaterLevel = 1;
		System.out.println("BeforeClass");
	}
	
	@org.junit.Before
	public void setUp() throws Exception {
		waterMeter = WaterMeter.getInstance();
		System.out.println("SetUp");
	}
	
	@Test
	public void newInstanceTest() {
		assertEquals("Water Level is not what is expected.", initialWaterLevel, waterMeter.getCurrentLevel());
	}
	
	@Test
	public void increaseWaterMeter() {
		waterMeter.increaseWaterMeter();
		assertEquals("Water Level is not what is expected.", initialWaterLevel+1, waterMeter.getCurrentLevel());
	}
	
	@Test
	public void settingLegalWaterLevelTest() {
		int waterLevel;
		for(waterLevel = MIN_WATER_LEVEL; waterLevel <= MAX_WATER_LEVEL; waterLevel++) {
			waterMeter.setCurrentLevel(waterLevel);
			assertEquals("Water Level is not what is expected.", waterLevel, waterMeter.getCurrentLevel());
		}
	}	
	
	public void settingIllegalWaterLevel(int illegalLevel) {
		   try
		   {
			   waterMeter.setCurrentLevel(illegalLevel);
		   }
		   catch(RuntimeException re)
		   {
		      String message = "The watermeter has been set to be out of legal bounds.";
		      assertEquals(message, re.getMessage());
		      throw re;
		   }
		   fail("WaterMeter illegal level runtime exception didn't occur!");
	}
	
	@Test (expected = RuntimeException.class)
	public void settingIllegalWaterLevelTest() {
		settingIllegalWaterLevel(0);
		for(int waterLevel = MAX_WATER_LEVEL+1; waterLevel <= MAX_WATER_LEVEL+10; waterLevel++) {
			settingIllegalWaterLevel(waterLevel);
		}
	}
	
	@Test (expected = RuntimeException.class)
	public void exceedingGameOverWaterLevelTest() {
		   try
		   {
			   waterMeter.setCurrentLevel(MAX_WATER_LEVEL);
			   waterMeter.increaseWaterMeter();
			   waterMeter.increaseWaterMeter();
		   }
		   catch(RuntimeException re)
		   {
		      String message = "The watermeter has exceeded the Game Over Level.";
		      assertEquals(message, re.getMessage());
		      throw re;
		   }
		   fail("WaterMeter exceeded game over level runtime exception didn't occur!");
	}
	
	@org.junit.After
	public void tearDown() {
		waterMeter.destroy();
		System.out.println("Tear down");
	}
	
	@org.junit.AfterClass
	public static void AfterClass() {
		System.out.println("After Class");
	}
}
