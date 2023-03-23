package sv.edu.udb.guia08app

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayoutStates.TAG
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import sv.edu.udb.guia08app.datos.Persona
import java.text.SimpleDateFormat
import java.util.*


class AddPersonaActivity : AppCompatActivity() {

    val NEW_SPINNER_ID = 1
    private var edtDUI: EditText? = null
    private var edtNombre: EditText? = null
    private var edtFecha: EditText? = null
    private var edtGenero: EditText? = null
    private var edtPeso: EditText? = null
    private var edtAltura: EditText? = null
    private var tvFechaNacimiento: TextView? = null
    private var key = ""
    private var nombre = ""
    private var dui = ""
    private var accion = ""
    private lateinit var database: DatabaseReference
    private var spinnerGeneros:Spinner?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)
        inicializar()
    }

    private fun inicializar() {

        edtNombre = findViewById<EditText>(R.id.edtNombre)
        edtDUI = findViewById<EditText>(R.id.edtDUI)
        edtAltura = findViewById<EditText>(R.id.edtAltura)
        edtPeso = findViewById<EditText>(R.id.edtPeso)
        tvFechaNacimiento = findViewById<TextView>(R.id.tvfechaNacimient)

        val edtNombre = findViewById<EditText>(R.id.edtNombre)
        val edtDUI = findViewById<EditText>(R.id.edtDUI)
        val edtAltura = findViewById<EditText>(R.id.edtAltura)
        val edtPeso = findViewById<EditText>(R.id.edtPeso)

        spinnerGeneros=findViewById(R.id.mySpinner)

        var genres = arrayOf("Seleccione un genero","Masculino", "Femenino")
        var adaptador:ArrayAdapter<String> = ArrayAdapter(this,android.R.layout.simple_spinner_item,genres)
        spinnerGeneros?.adapter=adaptador

        //calendario
        val c= Calendar.getInstance()
        val year= c.get(Calendar.YEAR)
        val month= c.get(Calendar.MONTH)
        val day= c.get(Calendar.DAY_OF_MONTH)

        tvFechaNacimiento?.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mDay ->
                tvFechaNacimiento?.setText(""+ mDay +"/"+(mMonth+1)+"/"+mYear)
            }, year , month, day)

            dpd.show()
        }

        // Obtención de datos que envia actividad anterior
        val datos: Bundle? = intent.getExtras()
        if (datos != null) {
            key = datos.getString("key").toString()

        }
        if (datos != null) {
            //edtGenero.setText(intent.getStringExtra("genero").toString())

            //asignar posicion de spinner
            val generoSelected = intent.getStringExtra("genero").toString()
            if(generoSelected=="Masculino" || generoSelected=="Femenino"){
                spinnerGeneros?.setSelection(genres.indexOf(generoSelected))
            }else{
                spinnerGeneros?.setSelection(0)
            }

        }

        if (datos != null) {
            tvFechaNacimiento?.setText(intent.getStringExtra("fechaNacimiento").toString())
        }else{ tvFechaNacimiento?.setText("1/1/2001")}
        if (datos != null) {
            edtDUI.setText(intent.getStringExtra("dui").toString())
        }
        if (datos != null) {
            edtNombre.setText(intent.getStringExtra("nombre").toString())
        }
        if (datos != null) {
            accion = datos.getString("accion").toString()
        }

        if (datos != null) {
            edtAltura.setText(intent.getStringExtra("altura").toString())
        }
        if (datos != null) {
            edtPeso.setText(intent.getStringExtra("peso"))
        }

    }


    fun guardar(v: View?) {
        var genero: String = ""
        if(spinnerGeneros?.selectedItemPosition==0){
            genero= "Desconocido"
        }else{
            genero= spinnerGeneros?.getSelectedItem().toString()
        }

        val altura: Double = edtAltura?.text.toString().toDouble()
        val peso: Double = edtPeso?.text.toString().toDouble()
        val fechaNacimiento: String = tvFechaNacimiento?.text.toString()
        val nombre: String = edtNombre?.text.toString()
        val dui: String = edtDUI?.text.toString()

        database = FirebaseDatabase.getInstance().getReference("personas")

        // Se forma objeto persona
        val persona = Persona(altura,peso,genero,fechaNacimiento, dui, nombre)

        if (accion == "a") { //Agregar registro
            database.child(nombre).setValue(persona).addOnSuccessListener {
                Toast.makeText(this, "Se guardo con exito", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed ", Toast.LENGTH_SHORT).show()
            }
        } else  // Editar registro
        {
            val key = database.child("nombre").push().key
            if (key == null) {
                Toast.makeText(this, "Llave vacia", Toast.LENGTH_SHORT).show()
            }
            val personasValues = persona.toMap()
            val childUpdates = hashMapOf<String, Any>(
                "$nombre" to personasValues
            )
            database.updateChildren(childUpdates)
            Toast.makeText(this, "Se actualizo con exito", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    fun cancelar(v: View?) {
        finish()
    }
}