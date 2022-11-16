package com.example.foodrecipev2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipev2.databinding.ActivityRecipeListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_recipe_list.*
import java.util.*

class RecipeList : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeListBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var recipe: Recipe
    private lateinit var uid:String
    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesList: ArrayList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        /*recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        imagesList = arrayListOf()

        databaseReference = FirebaseDatabase.getInstance().getReference("recipe")
        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(dataSnapshot in snapshot.children){
                        val recipe = dataSnapshot.getValue(Recipe::class.java)
                        imagesList.add(recipe!!)

                    }

                    recyclerView.adapter= RecipeAdapter(imagesList,this)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })*/

    }
}