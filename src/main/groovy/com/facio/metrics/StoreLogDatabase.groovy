package com.facio.metrics

import groovy.sql.Sql


/**
 * Insert audil log to Database
 * 
 * @author fabiano
 *
 */
class StoreLogDatabase {
    
    //TODO: use strategy this is ugly
//    private Metrics metrics = new Metrics()
    def metrics = null
    def db = [url: 'jdbc:mysql://127.0.0.1/logaudit',
        user: 'root', password: 'amadeus', driver: 'com.mysql.jdbc.Driver']

    //TODO: change this implementation to use a Strategy pattern
    def fromFile(file) {
        
        def sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
        
        file?.eachLine {
            def info = metrics.parseLogLine(it)
            
            info.mysqldatetime = info.dateTime.format("yyyy-MM-dd HH:mm:ss")
            
            def sqlcmd = "INSERT INTO Log_Request (node,ip_client,time_req,url,duration,size) VALUES ('visao01', ${info.ip},${info.mysqldatetime},${info.url},${info.requestTime},${info.size})"
            
            sql.execute sqlcmd
        }
    }
    
    def testIsThisLabel() {
        def exit = false
        
        while (!exit) {
            expected:
                println("After expected")
                
            where:
                println("After where")
                if (exit) {
                    println ("We are back to where ... time to exit !!!")
                    return 
                }

            goingback:
                println("go back")
                exit = true
                continue where;
        }
            
    }
}