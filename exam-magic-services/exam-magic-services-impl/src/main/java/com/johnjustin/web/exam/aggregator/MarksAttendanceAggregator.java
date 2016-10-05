package com.johnjustin.web.exam.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.johnjustin.web.exam.magic.core.ExamUser;

public class MarksAttendanceAggregator implements AggregationStrategy{
	
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange){
		
		JSONObject marksAttendanceCombinedJson;
		Exchange resultExchange;
		
		if(oldExchange == null){
			marksAttendanceCombinedJson = new JSONObject();
			resultExchange = newExchange;
		}else{
			marksAttendanceCombinedJson = oldExchange.getIn().getBody(JSONObject.class);
			resultExchange  = oldExchange;
		}
		
		Map<String , Object> headers = newExchange.getIn().getHeaders();
		if(headers.containsKey("marks")){
			List<String> marksList = 	newExchange.getIn().getBody(List.class);
			marksList = (null != marksList)? marksList : new ArrayList<String>();
			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(	marksList ,	((TypeToken<List<String>>) new TypeToken<List<String>>(){}).getType());
			JsonArray jarray = element.getAsJsonArray();
			try {
				marksAttendanceCombinedJson.put("marks", jarray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		if(headers.containsKey("attendance")){
			List<String> attendanceList = 	newExchange.getIn().getBody(List.class);
			attendanceList = (null != attendanceList)? attendanceList : new ArrayList<String>();
			Gson gson = new Gson();
			JsonElement element = gson.toJsonTree(	attendanceList ,	((TypeToken<List<String>>) new TypeToken<List<String>>(){}).getType());
			JsonArray jarray = element.getAsJsonArray();
			try {
				marksAttendanceCombinedJson.put("attendance", jarray);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		resultExchange.getIn().setBody(marksAttendanceCombinedJson, JSONObject.class);
		return resultExchange;
		
	}

}
