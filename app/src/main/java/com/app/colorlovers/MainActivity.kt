package com.app.colorlovers

import DataModel
import android.app.Activity
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.colorlovers.adapters.RecyclerViewAdapter
import com.bfcassociates.rfselection.apirequests.RestServiceHandler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity(), View.OnClickListener {

    var disposable: Disposable? = null
    val appApiServe by lazy {
        RestServiceHandler.create()
    }
    var list: List<DataModel>? = null
    var rv_images: RecyclerView? = null
    var et_search : EditText ?= null
    var progress_Load_Colors : ProgressBar ?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    fun initUi() {
        et_search = findViewById<EditText>(R.id.et_search)
        val btn_search = findViewById<Button>(R.id.btn_search)
        rv_images = findViewById<RecyclerView>(R.id.rv_images)
        progress_Load_Colors = findViewById<ProgressBar>(R.id.progressBar)
        callApiToGetColors(KEY_WORD);
        btn_search.setOnClickListener(this)

    }

    private fun callApiToGetColors(keyword: String) {
        progress_Load_Colors?.visibility = View.VISIBLE
        disposable =
                appApiServe.getListOfColors(keyword, "json", "20")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ result ->
                            Log.d("MainActivity", "Response is: " + result)
                            list = result
                            val myAdapter = RecyclerViewAdapter(this, list!!)
                            rv_images?.layoutManager =
                                    GridLayoutManager(this, 2)
                            rv_images?.adapter = myAdapter
                            progress_Load_Colors?.visibility = View.GONE

                        },
                                { error ->
                                    progress_Load_Colors?.visibility = View.GONE
                                    Toast.makeText(
                                            applicationContext,
                                            "Item Not Found Error!",
                                            Toast.LENGTH_SHORT
                                    ).show()
                                    Log.d("MainActivity", "error res is: " + error.message.toString())
                                })
    }




    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_search -> {
               var data = et_search?.text.toString()
                hideKeyboard(v)
                callApiToGetColors(data);
            }
        }
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}