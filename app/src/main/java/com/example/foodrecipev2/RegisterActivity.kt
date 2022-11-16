package com.example.foodrecipev2

import android.content.Intent
import android.os.Build.USER
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipev2.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Auth
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()


    binding.registerButton.setOnClickListener{
        val email  = binding.registerEmail.text.toString().trim()
        val password = binding.registerPassword.text.toString().trim()
        if(email.isEmpty()) {
            registerEmail.error = "Email Required"
            return@setOnClickListener
        }else if(password.isEmpty()){
            registerPassword.error = "Password Required"
            return@setOnClickListener
        }
        registerUser(email,password)
        //startActivity(Intent(this, MainActivity::class.java))
    }
    binding.alreadyAndLogin.setOnClickListener{
        startActivity(Intent(this, MainActivity::class.java))
    }


        emailFocusListener()
        passwordFocusListener()
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){
            if(it.isSuccessful){
                val databaseRef = database.reference.child("users").child(auth.currentUser!!.uid)
                val users : Users = Users(auth.currentUser!!.uid,email)
                databaseRef.setValue(users).addOnCompleteListener{
                    if(it.isSuccessful){
                        startActivity(Intent(this, MainActivity::class.java))
                        Toast.makeText(this,"Successfully Registered!",Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this, "Something went wrong, try again",Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this, "Something went wrong, try again",Toast.LENGTH_LONG).show()
            }
        }

    }

    private fun emailFocusListener() {
        binding.registerEmail.setOnFocusChangeListener{ _, focused ->
            if(!focused)
            {
                binding.emailcontainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailText = binding.registerEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

    private fun passwordFocusListener() {
        binding.registerPassword.setOnFocusChangeListener{ _, focused ->
            if(!focused)
            {
                binding.passwordcontainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordText = binding.registerPassword.text.toString()
        if(passwordText.length < 6)
        {
            return "Minimum 6 Character Required"
        }
        return null
    }


}