package com.example.lab7plataformas

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import kotlinx.android.synthetic.main.activity_receiver.*

class ReceiverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiver)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
        }

        button8.setOnClickListener {

            var senderString = sender.text.toString()
            var receiverString = receiver.text.toString()
            //Despliega Snackbar con a informacion
            val snackbar = Snackbar.make(root_layout, "Correo enviado desde $senderString hacia $receiverString ", Snackbar.LENGTH_INDEFINITE)
            snackbar.setAction("Cerrar") {
                snackbar.dismiss()
                Thread.sleep(100)
                val intent: Intent = Intent(applicationContext, Main3Activity::class.java)
                startActivity(intent)
                finish()
            }
            snackbar.show()
        }
    }

    private fun handleSendText(intent: Intent) {
        receiver.setText(intent.getStringExtra(Intent.EXTRA_EMAIL))
        subject.setText(intent.getStringExtra(Intent.EXTRA_SUBJECT))
        text.setText(intent.getStringExtra(Intent.EXTRA_TEXT))
    }
}
