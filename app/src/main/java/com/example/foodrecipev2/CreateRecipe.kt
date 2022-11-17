package com.example.foodrecipev2

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foodrecipev2.databinding.ActivityCreateRecipeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_create_recipe.*
import java.text.SimpleDateFormat
import java.util.*

class CreateRecipe : AppCompatActivity() {

    private lateinit var binding : ActivityCreateRecipeBinding
    private lateinit var ImageUrl : Uri
    private lateinit var auth : FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private lateinit var mStorage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Auth
        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("recipe")
        val key = databaseReference.push().key.toString()
        mStorage = FirebaseStorage.getInstance()


        //Btn
        binding.btnAddImage.setOnClickListener {
            //selectImg()
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            val Gallery_Code: Int = 1;
            intent.type = "images/"
            startActivityForResult(intent, Gallery_Code)


        }
        binding.btnSubmit.setOnClickListener {
            //submit()
            val progressDialog = ProgressDialog(this)
            val name = binding.etFoodTitle.text.toString()
            val etStep = binding.etStep.text.toString()
            val etIngredient = binding.etIngredient.text.toString()
            val uid = auth.currentUser?.uid.toString()
            if (name != null && etStep != null && etIngredient != null && ImageUrl != null) {
                progressDialog.setMessage("Uploading...")
                progressDialog.setCancelable(false)
                progressDialog.show()

                val storageReference = FirebaseStorage.getInstance().getReference()
                val ref = storageReference.child("recipe/$key")
                val uploadTask = ref.putFile(ImageUrl)

                uploadTask.addOnFailureListener {
                    // Handle unsuccessful uploads
                    Toast.makeText(baseContext, "Failed to upload", Toast.LENGTH_LONG).show()
                }.addOnSuccessListener {
                    Toast.makeText(baseContext, "Image uploaded", Toast.LENGTH_LONG).show()

                    //Not Working
                    var downloadUrl: Uri? = null
                    /*FirebaseStorage.getInstance().reference.child("recipe").child(key)
                        .downloadUrl.addOnSuccessListener { it1 ->
                            downloadUrl = it1
                            }*/
                        val uid = auth.currentUser?.uid.toString()
                        val recipe = Recipe(name,etStep,etIngredient,uid)
                        databaseReference.child(key).setValue(recipe).addOnCompleteListener{
                        //Not Working
                        /*val push = databaseReference.push();
                        push.child("Recipe Image").setValue(downloadUrl)*/
                        progressDialog.dismiss()
                        Toast.makeText(this,"Success!",Toast.LENGTH_LONG).show()
                        startActivity(Intent(this,HomeActivity::class.java))

                        }



                }

                    /* val t = storageReference.downloadUrl.getResult().toString();
                        val push = databaseReference.push();
                        push.child("Recipe Name").setValue(name)
                        push.child("Recipe Step").setValue(etStep)
                        push.child("Recipe Ingredient").setValue(etIngredient)
                        push.child("Recipe Image").setValue(t)*/

                }else{
                Toast.makeText(this,"Failed Submitting!",Toast.LENGTH_LONG).show()
            }

            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            val Gallery_Code: Int = 1;
            if (requestCode == Gallery_Code && resultCode == RESULT_OK) {
                ImageUrl = data?.data!!
                binding.addImage.setImageURI(ImageUrl)
                btnAddImage.alpha = 0.0f
                btnAddImage.setEnabled(false)
            }
        }

        /*private fun submit() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading File")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val name = binding.etFoodTitle.text.toString()
        val etStep = binding.etStep.text.toString()
        val etIngredient = binding.etIngredient.text.toString()
        val uid = auth.currentUser?.uid.toString()
        val recipe = Recipe(name,etStep,etIngredient)
        if(uid != null){
            databaseReference.child(name).setValue(recipe).addOnCompleteListener{
                if(it.isSuccessful){
                    //Upload Image

                    val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
                    val now = Date()
                    val fileName = formatter.format(now)
                    val storageReference = FirebaseStorage.getInstance().getReference("Recipe/$fileName")
                    storageReference.putFile(ImageUrl).addOnSuccessListener() {
                        binding.addImage.setImageURI(null)
                        Toast.makeText(this,"Successfully Submit!",Toast.LENGTH_LONG).show()
                        if(progressDialog.isShowing)progressDialog.dismiss()
                        startActivity(Intent(this,HomeActivity::class.java))
                    }.addOnFailureListener{
                        if(progressDialog.isShowing)progressDialog.dismiss()
                        Toast.makeText(this,"Failed!",Toast.LENGTH_LONG).show()
                    }



                }else{
                    Toast.makeText(this,"Failed Submit",Toast.LENGTH_LONG).show()
                }

            }
        }*/


        /*private fun selectImg() {
        val intent =Intent()
        intent.type = "images/"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100 && resultCode == RESULT_OK){
            ImageUrl = data?.data!!
            binding.addImage.setImageURI(ImageUrl )
            btnAddImage.alpha = 0.0f
            btnAddImage.setEnabled(false)

        }
    }
}*/
    }
