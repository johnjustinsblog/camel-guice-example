package com.johnjustin.web.exam.magic.predicates;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

public class StudentPredicate implements Predicate{
	
	@Override
	public boolean matches(Exchange exchg){
		
		return true;
	}

}
