package com.example.recyclerview.pages

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.recyclerview.MainActivity
import com.example.recyclerview.R
import com.example.recyclerview.services.API_interface
import com.example.recyclerview.utils.RequestToken
import com.example.recyclerview.utils.SessionResponse
import com.example.recyclerview.utils.TokenInfo
import com.example.recyclerview.utils.UserSession
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginPage : AppCompatActivity() {

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(com.example.recyclerview.BASE_URL)
        .build()
        .create(API_interface::class.java)

    var myToken: String = ""
    val session: UserSession = UserSession()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        val loginButton: Button = findViewById(R.id.btnLogin)
        val signInButton: Button = findViewById(R.id.btnSignIn)
        session.MySession(this)
        getToken()


        loginButton.setOnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse("https://www.themoviedb.org/authenticate/$myToken")
            startActivity(openURL)
        }

        signInButton.setOnClickListener{
            getSessionID(myToken)
        }

    }

    private fun getToken() {
        val response = retrofitBuilder.getTokenRequest()
        response.enqueue(object : Callback<RequestToken> {
            override fun onResponse(
                call: Call<RequestToken>,
                response: Response<RequestToken>
            ) {
                myToken = response.body()!!.request_token
                Log.d("Tokerino", myToken)

            }
            //71d5a140e1f88c195710e2e6527f1861339aa5c4
            //1ccbfd63a5027987bf1deb6035fa250ab9359498
            override fun onFailure(call: Call<RequestToken>, t: Throwable) {
                TODO("Not yet implemented")
            }


        })
    }

    private fun getSessionID(token3: String){
        val tokerino = TokenInfo(token3)
        val response2 = retrofitBuilder.getSessionID(tokerino)

        response2.enqueue(object: Callback<SessionResponse?>{
            override fun onResponse(
                call: Call<SessionResponse?>,
                response: Response<SessionResponse?>
            ) {
                val sessionResponse = response.body()
                if(sessionResponse==null){
                    Toast.makeText(this@LoginPage, "¡Algo ha salido mal! Intenta de nuevo", Toast.LENGTH_LONG).show()
                }else{
                    session.setUserSession(sessionResponse.session_id)
                    succesfulLogin(sessionResponse.session_id)
                }
            }

            override fun onFailure(call: Call<SessionResponse?>, t: Throwable) {
                Toast.makeText(this@LoginPage, t.message.toString(), Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun succesfulLogin(sessionID: String){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

}