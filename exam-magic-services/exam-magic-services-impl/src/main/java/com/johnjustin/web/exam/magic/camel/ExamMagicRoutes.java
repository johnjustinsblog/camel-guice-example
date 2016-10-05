package com.johnjustin.web.exam.magic.camel;

import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;

import com.google.inject.Inject;
import com.johnjustin.exam.magic.data.ExamMagicAssessment;
import com.johnjustin.web.exam.magic.predicates.StudentPredicate;
import com.johnjustin.web.exam.magic.processors.AssignExamToUserProcessor;
import com.sun.jersey.server.wadl.generators.ObjectFactory;

public class ExamMagicRoutes extends RouteBuilder{
	
	private ExamMagicAssessment examMagicAssessment;
	
	@Inject
	public ExamMagicRoutes(ExamMagicAssessment examMagicAssessment){
		this.examMagicAssessment = examMagicAssessment;
	}
	
	
	@Override
	public void configure() throws Exception {
		
		from("direct:myRoute")
		.routeId("test")
		.log("testing routes")
		.validate(body().isNotNull())
		.bean(examMagicAssessment, "myTest()");
		
		
		from("direct:assignExamToUser")
		.routeId("direct:assignExamToUser")
		.log("direct:assignExamToUser")
		.validate(body().isNotNull())
		.process(new AssignExamToUserProcessor())
		.id("userAssignExamToUserProcessor")
		.bean(examMagicAssessment, "assignExamToUser(${header.userid}, ${property.std})");
		
				
		from("direct:getExamUserValidate")
		.routeId("direct:getExamUserValidate")
		.log("direct:getExamUserValidate")
		.process(new AssignExamToUserProcessor())
		.id("userAssignExamToUserProcessor2")
		.setBody(simple("${property.userid}"))
		.process(p->{
			System.out.println("test"+body());
		})
		.validate(body().isNotNull())
		.to("direct:validateStudent")
		.validate(body().isEqualTo(true))
		.setBody(simple("${property.std}"))
		.bean(examMagicAssessment, "getMarksToUser(${body})");
		
		
		from("direct:validateStudent")
		.routeId("direct:validateStudent")
		.log(LoggingLevel.INFO,"route for direct:validateStudent")
		//.process(new AssignExamToUserProcessor())
		//.id("userAssignExamToUserProcessor3")
		//.setBody(simple("${property.std}"))
		//.setHeader(Exchange.HTTP_PATH, simple("GetStudentDetail?uderid=${body}&std=&division="))
		//.setHeader(Exchange.HTTP_METHOD, constant("GET"))
		//.to(serviceEndpoint)
		//.unmarshal(new JaxbDataFormat(JAXBContext.newInstance(ObjectFactory.class)))
		.validate(new StudentPredicate())
		.setBody(constant(true))
		.end();
		
		
		
		
		from("direct:getExamUser")
		.process(p->{
			System.out.println("test");
		})
		.log("getExamUser route")
		.bean(examMagicAssessment, "myTest()");
		
	}

}
 