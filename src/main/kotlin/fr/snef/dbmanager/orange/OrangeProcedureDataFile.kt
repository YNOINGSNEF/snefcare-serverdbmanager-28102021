package fr.snef.dbmanager.orange

abstract class OrangeProcedureDataFile : OrangeDataFile() {

    abstract val procedureQuery: String
    open val shouldTruncate = true

    protected val equipmentAntennaFilter = "E.DEVICE_TYPE LIKE 'Antenne%' AND E.DEVICE_TYPE NOT LIKE '%GPS%'"
}
