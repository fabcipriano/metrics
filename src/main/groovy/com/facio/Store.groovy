package com.facio

import groovy.io.FileType

import com.facio.metrics.StoreLogDatabase


if (args.size() != 1) {
	println 'Please use: groovy Store <filename>'
	return
}

store = new StoreLogDatabase()


file = new File(args[0])

if (file.isFile()) {
	println "Store logs from (${file}) to Database ..."
	store.fromFile(file)
	println 'Done.'
} else {
	println "===== Store logs from folder (${file}) to Database ... ====="
	file.eachFileMatch FileType.FILES, ~/localhost_access_log\.[\d]{4}-[\d]{2}-[\d]{2}.log/ ,
		{
			println "Storing ${it.name} to Database ..."
			store.fromFile(it)
			println "======= Done with ${it.name}"
		}

}