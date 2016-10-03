package com.johnjustin.exam.magic.data.impl;

import com.johnjustin.exam.magic.data.ExamMagicAssessment;

public class ExamMagicAssessmentImpl implements ExamMagicAssessment{

	@Override
	public String myTest() {
         System.out.println("hello myTest");
         return "hello";
	}
	
	@Override
	public String assignExamToUser(String userid , String std){
		System.out.println("assignExamToUser"+userid);
		return userid;
		
	}

}
