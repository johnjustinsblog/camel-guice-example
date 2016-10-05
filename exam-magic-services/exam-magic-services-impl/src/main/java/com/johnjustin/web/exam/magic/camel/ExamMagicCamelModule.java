package com.johnjustin.web.exam.magic.camel;

import com.google.inject.matcher.Matchers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.apache.commons.configuration.Configuration;

public class ExamMagicCamelModule extends CamelModuleWithMatchingRoutes{
	
	private Configuration config = null;
	
	public ExamMagicCamelModule(Configuration config){
		
		this.config = config;
		//super(Matchers.inPackage(Package.getPackage("com.johnjustin.web.exam.magic.camel")).and
		//		(Matchers.subclassesOf(RouteBuilder.class)));
	}
	
	@Override
	protected final void configure(){
		super.configure();
		bind(ExamMagicRoutes.class);
	}
}
