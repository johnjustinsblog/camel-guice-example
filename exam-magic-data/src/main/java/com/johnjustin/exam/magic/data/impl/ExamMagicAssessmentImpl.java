package com.johnjustin.exam.magic.data.impl;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	public String getMarksToUser( String std){
		String marks ="58";
		return marks;
	}
	
	@Override
	public List<String>getMarks(){
		
		List<String> marks = new ArrayList<String>();
		marks.add("25");
		marks.add("26");
		marks.add("27");
		marks.add("28");
		marks.add("29");
		return marks;
	}
	
	@Override
	public List<String> getAttendance(){
		List<String> attendance = new ArrayList<String>();
		attendance.add("15");
		attendance.add("16");
		attendance.add("17");
		attendance.add("18");
		attendance.add("19");
		return attendance;
		
	}
}
