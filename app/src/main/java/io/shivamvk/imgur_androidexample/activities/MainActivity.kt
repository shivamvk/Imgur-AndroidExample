package io.shivamvk.imgur_androidexample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.shivamvk.imgur_androidexample.network.ApiFactory
import io.shivamvk.imgur_androidexample.Constants
import io.shivamvk.imgur_androidexample.R
import io.shivamvk.imgur_androidexample.adapters.RvMainImagesAdapter
import io.shivamvk.imgur_androidexample.models.ResponseModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var rvMainImages: RecyclerView
    private lateinit var rvMainImagesAdapter: RecyclerView.Adapter<*>
    private lateinit var rvMainImagesLayoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMainImagesLayoutManager = LinearLayoutManager(this)
        rvMainImages = findViewById<RecyclerView>(R.id.rv_main_images).apply {
            setHasFixedSize(true)
            layoutManager = rvMainImagesLayoutManager
        }

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
                        Log.i("api: Size of list", response.body()!!.data.size.toString())
                        rvMainImagesAdapter = RvMainImagesAdapter(response.body()!!.data)
                        rvMainImages.adapter = rvMainImagesAdapter
                    }
                })
            }
        }
    }
}
