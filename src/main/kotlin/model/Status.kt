package model

enum class Status(val label: String) {
    Reel("Réel"),
    Prev("Prévisionnel"),
    Mhs("MHS"),
    AMuter("A Muter"),
    ASupprimer("A Supprimer"),
    Unknown("N/A");

    companion object {
        @Throws(TypeCastException::class)
        fun from(str: String) = if (str.isBlank()) {
            Unknown
        } else {
            Status.values().firstOrNull { status ->
                str.contains(status.label, true)
            } ?: throw TypeCastException()
        }
    }
}