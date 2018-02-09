package model

enum class Status(val label: String) {
    Reel("Réel"),
    Prev("Prévisionnel"),
    Mhs("MHS"),
    AMuter("A Muter"),
    ASupprimer("A Supprimer"),
    Unknown("N/A")
}