package com.johnjustin.web.exam.magic.jersey;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.processor.validation.PredicateValidationException;

import com.johnjustin.exam.magic.services.ExamMagicResource;
import com.johnjustin.web.exam.magic.core.ExamUser;
import com.johnjustin.web.exam.magic.data.exception.RepositoryException;

public class ExamMagicResourceJersey implements ExamMagicResource{
	
	@Produce
	private ProducerTemplate producer;
	
	@Override
	public Response getStudentExamDetails(String userId){
		
		Response response = Response.noContent().build();
		List<ExamUser> students = null;
		
		try {
			Map<String,Object> paramMap = new HashMap<String,Object>();
			paramMap.put("userId", userId);			
			
			students = (List<ExamUser>)producer.requestBodyAndHeaders("direct:getExamUser",null,paramMap,List.class);
			if(null != students){
				response = Response.status(Status.OK).type(MediaType.APPLICATION_JSON_TYPE).entity(students).build();
			}
		}catch(CamelExecutionException e){
			response = processException(e);
		}
		
		
		return response;
	}
	
	private Response processException(Exception exception){
		
		Response response = null;
		
		if(exception.getCause() instanceof PredicateValidationException){
			
			PredicateValidationException exec = (PredicateValidationException) exception.getCause();
			response = response.status(Status.NOT_FOUND).entity(exec.getExchange().getProperty("exception")).build();
		}else if(exception.getCause() instanceof RepositoryException){
			
			RepositoryException exe = (RepositoryException) exception.getCause();
			response = response.status(Status.BAD_REQUEST).entity(exe.getMessage()).build();
		}else{
			response = response.serverError().build();
		}
		
		return response;
	}

}
