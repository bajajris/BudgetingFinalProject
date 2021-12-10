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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Name: Raj Rajput
 * Student Id: 991550770
 * Date: 2021-12-04
 * Description: Login Activity
 */
class LoginActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var progressDialog: ProgressDialog? = null
    private var authStateListener: FirebaseAuth.AuthStateListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        authStateListener = FirebaseAuth.AuthStateListener {
            val user: FirebaseUser? = mAuth?.currentUser
            if (user != null) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        var email: EditText? = findViewById(R.id.email)
        var password: EditText? = findViewById(R.id.password)
        var loginBtn: Button? = findViewById(R.id.loginBtn)
        var loginQn: TextView? = findViewById(R.id.loginQn)
        mAuth = FirebaseAuth.getInstance()
        progressDialog = ProgressDialog(this)
        loginQn?.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        })
        loginBtn?.setOnClickListener(View.OnClickListener {
            val emailString = email?.getText().toString()
            val passwordString = password?.getText().toString()
            if (TextUtils.isEmpty(emailString)) {
                email?.setError("Email is required")
            }
            if (TextUtils.isEmpty(passwordString)) {
                password?.setError("Password is required")
            } else {
                progressDialog!!.setMessage("login in progress")
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.show()
                mAuth!!.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                        progressDialog!!.dismiss()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            task.exception.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        progressDialog!!.dismiss()
                    }
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        authStateListener?.let { mAuth?.addAuthStateListener(it) }
    }

    override fun onStop() {
        super.onStop()
        mAuth?.removeAuthStateListener(authStateListener!!)
    }
}