package com.facio.formatter;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class CSVFormatTest {
	final shouldFail = new GroovyTestCase().&shouldFail
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String linetoParse = """[filename:localhost_access_log.2015-02-08.log, total:33015697, countLines:179246, totalMedia:184.192, maxTimeRequest:80366, maxTimeRequestDateTime:Sun Feb 08 10:53:01 BRST 2015, maxRequestUrl:/fff7a7169e5/serviceOrderRequest/unlockRequestValid/, maxRequestSize:817]"""
		String result = """"localhost_access_log.2015-02-08.log";"33015697";"179246";"184.192";"80366";"Sun Feb 08 10:53:01 BRST 2015";"/fff7a7169e5/serviceOrderRequest/unlockRequestValid/";"817";"""
		CSVFormat csv = new CSVFormat();
		
		shouldFail(IllegalArgumentException) {
			csv.linetxt2csv(null);
		}

		shouldFail(IllegalArgumentException) {
			csv.linetxt2csv("");
		}

		shouldFail(IllegalArgumentException) {
			csv.linetxt2csv("     ");
		}
				
		assertEquals("Parse must be OK", result, csv.linetxt2csv(linetoParse));
		assertEquals("Parse must be null", null, csv.linetxt2csv("Starting analise... /home/fabiano/tmp/visaoLogs/log/visao01"));
		
	}

}
