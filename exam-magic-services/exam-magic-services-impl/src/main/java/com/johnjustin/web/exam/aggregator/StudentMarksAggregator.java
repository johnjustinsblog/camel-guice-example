package com.johnjustin.web.exam.aggregator;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.johnjustin.web.exam.magic.core.ExamUser;

public class StudentMarksAggregator implements AggregationStrategy{
	
	@Override
	public Exchange aggregate(Exchange oldExchane, Exchange newExchange){
		
		ExamUser student = newExchange.getIn().getBody(ExamUser.class);
		List<ExamUser> studentList = null;
		if(oldExchane == null){
			studentList = new ArrayList();
			if(null!= student){
				studentList.add(student);
			}
			newExchange.getIn().setBody(studentList);
			return newExchange;
		}else{
			studentList = oldExchane.getIn().getBody(List.class);
			if(null!= student){
				studentList.add(student);
			}
			return oldExchane;
		}
	}

}
