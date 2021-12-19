package com.example.myrecapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*


class RVAdapter (private val activity: ViewRecpe):  RecyclerView.Adapter<RVAdapter.ItemViewHolder>(){

    private var list= emptyList<Recipe>()

    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var recipe = list[position]

        holder.itemView.apply {
            tvTitle.text = "Title: ${recipe.title}"
            tvAuthor.text = "Author: ${recipe.author}"

            cvMain.setOnClickListener {
                Toast.makeText(holder.itemView.context," The Ingredients :${recipe.ingredients}\n The Instructions :${recipe.instructions}", Toast.LENGTH_SHORT).show()

            }
            editbtn.setOnClickListener {

                var title = recipe.title
                var author = recipe.author
                var ingredients = recipe.ingredients
                var instructions = recipe.instructions
                activity.editAlert(recipe.id,title,author,ingredients,instructions)
            }
            delbtn.setOnClickListener {
                activity.deleteAlert(recipe.id)
            }
        }
    }

    override fun getItemCount() = list.size

    fun update(recipe: List<Recipe>){

        this.list = recipe
        notifyDataSetChanged()
    }
}