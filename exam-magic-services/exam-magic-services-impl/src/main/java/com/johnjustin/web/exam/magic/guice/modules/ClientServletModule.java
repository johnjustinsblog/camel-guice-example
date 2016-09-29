/**
 * 
 */
package com.johnjustin.web.exam.magic.guice.modules;

import java.util.Map;

import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * @author John Justin
 *
 */
public class ClientServletModule extends JerseyServletModule {
	
	@Override
	protected void configureServlets() {
		//bind(PeopleFinderResource.class).to(PeopleFinderResourceJersey.class).in(Singleton.class);
		
		bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
		bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);
		
		Map<String, String> params=Maps.newHashMap();
		params.put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE.toString());
		serve("/rest/*").with(GuiceContainer.class, params);
	}
}
