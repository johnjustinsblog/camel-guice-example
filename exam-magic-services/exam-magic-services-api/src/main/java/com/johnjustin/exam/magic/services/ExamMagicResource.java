package com.johnjustin.exam.magic.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("exammagic")
public interface ExamMagicResource {
	
	String CONSUMES = "application/json";
	
	@Path("student/division/{userid}")
	@GET
	@Consumes(CONSUMES)
	@Produces(CONSUMES)
	Response getStudentExamDetails(@PathParam("userid") final String userid);

}
