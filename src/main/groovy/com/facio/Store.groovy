package com.facio

import com.facio.metrics.StoreLogDatabase


if (args.size() != 1) {
	println 'Please use: groovy Store <filename>'
}

store = new StoreLogDatabase()

file = new File(args[0])

if (file.isFile()) {
	println "Store logs from (${file}) to Database ..."
	store.fromFile(file)
	println 'Done.'
}