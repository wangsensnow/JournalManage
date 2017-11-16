package hap.exam.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()
dbType = MigrationHelper.getInstance().dbType()

databaseChangeLog(logicalFilePath:"hap/exam/db/2016-06-01-init-data.groovy"){


    changeSet(author: "yourname", id: "20160601-yourname-1") {
            //sqlFile(path: MigrationHelper.getInstance().dataPath("com/hand/hap/db/data/"+dbType+"/demo.sql"), encoding: "UTF-8")
    }
    //Milestone , excel data, runAlways=true
    changeSet(author: "yourname", id: "20170101-init-data-xlsx", runAlways:"true"){
        //customChange(class:ExcelDataLoader.class.name){
            //param(name:"filePath",value:MigrationHelper.getInstance().dataPath("com/hand/hap/db/data/2017-01-01-init-data.xlsx"))
        //}
    }
}
