package project.st991532818.org

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth


class RegistrationActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        var email: EditText? = findViewById(R.id.email)
        var password: EditText? = findViewById(R.id.password)
        var registerBtn: Button? = findViewById(R.id.registerBtn)
        var registerQn: TextView? = findViewById(R.id.registerQn)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        registerQn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
        })
        registerBtn?.setOnClickListener(View.OnClickListener {
            val emailString = email?.text.toString()
            val passwordString = password?.text.toString()
            if (TextUtils.isEmpty(emailString)) {
                email?.error = "Email is required"
            }
            if (TextUtils.isEmpty(passwordString)) {
                password?.error = "Password is required"
            } else {
                progressDialog!!.setMessage("registration in progress")
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.show()
                mAuth!!.createUserWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(
                                this@RegistrationActivity,
                                MainActivity::class.java
                            )
                            startActivity(intent)
                            finish()
                            progressDialog!!.dismiss()
                        } else {
                            Toast.makeText(
                                this@RegistrationActivity,
                                task.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            progressDialog!!.dismiss()
                        }
                    }
            }
        })
    }
}