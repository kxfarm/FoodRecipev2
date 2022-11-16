package com.example.foodrecipev2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.foodrecipev2.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_register.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Auth
        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener{
            val email  = binding.userEmailET.text.toString().trim()
            val password = binding.userPassword.text.toString().trim()
            if(email.isEmpty()) {
                userEmailET.error = "Email Required"
                return@setOnClickListener
            }else if(password.isEmpty()){
                userPassword.error = "Password Required"
                return@setOnClickListener
            }
            loginUser(email,password)
        //startActivity(Intent(this,HomeActivity::class.java))
        }
        binding.btnRegister.setOnClickListener{
            startActivity(Intent(this,RegisterActivity::class.java))
        }

        emailFocusListener()
        passwordFocusListener()
    }

//Login
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { login ->
            if (login.isSuccessful) {
                startActivity(Intent(this, HomeActivity::class.java))
                Toast.makeText(this,"Successfully Login!",Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"Could not Identify Credentials!",Toast.LENGTH_LONG).show()
            }
        }
    }

//EmailHelpTextValidation
    private fun emailFocusListener() {
        binding.userEmailET.setOnFocusChangeListener{ _, focused ->
            if(!focused)
            {
                binding.emailcontainer.helperText = validEmail()
            }
        }
    }
    private fun validEmail(): String? {
        val emailText = binding.userEmailET.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Invalid Email Address"
        }
        return null
    }

//PasswordHelpTextValidation
    private fun passwordFocusListener() {
        binding.userPassword.setOnFocusChangeListener{ _, focused ->
            if(!focused)
            {
                binding.passwordcontainer.helperText = validPassword()
            }
        }
    }
    private fun validPassword(): String? {
        val passwordText = binding.userPassword.text.toString()
        if(passwordText.length < 6)
        {
            return "Minimum 6 Character Required"
        }
        return null
    }

}