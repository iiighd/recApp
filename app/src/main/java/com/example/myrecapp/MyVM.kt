package com.example.myrecapp
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyVM (application: Application): AndroidViewModel(application) {
    private val recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    private var db: FirebaseFirestore = Firebase.firestore

    fun getRecipes(): LiveData<List<Recipe>> {
        return recipes
    }

    fun getData(){
        var title =""
        var author =""
        var ingredients = ""
        var instructions =""
        db.collection("Recipes")
            .get()
            .addOnSuccessListener { result ->
                var recipeList = arrayListOf<Recipe>()

                for (document in result) {

                    document.data.map { (key, value)
                        ->
                        when (key) {
                            "title" -> title = value as String
                            "author" -> author = value as String
                            "ingredients" -> ingredients = value as String
                            "instructions" -> instructions = value as String
                        }
                    }
                    recipeList.add(Recipe(document.id,title,author,ingredients, instructions))

                }
                recipes.value = recipeList
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "Error getting documents.", exception)
            }
    }
    fun add( recipe: Recipe){
        CoroutineScope(Dispatchers.IO).launch {
            val Recipe = hashMapOf(
                "title" to recipe.title,
                "author" to recipe.author,
                "ingredients" to recipe.ingredients,
                "instructions" to recipe.instructions
            )
            db.collection("Recipes")
                .add(Recipe)
            getData()
        }
    }
    fun edit(recipeID: String, title: String, author: String, ingredients: String, instructions: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("Recipes").document(recipeID)
                .update(mapOf(
                    "title" to title,
                    "author" to author,
                    "ingredients" to ingredients,
                    "instructions" to instructions
                ))
            getData()
        }
    }

    fun delete(recipeID: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("Recipes")
                .document(recipeID)
                .delete()
            getData()

        }
    }

}