package model.files

import model.DataFile

class S1BearerRoutes : DataFile() {
    override val fileName = "S1Bearer-routes"
    override val fileHeader = Header::class.java

    override val tableName = "TODO"
    override val tableHeader = listOf("TODO", "TODO")

    enum class Header {
        REGION,
        S1_NAME,
        CIRCUIT_NAME,
        CIRCUITTYPE,
        ROUTE_NUMBER,
        ROUTE_SEQUENCE,
        SITEA,
        SITEB,
        NODEA,
        NODEB,
        NODETYPEA,
        NODETYPEB,
        NODEDEFA,
        NODEDEFB,
        PORTA,
        PORTB,
        BANDWIDTH,
        STATUS,
        RESOLUTION,
        SECTOR,
        VLANID
    }
}