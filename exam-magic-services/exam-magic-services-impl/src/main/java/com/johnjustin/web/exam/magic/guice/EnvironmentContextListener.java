/**
 * 
 */
package com.johnjustin.web.exam.magic.guice;

import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.SystemConfiguration;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;
import com.johnjustin.web.exam.magic.guice.modules.ClientServletModule;

/**
 * @author John Justin
 *
 */
public final class EnvironmentContextListener extends GuiceServletContextListener {

	/* (non-Javadoc)
	 * @see com.google.inject.servlet.GuiceServletContextListener#getInjector()
	 */
	@Override
	protected Injector getInjector() {
		try{
			CompositeConfiguration compositeConfiguration = new CompositeConfiguration();
			compositeConfiguration.addConfiguration(new SystemConfiguration());
			List<Module> modules = Lists.newArrayList();
			modules.add(new ClientServletModule());
			return Guice.createInjector(modules);
		} catch(Exception ex){
			throw new RuntimeException("Some thing happened");
		}
	}

}
