package com.johnjustin.web.exam.magic.camel;

import org.apache.camel.builder.RouteBuilder;

import com.google.inject.Inject;
import com.johnjustin.exam.magic.data.ExamMagicAssessment;

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
		.bean(examMagicAssessment, "addMethod(${property.id} , ${property.mydiv})");
		
	}

}
 