package com.johnjustin.web.exam.magic.camel;

import com.google.inject.matcher.Matchers;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.guice.CamelModuleWithMatchingRoutes;

public class ExamMagicCamelModule extends CamelModuleWithMatchingRoutes{
	
	public ExamMagicCamelModule(){
		
		super(Matchers.inPackage(Package.getPackage("com.johnjustin.web.exam.magic.camel")).and
				(Matchers.subclassesOf(RouteBuilder.class)));
	}
	
	@Override
	protected final void configure(){
		super.configure();
		//bind(MyRoutes.class);
	}
}
