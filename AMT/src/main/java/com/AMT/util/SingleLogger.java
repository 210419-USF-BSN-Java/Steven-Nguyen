package com.AMT.util;

import org.apache.log4j.Logger;


public class SingleLogger {
	
private static Logger log;
//so nobody can tamper the log variable

private SingleLogger() {};
//so nobody can create empty Logger object

//so anybody can use this method without creating an object
public static synchronized Logger getLogger() {
	log = Logger.getRootLogger();
	return log;
	
}
}
