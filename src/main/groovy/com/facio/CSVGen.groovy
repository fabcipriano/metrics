package com.facio

import com.facio.formatter.CSVFormat

if (args.size() != 1) {
	println 'Please use command: groovy CSVGen <filename>'
	return
}

println "Generating CSV... ${args[0]}"

file = new File(args[0])
if (file.isFile()) {
	csv = new CSVFormat()
	
	csv.convertFile2csv(file, "out.csv")
}