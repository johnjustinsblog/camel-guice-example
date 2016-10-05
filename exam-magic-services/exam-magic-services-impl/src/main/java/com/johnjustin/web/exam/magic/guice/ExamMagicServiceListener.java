package com.johnjustin.web.exam.magic.guice;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationConverter;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.google.inject.AbstractModule;
import com.google.inject.ConfigurationException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.google.inject.servlet.GuiceServletContextListener;
import com.johnjustin.exam.magic.services.ExamMagicResource;
import com.johnjustin.web.exam.magic.camel.ExamMagicCamelModule;
import com.johnjustin.web.exam.magic.guice.modules.ExamMagicDataModule;
import com.johnjustin.web.exam.magic.jersey.ExamMagicResourceJersey;
import com.sun.jersey.guice.JerseyServletModule;

public class ExamMagicServiceListener extends GuiceServletContextListener{
	
	private ExamMagicDataModule dataModule = null;
	
	@Override
	protected Injector getInjector(){
		
		String configFile = "configuration.properties";
		
		if(System.getProperty("examMagicConfig") != null ){
			configFile = System.getProperty("examMagicConfig");
		}
		try{
			final CompositeConfiguration conf =  new CompositeConfiguration();
			final PropertiesConfiguration prop = new PropertiesConfiguration(configFile);
			conf.addConfiguration(new SystemConfiguration());
			conf.addConfiguration(prop);
			
			List<Module> modules = new ArrayList<Module>();
			
			modules.add(new AbstractModule(){
				
				@Override
				protected void configure(){
					
					bind(Configuration.class).toInstance(conf);
					Names.bindProperties(binder(), ConfigurationConverter.getProperties(conf));
				}
			});
			dataModule = new ExamMagicDataModule(conf);
			modules.add(dataModule);
			modules.add(new ExamMagicServeltModule());
			modules.add(new ExamMagicCamelModule(conf));
			
			Injector injector = Guice.createInjector(modules);
			return injector;
			
			
		}catch(ConfigurationException | org.apache.commons.configuration.ConfigurationException exception){
			throw new RuntimeException("Failure reading conf files"+configFile, exception);
		}
	}
	
	private class ExamMagicServeltModule extends JerseyServletModule{
		
		@Override
		protected void configureServlets(){
			
			bind(ExamMagicResource.class).to(ExamMagicResourceJersey.class);
			bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
			bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
		}
		
		
	}
	
	/*@Override
	public void ContextDestroyed(ServletContextEvent servletContextEvent){
		
		super.contextDestroyed(servletContextEvent);
		//dataModule.closeSession();
	}*/
}
