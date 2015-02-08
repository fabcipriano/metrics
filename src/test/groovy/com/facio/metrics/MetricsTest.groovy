package com.facio.metrics;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

class MetricsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateMediaFromFile() {
		Metrics m = new Metrics();
		
		def media = m.calculateMediaFromFile(null);
		
		assertEquals("must be zero when file is null", 0, media);
		
		media = m.calculateMediaFromFile(new LogFileMock());
		
		assertEquals("media from mock file", 3210.000, media);		
	}

	@Test
	public void testParseRequestTime() {
		def logLine = """10.203.30.66 - - [03/Feb/2015:00:00:36 -0200] "GET /fff7a7169e5/callPendency/showPendency/878628/31872877?_=1422928843111 HTTP/1.1" 200 14384 1328 ms"""
		Metrics m = new Metrics();
		
		def result = m.parseRequestTime(null);
		assertEquals("must be zero when string is null", 0, result);
		
		result = m.parseRequestTime("");
		assertEquals("must be zero when string is empty", 0, result);
		
		result = m.parseRequestTime("    ");
		assertEquals("must be zero when string is blank", 0, result);
		
		result = m.parseRequestTime(logLine);
		assertEquals("parse log line with request time", 1328, result);		
		
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
		println 'Calling MOCK File implementation...'
		lines.each {line -> closure(line)}
		println 'MOCK File implementation finished.'
	}
}
