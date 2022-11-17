package com.example.foodrecipev2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import java.io.Serializable
import java.util.*


class RecipeAdapter(private val recipeList : ArrayList<Recipe>) : RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = recipeList[position]

        holder.name.text = currentItem.name
        holder.step.text = currentItem.step
        //holder.ingredient.text = currentItem.ingredient
        holder.btn.setOnClickListener(View.OnClickListener {

            /*val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("Key", recipeList as Serializable?)
            startActivity(intent)*/

            /*startActivity(Intent(this, UpdateActivity::class.java).apply {
                putExtra("Key", recipeList as Serializable?)
            })*/

        })




    }

    override fun getItemCount(): Int {
        return recipeList.size

    }

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.titleTv)
        val step : TextView = itemView.findViewById(R.id.StepTv)
        val btn : Button = itemView.findViewById(R.id.buttonNext)
        //val ingredient : TextView = itemView.findViewById(R.id.IngredientTv)

    }

}