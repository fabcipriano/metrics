package com.facio.metrics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.IllegalArgumentException;

class MetricsTest {
	final shouldFail = new GroovyTestCase().&shouldFail
	
	//small number
	private static final double DELTA = 1e-15;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateMediaFromFile() {
		Metrics m = new Metrics();
		
		def info = m.calculateMediaFromFile(null);
		
		assertEquals("must be null when file is null", null, info);
		
		info = m.calculateMediaFromFile(new LogFileMock());
		
		assertEquals("media from mock file", 3210.000, info.totalMedia);
		assertEquals("number of lines from mock file", 5, info.countLines);
		assertEquals("total time in ms from mock file", 16050, info.total, DELTA);
		assertEquals("maxtime request from file", 12990, info.maxTimeRequest);
		assertEquals("maxtime request datetime from file", "03/02/2015 00:02:47", info.maxTimeRequestDateTime.format("dd/MM/yyyy HH:mm:ss"));
		
		assertEquals("max request url from file", "/fff7a7169e5/lastPayments/searchLastPayments/agreement/24421066/35837900810/84308/agreement?_=1422928953397", info.maxRequestUrl);			
	}

	@Test
	public void testParseRequestTime() {
		def okLogLine = """10.203.30.66 - - [03/Feb/2015:00:00:36 -0200] "GET /fff7a7169e5/callPendency/showPendency/878628/31872877?_=1422928843111 HTTP/1.1" 200 14384 1328 ms"""
		def badFormatLogLine = """10.203.30.66 - - [03/Feb/2015:00:00:36 -0200] "GET /fff7a7169e5/callPendency/showPendency/878628/31872877?_=1422928843111 HTTP/1.1" 200 14384 ababababa ms"""
		Metrics m = new Metrics();
		
		def result = m.parseRequestTime(null);
		assertEquals("must be null when string is null", null, result);
		
		result = m.parseRequestTime("");
		assertEquals("must be nullo when string is empty", null, result);
		
		result = m.parseRequestTime("    ");
		assertEquals("must be null when string is blank", null, result);
		
		result = m.parseRequestTime(okLogLine);
		assertEquals("parse log line with request time", 1328, result.requestTime);
		assertEquals("parse log line with url", "/fff7a7169e5/callPendency/showPendency/878628/31872877?_=1422928843111", result.url);
		assertEquals("parse log line with date", "03/02/2015 00:00:36", result.dateTime.format("dd/MM/yyyy HH:mm:ss"));
		
		shouldFail(NumberFormatException) {
			m.parseRequestTime(badFormatLogLine);
		}		
	}
	
	@Test
	public void testConvertLogFormat2Datetime() {
		Metrics m = new Metrics();
		
		shouldFail(java.lang.IllegalArgumentException) {
			m.convertLogFormat2Datetime(null);
		}		
		
		shouldFail(java.lang.IllegalArgumentException) {
			m.convertLogFormat2Datetime("");
		}		
		
		shouldFail(java.lang.IllegalArgumentException) {
			m.convertLogFormat2Datetime("   ");
		}
		
		//invalid format
		shouldFail(java.text.ParseException) {
			m.convertLogFormat2Datetime("24/02/1990 01:02:30");
		}
		
		def result = m.convertLogFormat2Datetime("[03/Feb/2015:00:00:36")
		assertEquals("must be null when string is null", "03/02/2015 00:00:36", result.format("dd/MM/yyyy HH:mm:ss"));
	}
	
	@Test
	public void testRegexFileLog() {
		def filename01 = "localhost_access_log.2015-02-03.log"
		def filename02 = "localhost_access_log.2015-01-01.log"
		def wrongfilename = "localhost_access_log.2015-02-03.log.tgz"
		def pattern = ~/localhost_access_log\.[\d]{4}-[\d]{2}-[\d]{2}.log/
		
		assertTrue(pattern.matcher(filename01).matches())
		assertTrue(pattern.matcher(filename02).matches())
		
		assertFalse(pattern.matcher(wrongfilename).matches())
	}

}

class LogFileMock {
	def lines = [
		"""10.203.30.66 - - [03/Feb/2015:00:00:36 -0200] "GET /fff7a7169e5/callPendency/showPendency/878628/31872877?_=1422928843111 HTTP/1.1" 200 14384 1328 ms""",
		"""10.203.31.169 - - [03/Feb/2015:00:01:05 -0200] "GET /fff7a7169e5/callPendency/showPendency/3292266/32102468?_=1422928871000 HTTP/1.1" 200 14384 1198 ms""",
		"""10.203.31.169 - - [03/Feb/2015:00:01:05 -0200] "GET /fff7a7169e5/schedulingPortal/getConnectionData?_=1422928872321 HTTP/1.1" 200 230 133 ms""",
		"""10.203.48.72 - - [03/Feb/2015:00:01:23 -0200] "GET /fff7a7169e5/interaction/decodeUrlSpecialCases/14052 HTTP/1.1" 200 617 401 ms""",
		"""10.203.30.224 - - [03/Feb/2015:00:02:47 -0200] "GET /fff7a7169e5/lastPayments/searchLastPayments/agreement/24421066/35837900810/84308/agreement?_=1422928953397 HTTP/1.1" 200 9659 12990 ms"""
		]

	def eachLine(closure) {
		lines.each {line -> closure(line)}
	}
}
