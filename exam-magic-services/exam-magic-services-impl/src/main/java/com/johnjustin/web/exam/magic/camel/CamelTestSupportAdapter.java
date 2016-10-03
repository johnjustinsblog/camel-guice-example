/*package com.johnjustin.web.exam.magic.camel;


import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

public class CamelTestSupportAdapter extends CamelTestSupport {
	
	@BeforeTest
	@Override
	public void setUp() throws Exception{
		
		super.setUp();
	}

	@BeforeClass
	@Override
	protected RouteBuilder createRouteBuilder()throws Exception{
		return super.createRouteBuilder();
	}
	
	@After
	public void tearDown() throws Exception{
		
		super.tearDown();
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception{
		
		CamelTestSupport.tearDownAfterClass();
	}
}
*/