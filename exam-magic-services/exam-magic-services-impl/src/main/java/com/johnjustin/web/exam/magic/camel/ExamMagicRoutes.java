package com.johnjustin.web.exam.magic.camel;

import javax.xml.bind.JAXBContext;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.cache.CacheConstants;
import org.apache.camel.component.http4.HttpOperationFailedException;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.johnjustin.exam.magic.data.ExamMagicAssessment;
import com.johnjustin.web.exam.aggregator.MarksAttendanceAggregator;
import com.johnjustin.web.exam.aggregator.StudentMarksAggregator;
import com.johnjustin.web.exam.magic.exception.ServiceException;
import com.johnjustin.web.exam.magic.predicates.StudentPredicate;
import com.johnjustin.web.exam.magic.processors.AssignExamToUserProcessor;
import com.sun.jersey.server.wadl.generators.ObjectFactory;

public class ExamMagicRoutes extends RouteBuilder{
	
	private ExamMagicAssessment examMagicAssessment;
	
	public static final String EXAM_MAGIC_CACHE = "cache://examCache";
	public static final String UPDATE_EXAM_MAGIC_CACHE = "direct:updateExamCache";
	public static final String GET_FROM_EXAM_CACHE = "direct:getFromExamCache";
	public static final String GET_STUDET_FROM_CACHE = "direct:getStudentsFrmCache";
	
	@Inject
	@Named("schoolname")
	private String schoolName = null;
	
	@Inject
	public ExamMagicRoutes(ExamMagicAssessment examMagicAssessment){
		this.examMagicAssessment = examMagicAssessment;
	}
	
	
	@Override
	public void configure() throws Exception {
		
		onException(HttpOperationFailedException.class)
		.handled(true)
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				HttpOperationFailedException exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT,HttpOperationFailedException.class);
			//	LOGGER.error(" Exam Magic Routes Error "+exception.getResponseBody(),exception.fillInStackTrace());
				throw new ServiceException(exception.getResponseBody(),exception.getStatusCode());
			}
		});
		
		
		
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
		//.marshal().json(JsonLibrary.Jackson)
		.validate(new StudentPredicate())
		.setBody(constant(true))
		.end();
		
		
		from("direct:getMarksAndAttendance")
		.routeId("direct:getMarksAndAttendance")
		.log(LoggingLevel.INFO,"route for direct:getMarksAndAttendance")
		.multicast()
		.aggregationStrategy(new MarksAttendanceAggregator())
		.parallelProcessing()
		.to("direct:getMarks" , "direct:getAttendance")
		.end();
		
		
		from("direct:getMarks")
		.setHeader("marks",simple("true"))
		.routeId("direct:getMarks")
		.log("direct:getMarks")
		.bean(examMagicAssessment, "getMarks()");
		
		
		from("direct:getAttendance")
		.setHeader("attendance",simple("true"))
		.routeId("direct:getAttendance")
		.log("direct:getAttendance")
		.bean(examMagicAssessment, "getAttendance()");
		
		
		from(GET_STUDET_FROM_CACHE)
		.routeId(GET_STUDET_FROM_CACHE)
		.log(GET_STUDET_FROM_CACHE)
		.setHeader("myexamroute",constant("getAllExamChilds"))
		.setProperty("cacheKey", simple("${property.std}"))
		.to(GET_FROM_EXAM_CACHE);
		
		//************************** caching routes start ************************//
		
		from(UPDATE_EXAM_MAGIC_CACHE)
		.routeId(UPDATE_EXAM_MAGIC_CACHE)
		.log(GET_STUDET_FROM_CACHE)
		.setHeader(CacheConstants.CACHE_OPERATION,constant(CacheConstants.CACHE_OPERATION_ADD))
		.setHeader(CacheConstants.CACHE_KEY, property("cacheKey"))
		.to(EXAM_MAGIC_CACHE)
		.end();
		
		from(GET_FROM_EXAM_CACHE)
		.routeId(GET_FROM_EXAM_CACHE)
		.log(GET_FROM_EXAM_CACHE)
		.setHeader(CacheConstants.CACHE_OPERATION,constant(CacheConstants.CACHE_OPERATION_GET))
		.setHeader(CacheConstants.CACHE_KEY, property("cacheKey"))
		.to(EXAM_MAGIC_CACHE)
		.log(LoggingLevel.INFO, "response from cache ${body}")
		.choice()
			.when(header(CacheConstants.CACHE_ELEMENT_WAS_FOUND).isNull())
			.log(LoggingLevel.ERROR, "no value in cache for id  ${body}")
			.recipientList(header("myexamroute"))
			.to(UPDATE_EXAM_MAGIC_CACHE)
			.endChoice()
		.end()
		.removeHeader(CacheConstants.CACHE_KEY)
		.removeHeader(CacheConstants.CACHE_OPERATION)
		.end();
		
		//************************* caching routes end **************************//
		
		
		
		from("direct:getExamUser")
		.process(p->{
			System.out.println("test");
		})
		.log("getExamUser route")
		.bean(examMagicAssessment, "myTest()");
		
	}

}
 