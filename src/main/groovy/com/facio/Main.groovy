package com.facio; 

import groovy.io.FileType

import com.facio.metrics.Metrics

printStatisticsFromFile = { file ->
	def metricsFile = new Metrics()
	
	info = metricsFile.calculateMediaFromFile(file)
	println info.toString()
}

if (args.size() != 1) {
	println 'Please use command: groovy Main <filename>'
	return
}

println "Starting analise... ${args[0]}"

file = new File(args[0])
if (file.isFile()) {
	printStatisticsFromFile(file)
} else {
println "===== Statistics From Files ====="
	file.eachFileMatch FileType.FILES, ~/localhost_access_log\.[\d]{4}-[\d]{2}-[\d]{2}.log/ ,
			{ 
				printStatisticsFromFile(it) 
			}
}


println "Done analise of ${args[0]}"



