package com.oguzavanoglu.kotlinworkmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshDatabase(val context: Context, workerParams: WorkerParameters) : Worker(context,
    workerParams
) {



    override fun doWork(): Result {

        val getData = inputData

        val myNumber = getData.getInt("intKey",0)

        refreshdatabase(myNumber)

        return Result.success()
    }

    private fun refreshdatabase(myNumber : Int){

        val sharedPreferences = context.getSharedPreferences("com.oguzavanoglu.kotlinworkmanager",Context.MODE_PRIVATE)
        var mysavednumber = sharedPreferences.getInt("mynumber",0)
        mysavednumber = mysavednumber + myNumber
        println(mysavednumber)
        sharedPreferences.edit().putInt("mynumber",mysavednumber).apply()
    }


}