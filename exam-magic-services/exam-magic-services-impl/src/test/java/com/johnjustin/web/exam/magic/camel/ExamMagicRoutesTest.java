package com.johnjustin.web.exam.magic.camel;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.validation.PredicateValidationException;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.http.HttpStatus;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.johnjustin.exam.magic.data.ExamMagicAssessment;

@Test(groups="integration")
public class ExamMagicRoutesTest extends CamelTestSupport{
	
	@Produce
	private ProducerTemplate producer;
	
	@Mock
	ExamMagicAssessment examMagicAssessment = null;
	
	@Override
	@BeforeClass
	public void setUp() throws Exception{
		
		examMagicAssessment = mock(ExamMagicAssessment.class);
		super.setUp();
	}
	
	@Override
	protected RouteBuilder[] createRouteBuilders() throws Exception{
		
		super.createRouteBuilders();
		
		ExamMagicRoutes r = new ExamMagicRoutes(examMagicAssessment);
		return new RouteBuilder[] {r};
	}
	
	@Test(enabled = true, description="test for sample routes")
	public void myRoutesTest()throws Exception{
		
		String id = "id";
		String div = "div";
		
		//when(examMagicAssessment.myTest(id,div)).thenReturn("null");
	when(examMagicAssessment.myTest())
		.thenReturn("hello");
	Map<String,Object> paramMap = new HashMap<String,Object>();
	paramMap.put("userId", "james");	
		
	String name = producer.requestBodyAndHeaders("direct:getExamUser",null,paramMap,String.class);
		
		assertEquals(name, "hello");
	}
	
	
	//#################################################################################################//
	@Test(enabled = true, description="test for assignExamToUser routes")
	public void assignExamToUserTest()throws Exception{
		
		String userid = "james";
		String std = "5th";
		
	when(examMagicAssessment.assignExamToUser(Mockito.anyString(),Mockito.anyString())).thenReturn("james");
	
	context.getRouteDefinition("direct:assignExamToUser").adviceWith(context, new AdviceWithRouteBuilder() {
		
		@Override
		public void configure() throws Exception {
			weaveById("userAssignExamToUserProcessor").replace().process(new Processor(){
				@Override
				public void process(final Exchange exchange)throws Exception{
					exchange.getIn().setHeader("userid", "james");
					exchange.setProperty("std", "5th");
				}
			});
			
		}
	});
	context.start();
	
	Map<String,Object> paramMap = new HashMap<String,Object>();
	paramMap.put("userId", "james");	
		
	String name = producer.requestBodyAndHeaders("direct:assignExamToUser",paramMap,paramMap,String.class);
	assertEquals(name, "james");
	}
	
	//################################################################//
	@DataProvider(name = "getStudentInput")
	public Object[][] getStudentInput(){
		
		String userId = "james";
		return new Object[][]{{ HttpStatus.SC_OK , userId }};
	}
	
	@Test(enabled = true, description="test for getExamUser routes", dataProvider = "getStudentInput")
	public void getExamUserTest(final int status, String userid)throws Exception{
		
		//String userid = "james";
		///String std = "5th";
		String actual = null;
	when(examMagicAssessment.getMarksToUser(Mockito.anyString())).thenReturn("58");
	context.start();
	
	
	context.getRouteDefinition("direct:getExamUserValidate").adviceWith(context, new AdviceWithRouteBuilder() {
		
		@Override
		public void configure() throws Exception {
			weaveByToString(".*validate.*").replace().to("mock:validateStudent").process(new Processor(){
				@Override
				public void process(Exchange exchange)throws Exception{
					if(status== 200){
						exchange.getOut().setBody(true);
					}else{
							exchange.getOut().setBody(false);
						}
					}
			});
			
		}
	});
	
	try{
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("userid", "james");
		paramMap.put("std", "5th");
		actual = producer.requestBodyAndHeaders("direct:getExamUserValidate",paramMap,paramMap,String.class);
		 
	}catch(CamelExecutionException e){
		if(status == 404){
			assertTrue(e.getCause() instanceof PredicateValidationException);
		}else{
			fail();
		}
	}
	if(status == 200){
		assertNotNull(actual);
	}
	}
	
	//################################################################//
}

