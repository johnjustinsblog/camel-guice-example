package com.johnjustin.web.exam.magic.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelTest {

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		try {
			//context.addComponent("activemq", ActiveMQComponent.activeMQComponent("vm://localhost?broker.persistent=false"));
			try {
				context.addRoutes(new RouteBuilder() {
					@Override
					public void configure() throws Exception {
						from("direct:testqueue")
						.process(new Processor() {
							
							@Override
							public void process(Exchange arg0) throws Exception {
								System.out.println("hello world");
								
							}
						})
						.log("stream:out");
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
			ProducerTemplate template = context.createProducerTemplate();
			context.start();
			template.sendBody("direct:testqueue", "Hello World");
			Thread.sleep(2000);
		} finally {
			context.stop();
		}


	}

}
