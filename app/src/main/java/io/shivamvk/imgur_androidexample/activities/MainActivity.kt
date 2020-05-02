package io.shivamvk.imgur_androidexample.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.shivamvk.imgur_androidexample.network.ApiFactory
import io.shivamvk.imgur_androidexample.utils.Constants
import io.shivamvk.imgur_androidexample.adapters.RvMainImagesAdapter
import io.shivamvk.imgur_androidexample.databinding.ActivityMainBinding
import io.shivamvk.imgur_androidexample.models.ResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvMainImagesAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Imgur Client"
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setUpRecyclerView()
        setUpFAB()
        fillData()

    }

    fun setUpFAB(){
        binding.fabMainUpload.setOnClickListener { view ->
            startActivity(Intent(this, UploadImageActivity::class.java))
        }
    }

    fun fillData(){
        val service = ApiFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val callGetImages: Call<ResponseModel> = service.getImages("Client-ID " + Constants.client_id)
            withContext(Dispatchers.Main){
                callGetImages.enqueue(object : Callback<ResponseModel>{
                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        Log.i("api", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        Log.i("api: ", "Response received")
                        var displayMetrics = DisplayMetrics()
                        windowManager.defaultDisplay.getMetrics(displayMetrics)
                        rvMainImagesAdapter = RvMainImagesAdapter(
                            applicationContext,
                            supportFragmentManager,
                            response.body()!!.data,
                            displayMetrics.widthPixels,
                            displayMetrics.heightPixels)
                        binding.rvMainImages.adapter = rvMainImagesAdapter
                    }
                })
            }
        }
    }

    fun setUpRecyclerView(){
        binding.rvMainImages.setHasFixedSize(true)
        binding.rvMainImages.layoutManager = LinearLayoutManager(this)
    }
}
