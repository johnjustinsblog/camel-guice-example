package com.johnjustin.web.exam.magic.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.google.inject.Inject;
import com.johnjustin.exam.magic.data.ExamMagicAssessment;
import com.johnjustin.web.exam.magic.processors.AssignExamToUserProcessor;

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
		
		
		
		from("direct:getExamUser")
		.process(p->{
			System.out.println("test");
		})
		.log("getExamUser route")
		.bean(examMagicAssessment, "myTest()");
		
	}

}
 