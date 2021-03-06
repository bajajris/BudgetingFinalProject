package project.st991532818.org

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.auth.FirebaseAuth
import project.st991532818.org.databinding.ActivityMainBinding
/**
 * Name: Raj Rajput
 * Student Id:991550770
 * Date: 2021-12-04
 * Description: Main Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // register notification channel
//        NotificationHelper.createNotificationChannel(this,
//            NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
//            getString(R.string.app_name), "App notification channel.")


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_addexpense
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        var hv = navView.getHeaderView(0)
        var displayEmail = FirebaseAuth.getInstance().currentUser?.email
        hv.findViewById<TextView>(R.id.navHeaderTextView).text = displayEmail
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_about -> {
                showAbout()
                true
            }
            R.id.action_help -> {
                showHelp()
                true
            }
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showAbout(){
        val builder = AlertDialog.Builder(this)

        var mView: View = LayoutInflater.from(this).inflate(R.layout.about_dialog, null)
        builder.setTitle("About")

        builder.setView(mView)


        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            dialog.cancel()
        }
        var dialog = builder.create()

        dialog.show()
    }

    fun showHelp(){
        val builder = AlertDialog.Builder(this)

        var mView: View = LayoutInflater.from(this).inflate(R.layout.help_dialog, null)
        builder.setTitle("Help")

        builder.setView(mView)


        builder.setPositiveButton(android.R.string.ok) { dialog, which ->
            dialog.cancel()
        }
        var dialog = builder.create()

        dialog.show()
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}