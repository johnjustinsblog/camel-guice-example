package com.johnjustin.exam.magic.data;

import java.util.List;

public interface ExamMagicAssessment {
	
	public String myTest();
	
	public String assignExamToUser(String userid , String std);
	
	public String getMarksToUser( String std);
	
	public List<String> getMarks();
	
	public List<String> getAttendance();

}
