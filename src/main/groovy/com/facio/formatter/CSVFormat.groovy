package com.facio.formatter

class CSVFormat {

	/**
	 * Convert [key1:value1, key2:value2] to "value1";"value2" 
	 * 
	 * @param line
	 * @return
	 */
	public String linetxt2csv(String line) {
		String result = ""
		
		verifyBeforeParse(line)
		
		if (!line.startsWith("[")) {
			return null
		}
		
		def tuples = line.split(",");
		
		tuples.each {
			def value = it.replaceFirst(~/([\[a-zA-Z])*:/, "")
			
			result += "\"" + value.minus("]").trim() + "\""	
			result += ";"
		}		
		
		result
	}

	private verifyBeforeParse(line) {
		if (!line?.trim()) {
			throw new IllegalArgumentException("line canot be null or empty")
		}
	}
	
	def convertFile2csv(File file, String outputFilename) {
		if (file.isFile()) {
			
			def out = new File(outputFilename)
			println "save to ${out}"
			out.withWriter { writer ->
				
				writer.writeLine("filename;total;countLines;totalMedia;maxTimeRequest;maxTimeRequestDateTime;maxRequestUrl;maxRequestSize;numberRequestAboveLimit")				
				file.eachLine { line ->
					def parsed = linetxt2csv(line)
					if (parsed) {
						writer.writeLine(parsed)
					}					
				}
			}
		}
	}
}
