package com.facio.metrics

class Metrics {
		
	def calculateMediaFromFile(file) {
		def count = 0
		def media = 0
		BigDecimal total = 0
		
		def maxTimeRequest = 0;
		def maxRequestUrl = ""
		def datetimeRequest = ""
		def maxRequestSize = 0
		
		def infoMetrics = null
		file?.eachLine {
			
			def info = parseLogLine(it)
			
			if (info.requestTime > maxTimeRequest) {
				maxTimeRequest = info.requestTime;
				maxRequestUrl = info.url
				maxRequestSize = info.size
				datetimeRequest = info.dateTime
			}
			count++
			total += info.requestTime;
		}
		
		if (count) {
			media = total.divide(count, 3, BigDecimal.ROUND_HALF_UP);
			infoMetrics = [
				"filename" : file.toString().split("/")[-1], 
				"total":total, 
				"countLines":count, 
				"totalMedia":media,
				"maxTimeRequest" : maxTimeRequest,
				"maxTimeRequestDateTime" : datetimeRequest,
				"maxRequestUrl" : maxRequestUrl,
				"maxRequestSize" : maxRequestSize]
		} 
		
		infoMetrics
	}
	
	def parseLogLine(String message) {
		def tokens = message?.split(" ")
		def info = null
		def sizeInBytes = 0
		
		if (isValidFormatMessage(tokens)) {
			if (tokens[-3]?.isNumber()) {
				sizeInBytes = tokens[-3].toInteger()
			}
			
			info = [
				"ip" : tokens[0],
				"requestTime" : tokens[-2].toInteger(), 
				"url": tokens[6],
				"dateTime": convertLogFormat2Datetime(tokens[3]),
				"size": sizeInBytes]
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

