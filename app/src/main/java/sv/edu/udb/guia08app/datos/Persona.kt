package sv.edu.udb.guia08app.datos

import java.time.LocalDate

class Persona {
    fun key(key: String?) {
    }

    var dui: String? = null
    var nombre: String? = null
    var fechaNacimiento : String? = "1979-01-01";
    var genero: String? = null
    var peso: Double? = 0.0
    var altura: Double? = 0.0
    var key: String? = null
    var per: MutableMap<String, Boolean> = HashMap()

    constructor() {}
    constructor(altura: Double?, peso: Double?, genero: String?, fechaNacimiento: String?, dui: String?, nombre: String?) {
        this.dui = dui
        this.nombre = nombre
        this.fechaNacimiento = fechaNacimiento
        this.genero = genero
        this.peso = peso
        this.altura = altura
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "dui" to dui,
            "nombre" to nombre,
            "fechaNacimiento" to fechaNacimiento,
            "genero" to genero,
            "peso" to peso,
            "altura" to altura,
            "key" to key,
            "per" to per
        )
    }
}