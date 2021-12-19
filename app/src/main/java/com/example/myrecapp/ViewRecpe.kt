package com.example.myrecapp
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ViewRecpe : AppCompatActivity() {
    private val vm by lazy{ ViewModelProvider(this).get(MyVM::class.java)}
    lateinit var rvMain : RecyclerView
    private lateinit var rvAdapter: RVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_recpe)
        rvMain = findViewById(R.id.rvMain)

        vm.getData()

        vm.getRecipes().observe(this, {

                recipes -> rvAdapter.update(recipes)

        })
        rvAdapter = RVAdapter(this)
        rvMain.adapter = rvAdapter
        rvMain.layoutManager = LinearLayoutManager(this)
    }


    fun editAlert(idRecipe: String , title: String, author: String, ingredients: String,instructions: String) {

        val builder = AlertDialog.Builder(this)

        val view = layoutInflater.inflate(R.layout.edit_alert, null)

        builder.setView(view)

        val alertDialog: AlertDialog = builder.create()

        val etTitle = view.findViewById<EditText>(R.id.etTitle)
        val etAuthor = view.findViewById<EditText>(R.id.etAuthor)
        val etIngredients = view.findViewById<EditText>(R.id.etIngredients)
        val etInstructions = view.findViewById<EditText>(R.id.etInstructions)
        val edit = view.findViewById<Button>(R.id.edit)

        etTitle.setText(title)
        etAuthor.setText(author)
        etIngredients.setText(ingredients)
        etInstructions.setText(instructions)

        alertDialog.show()


        edit.setOnClickListener {
            var utitle = etTitle.text.toString()
            var uauthor = etAuthor.text.toString()
            var uingredients = etIngredients.text.toString()
            var uinstructions = etInstructions.text.toString()

            vm.edit(idRecipe,utitle,uauthor,uingredients,uinstructions)

            alertDialog.dismiss()

        }

    }

    fun deleteAlert( id: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)


        dialogBuilder.setMessage("Confirm delete ?")
            .setPositiveButton("Delete", DialogInterface.OnClickListener {
                    _, _ ->

                vm.delete(id)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Delete Alert")
        alert.show()


    }
}