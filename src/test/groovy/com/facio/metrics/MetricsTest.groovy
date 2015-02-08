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
		
	}

	@Test
	public void testParseRequestTime() {
		Metrics m = new Metrics();
		
		def result = m.parseRequestTime(null);
		assertEquals("must be zero when string is null", 0, result);
		
		m.parseRequestTime("");
		assertEquals("must be zero when string is empty", 0, result);
		
		m.parseRequestTime("    ");
		assertEquals("must be zero when string is blank", 0, result);
		
	}

}
