package fr.snef.dbmanager.orange

abstract class OrangeProcedureDataFile : OrangeDataFile() {

    abstract val procedureQuery: String
}
