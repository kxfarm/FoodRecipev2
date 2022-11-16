package com.example.foodrecipev2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.foodrecipev2.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

//Auth
        auth= FirebaseAuth.getInstance()

//Logout
        binding.buttonLogout.setOnClickListener{
            auth.signOut()
            val logoutIntent = Intent(this,MainActivity::class.java)
            logoutIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(logoutIntent)
            Toast.makeText(this,"Successfully Sign Out!", Toast.LENGTH_LONG).show()
            //startActivity(Intent(this,MainActivity::class.java))
        }

        binding.btnCreateRecipe.setOnClickListener{
            startActivity(Intent(this,CreateRecipe::class.java))
        }
        binding.btnRecipe.setOnClickListener{
            startActivity(Intent(this,RecipeList::class.java))
        }
    }

}