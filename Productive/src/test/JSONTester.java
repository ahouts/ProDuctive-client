package test;

import static org.junit.Assert.*;
import org.json.simple.JSONObject;


import org.junit.Test;

import shared.Note;
import shared.ProductiveDate;
import shared.Reminder;

public class JSONTester {

	@Test
	public void reminderParsingTest() {
		JSONObject r1JSON = SampleJSONs.reminderFactory(SampleJSONs.reminderFactoryID.ALL_A);
		JSONObject r2JSON = SampleJSONs.reminderFactory(SampleJSONs.reminderFactoryID.ALL_B);
		//JSONObject nullJSON = TestJSON.reminderFactory(TestJSON.reminderFactoryID.ALL_NULL);
		
		Reminder r1=null;
		Reminder r2=null;
		Reminder r3=null;
		
		try {
			 r1 = new Reminder(r1JSON);
			 r2 = new Reminder(r2JSON);
			// r3 = new Reminder(nullJSON);
		} catch (Exception e) {
			sop("The jsonParsing has failed");
			sop(e.getMessage());
			System.exit(0);
		}
		
		// expected Actual
		assertEquals("1", r1.getReminderId());
		assertEquals("I really need to remember to feed the chair and steam clean the dog", r1.getBody());
		ProductiveDate r1Date = new ProductiveDate("07-04-2013:11:42:41");
		assertEquals(r1Date, r1.getCreatedDate());
		
		assertEquals("2", r2.getReminderId());
		assertEquals("This body is not nearly as funny", r2.getBody());
		ProductiveDate r2Date = new ProductiveDate("01-03-2016:10:02:41");
		assertEquals(r2Date, r2.getCreatedDate());
	}
	@Test
	public void noteParsingTest() {
		JSONObject n1JSON = SampleJSONs.noteFactory(SampleJSONs.noteFactoryID.ALL_A);
		JSONObject n2JSON = SampleJSONs.noteFactory(SampleJSONs.noteFactoryID.ALL_B);
		JSONObject n3JSON = SampleJSONs.noteFactory(SampleJSONs.noteFactoryID.NULL_BODY);
		
		// TODO: This line breaks everything uncomment it and get it to work later
		JSONObject n4JSON = SampleJSONs.noteFactory(SampleJSONs.noteFactoryID.NULL_UPDATE_DATE);
		
		Note n1 = new Note(n1JSON);
		Note n2 = new Note(n2JSON);
		Note n3 = new Note(n3JSON);
		Note n4 = new Note(n4JSON);
		
		assertEquals("This is a bad title", n1.getTitle());
		assertEquals("This is a subpar body. I would say it sucks.", n1.getBody());
		ProductiveDate N1_CREATED_DATE_KEY = new ProductiveDate("01-01-2017:12:30:30");
		ProductiveDate N1_UPDATED_LAST_DATE_KEY = new ProductiveDate("01-02-2017:12:45:45");
		assertEquals(N1_CREATED_DATE_KEY, n1.getCreatedDate());
		assertEquals(N1_UPDATED_LAST_DATE_KEY, n1.getUpdatedLastDate());
		
		assertEquals("This is a good title", n2.getTitle());
		assertEquals("This is a great body. I would say it rocks.", n2.getBody());
		ProductiveDate N2_CREATED_DATE_KEY = new ProductiveDate("01-03-2017:12:30:30");
		ProductiveDate N2_UPDATED_LAST_DATE_KEY = new ProductiveDate("01-04-2017:12:45:45");
		assertEquals(N2_CREATED_DATE_KEY, n2.getCreatedDate());
		assertEquals(N2_UPDATED_LAST_DATE_KEY, n2.getUpdatedLastDate());
		
		assertEquals("The body should be null, but this is just a title", n3.getTitle());
		assertEquals(null, n3.getBody());
		ProductiveDate N3_CREATED_DATE_KEY = new ProductiveDate("04-01-2017:12:30:30");
		ProductiveDate N3_UPDATED_LAST_DATE_KEY = new ProductiveDate("04-04-2017:12:45:45");
		assertEquals(N3_CREATED_DATE_KEY, n3.getCreatedDate());
		assertEquals(N3_UPDATED_LAST_DATE_KEY, n3.getUpdatedLastDate());
		
		assertEquals("This is a another title", n4.getTitle());
		assertEquals("This is a another body. I would say it is ok.", n4.getBody());
		ProductiveDate N4_CREATED_DATE_KEY = new ProductiveDate("02-01-2017:12:30:30");
		ProductiveDate N4_UPDATED_LAST_DATE_KEY = new ProductiveDate(null);
		assertEquals(N4_CREATED_DATE_KEY, n4.getCreatedDate());
		assertEquals(N4_UPDATED_LAST_DATE_KEY, n4.getUpdatedLastDate());

	}
	
	private static void sop(String str) {
		System.out.println(str);
	}

}
