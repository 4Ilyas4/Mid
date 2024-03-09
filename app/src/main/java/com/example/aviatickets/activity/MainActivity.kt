package com.example.aviatickets.activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.aviatickets.R
import com.example.aviatickets.databinding.ActivityMainBinding
import com.example.aviatickets.fragment.OfferListFragment
// MainActivity.kt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, OfferListFragment())
                .commit()
        }
    }
}
