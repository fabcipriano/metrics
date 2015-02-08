package com.facio.metrics

class Metrics {
	
	def calculateMediaFromFile(file) {
		def count = 0
		def media = 0
		BigDecimal total = 0
		
		def maxTimeRequest = 0;
		def maxRequestUrl = ""
		
		def infoMetrics = null
		file?.eachLine {
			
			def info = parseRequestTime(it)
			
			if (info.requestTime > maxTimeRequest) {
				maxTimeRequest = info.requestTime;
				maxRequestUrl = info.url
			}
			count++
			total += info.requestTime;
		}
		
		if (count) {
			media = total.divide(count, 3, BigDecimal.ROUND_HALF_UP);
			infoMetrics = [
				"total":total, 
				"countLines":count, 
				"totalMedia":media,
				"maxTimeRequest" : maxTimeRequest,
				"maxRequestUrl" : maxRequestUrl]
		} 
		
		infoMetrics
	}
	
	def parseRequestTime(String message) {
		def tokens = message?.split(" ")
		def info = null
		
		if (isValidFormatMessage(tokens)) {
			info = [
				"requestTime" : tokens[-2].toInteger(), 
				"url": tokens[6],
				"dateTime": convertLogFormat2Datetime(tokens[3])]
		} 
		
		info
	}
	
	def convertLogFormat2Datetime(logDate) {
		if (logDate?.trim()) {
			Date.parse("[dd/MMM/yyyy:HH:mm:ss", logDate);
		} else {
			throw new IllegalArgumentException("Invalid format of string cannot be null, empty, etc")
		}
	}
	
	private def isValidFormatMessage(String[] tokens) {
		(tokens && tokens.size() >= 2)
	}

}

