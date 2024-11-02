package com.example.lab6_2_test

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var listViewContacts: ListView
    private lateinit var btnAddContact: Button
    private val contacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        listViewContacts = findViewById(R.id.listViewContacts)
        btnAddContact = findViewById(R.id.btnAddContact)

        btnAddContact.setOnClickListener {
            startActivityForResult(Intent(this, AddContactActivity::class.java),ADD_CONTACT_REQUEST)


        }

        loadContacts()
    }

    private fun loadContacts() {
        val contactList = dbHelper.getAllContacts()
        contacts.clear()
        contacts.addAll(contactList)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts.map { it.name })
        listViewContacts.adapter = adapter

        listViewContacts.setOnItemClickListener { _, _, position, _ ->
            val selectedContact = contacts[position]
            // Chuyển đến Activity chi tiết với thông tin liên hệ đã chọn
            val intent = Intent(this, ContactDetailsActivity::class.java).apply {
                putExtra("contactId", selectedContact.id)
            }
            startActivity(intent)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_CONTACT_REQUEST && resultCode == RESULT_OK) {
            loadContacts() // Cập nhật danh sách liên hệ
        }
    }

    companion object {
        private const val ADD_CONTACT_REQUEST = 1
    }
}