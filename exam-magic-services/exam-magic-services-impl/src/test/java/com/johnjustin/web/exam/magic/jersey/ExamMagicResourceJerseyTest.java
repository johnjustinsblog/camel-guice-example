/*package com.johnjustin.web.exam.magic.jersey;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.johnjustin.exam.magic.data.ExamMagicAssessment;

@Test(groups="integration")
public class ExamMagicResourceJerseyTest extends CamelTestSupport{
	
	@Produce
	private ProducerTemplate produce;
	
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
		
		ExamMagicRoutes r = new ExamMagicRoutes(examMagicAssessment);
		return new RouteBuilder[] {r};
	}

}
*/