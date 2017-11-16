package hap.exam.db.table


import com.hand.hap.liquibase.MigrationHelper

def mhi = MigrationHelper.getInstance()

databaseChangeLog(logicalFilePath:"hap/exam/db/2016-06-01-init-migration.groovy"){


    changeSet(author: "jessen", id: "20160601-hailor-1") {

        if(mhi.isDbType('oracle')){
            createSequence(sequenceName: 'HAP_DEMO_S')
        }
        createTable(tableName: "HAP_DEMO") {
            column(autoIncrement: "true", name: "id", type: "BIGINT") {
                constraints(nullable: "false", primaryKey: "true")
            }

            column(name: "name", type: "VARCHAR(100)") {
            }

            column(name: "object_version_number", type: "BIGINT", defaultValue : "1")
            column(name: "request_id", type: "BIGINT", defaultValue : "-1")
            column(name: "program_id", type: "BIGINT", defaultValue : "-1")
            column(name: "created_by", type: "BIGINT", defaultValue : "-1")
            column(name: "creation_date", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "last_updated_by", type: "BIGINT", defaultValue : "-1")
            column(name: "last_update_date", type: "DATETIME", defaultValueComputed : "CURRENT_TIMESTAMP")
            column(name: "last_update_login", type: "BIGINT", defaultValue : "-1")

        }

    }
}
