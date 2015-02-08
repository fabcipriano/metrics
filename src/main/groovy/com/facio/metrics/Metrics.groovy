package com.facio.metrics

class Metrics {
	
	def count
	def media
	BigDecimal total
	
	def calculateMediaFromFile(File f) {
		count = 0
		media = 0
		total = 0
		f?.eachLine {
			
			def requestTime = parseRequestTime(it)
			println "add requestTime ${requestTime}"
			
			count++
			total += requestTime;
		}
		
		if (count) {
			total.divide(count, 3, BigDecimal.ROUND_HALF_UP);
		} else {
			return 0
		}
	}
	
	def parseRequestTime(String message) {
		def tokens = message?.split(" ");
		
		if (isValidFormatMessage(tokens)) {
			tokens[-2].toInteger();
		} else {
			return 0
		}
	}
	
	def isValidFormatMessage(String[] tokens) {
		(tokens && tokens.size() >= 2)
	}

}
