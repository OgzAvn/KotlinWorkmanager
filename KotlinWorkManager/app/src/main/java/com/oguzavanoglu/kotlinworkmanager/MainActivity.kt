package com.oguzavanoglu.kotlinworkmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val data = Data.Builder().putInt("intKey",1).build()

        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(false)
            .build()

        /*
        val myworkrequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            //.setInitialDelay(5,TimeUnit.MINUTES)
            //.addTag("myTag")
            .build()

        WorkManager.getInstance(this@MainActivity).enqueue(myworkrequest) //oluşturduğum iş isteğini burada sıraya alıyorum

         */


        val myworkrequest : WorkRequest = PeriodicWorkRequestBuilder<RefreshDatabase>(15,TimeUnit.MINUTES) //En az 15dk da bir yapabiliyoruz.
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).enqueue(myworkrequest)

        WorkManager.getInstance(this@MainActivity).getWorkInfoByIdLiveData(myworkrequest.id).observe(this,
            Observer {

                if (it.state==WorkInfo.State.RUNNING){

                    println("running")

                }else if (it.state == WorkInfo.State.FAILED){

                    println("failed")
                }else if (it.state == WorkInfo.State.SUCCEEDED){
                    println("succeded")
                }

            })

        //WorkManager.getInstance(this).cancelAllWork()

        //Chaining

        //Workmanager sen bu işlemle başla o bitsin şunu yap o bitsin bunu yap diyebiliyoruz. Periodik requestlerde yapamazsın sadece onetime requestlerde yapabiliyoruz

        /*
        val oneTimeWorkRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshDatabase>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .then(oneTimeWorkRequest)
            .enqueue()


         */
    }
}