package com.johnjustin.web.services.impl;

import java.io.IOException;

public class Sample {
	private static int i=1;
	
	public static void main(String args[])throws IOException{
		System.out.println("Hello >>>>>>");
		
		Sample o1 = new Sample();
		Sample o2 = new Sample();
		
		o1.i = 2;
		System.out.println(" o1 >>>"+o1.i);
		System.out.println("  o2 >>>>"+o2.i);
	}
}
