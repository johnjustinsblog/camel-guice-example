package com.johnjustin.web.exam.magic.processors;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class AssignExamToUserProcessor implements Processor{

	@Override
	public void process(Exchange exchange)throws Exception{
		
		Map<String , Object> headerParam = exchange.getIn().getHeaders();
		if(headerParam.containsKey("userid")){
			exchange.getIn().setHeader("userid", headerParam.get("userid"));
			exchange.setProperty("userid", headerParam.get("std"));
		}
		
	}
}
