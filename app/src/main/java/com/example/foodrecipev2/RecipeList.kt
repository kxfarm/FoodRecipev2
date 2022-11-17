package com.example.foodrecipev2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_recipe_list.*
import java.util.*


class RecipeList : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var recipe: Recipe
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipeArrayList: ArrayList<Recipe>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recipeList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        recipeArrayList = arrayListOf<Recipe>()
        recyclerView.adapter = RecipeAdapter(recipeArrayList)
        getUserData()


    }

    private fun getUserData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe")

        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(recipeSnapshot in snapshot.children){
                        val user = FirebaseAuth.getInstance().currentUser
                        val uid = user!!.uid
                        if(recipeSnapshot.child("uid").getValue().toString().equals(uid)) {

                            val recipe = recipeSnapshot.getValue(Recipe::class.java)
                            //Add if recipe not null
                            recipeArrayList.add(recipe!!)
                        }

                    }

                    recyclerView.adapter = RecipeAdapter(recipeArrayList)

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}