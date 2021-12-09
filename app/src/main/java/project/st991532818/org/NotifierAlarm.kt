package project.st991532818.org

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import project.st991532818.org.models.PaymentReminder
import project.st991532818.org.models.Reminder
import project.st991532818.org.ui.reminders.ReminderFragment
import java.util.*

class NotifierAlarm : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val intent1 = Intent(context, ReminderFragment::class.java)

        val r = PaymentReminder()
        r.category = intent!!.getStringExtra("category").toString()//"test"
        r.amount = intent.getDoubleExtra("amount",0.0)
        r.date = intent.getStringExtra("date").toString()
        r.note = intent.getStringExtra("note").toString()
        r.payee = intent.getStringExtra("payee").toString()

        val alarmsound : Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)



        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val taskStackBuilder = TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(MainActivity::class.java)
        taskStackBuilder.addNextIntent(intent1)

        val intent2 = taskStackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(context!!)

        var channel: NotificationChannel? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel =
                NotificationChannel("my_channel_01", "hello", NotificationManager.IMPORTANCE_HIGH)
        }

        val notification: Notification = builder.setContentTitle("Reminder! Payment of $" + r.amount +" to " + r.payee)
//            .setContentText(intent!!.getStringExtra("Message")).setAutoCancel(true)
//            .setSound(alarmsound).setSmallIcon(R.mipmap.ic_launcher_round)
//            .setContentIntent(intent2)
//            .setChannelId("my_channel_01")
//            .build()
            .setContentText(r.category + ", " + r.note).setAutoCancel(true)
            .setSound(alarmsound).setSmallIcon(R.drawable.applogo)
            .setContentIntent(intent2)
            .setChannelId("my_channel_01")
            .build()

        val notificationManager : NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel!!)
        }
        notificationManager.notify(77, notification)
    }

}