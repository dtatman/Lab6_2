package com.example.lab6_2_test
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactDetailsActivity : AppCompatActivity() {
    private lateinit var textViewName: TextView
    private lateinit var textViewPhone: TextView
    private lateinit var textViewEmail: TextView
    private lateinit var buttonDelete: Button
    private lateinit var buttonCall: Button
    private lateinit var buttonEmail: Button
    private lateinit var dbHelper: DatabaseHelper
    private var contactId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_details)

        dbHelper = DatabaseHelper(this)

        textViewName = findViewById(R.id.textViewName)
        textViewPhone = findViewById(R.id.textViewPhone)
        textViewEmail = findViewById(R.id.textViewEmail)
        buttonDelete = findViewById(R.id.buttonDelete)
        buttonCall = findViewById(R.id.buttonCall)
        buttonEmail = findViewById(R.id.buttonEmail)

        // Nhận ID của liên hệ từ Intent
        contactId = intent.getIntExtra("contactId", -1)
        loadContactDetails()

        buttonDelete.setOnClickListener {
            dbHelper.deleteContact(contactId)
            finish() // Quay lại MainActivity
        }
        buttonCall.setOnClickListener {
            callContact()
        }

        buttonEmail.setOnClickListener {
            emailContact()
        }
    }
    private fun callContact() {
        val phoneNumber = textViewPhone.text.toString()
        val intent = Intent(Intent.ACTION_DIAL).apply {
            data = Uri.parse("tel:$phoneNumber")
        }
        startActivity(intent)
    }

    private fun emailContact() {
        val emailAddress = textViewEmail.text.toString()
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:$emailAddress")
        }
        startActivity(intent)
    }

    private fun loadContactDetails() {
        val contact = dbHelper.getContact(contactId)
        textViewName.text = contact.name
        textViewPhone.text = contact.phone
        textViewEmail.text = contact.email
    }
}