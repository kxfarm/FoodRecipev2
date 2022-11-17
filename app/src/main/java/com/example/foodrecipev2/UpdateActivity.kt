package com.example.foodrecipev2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        val `object`: Any? = intent.getSerializableExtra("Key")
        val update_key: Recipe? = `object` as Recipe?

    }
}