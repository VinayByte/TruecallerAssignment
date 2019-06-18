package com.vinay.truecallerassignment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ovrbach.truecallerchallenge.hide
import com.ovrbach.truecallerchallenge.show
import com.vinay.truecallerassignment.network.RequestStatus
import com.vinay.truecallerassignment.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupLiveDataObservation()

        fab.setOnClickListener { view ->
            makeRequest()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupLiveDataObservation() {
        viewModel.resultOne.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    txtTenthCharRequest.text = getString(R.string.tenth_char_req, response.data.toString())
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })

        viewModel.resultTwo.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    txtEveryTenthChar.text = response.data.toString()
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })

        viewModel.resultThree.observe(this, Observer { response ->
            when (response) {
                is RequestStatus.Success -> {
                    onSuccess()
                    txtWordCounter.text = response.data.toString()
                }
                is RequestStatus.Failure -> {
                    onError(response.error.localizedMessage)
                }
                is RequestStatus.Requesting -> {
                    onStartRequesting()
                }
                is RequestStatus.Cancelled -> {
                    //TODO
                }
            }
        })
    }

    private fun onStartRequesting() {
        main_progress.show()
        main_exception_text.hide()
        main_results_layout.hide()
        fab.hide()
    }

    private fun onError(error: String) {
        main_exception_text.text = getString(R.string.exception, error)

        main_progress.hide()
        main_exception_text.show()
        main_results_layout.hide()

        fab.show()
        fab.text = getString(R.string.try_again)
    }

    private fun onSuccess() {
        main_progress.hide()
        main_exception_text.hide()
        main_results_layout.show()

        fab.show()
        fab.text = getString(R.string.fetch)
    }

    private fun makeRequest() {
        viewModel.runRequests()
    }
}