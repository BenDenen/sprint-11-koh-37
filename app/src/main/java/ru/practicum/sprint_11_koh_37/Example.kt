package ru.practicum.sprint_11_koh_37

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.practicum.sprint_11_koh_33.CustomDateTypeAdapter
import ru.practicum.sprint_11_koh_33.NewsResponse
import java.util.Date

private const val BASE_URL = "https://raw.githubusercontent.com/BenDenen/sprint-11-koh-37/"

interface GetNewsDataService {

    @GET("refs/heads/master/jsons/news_1.json")
    fun getOldNews(): Call<NewsResponse>

    @GET("refs/heads/master/jsons/news_2.json")
    fun getNewNews(): Call<NewsResponse>
}

interface UsersDataService {

    @POST("api/vi/login")
    fun login(@Body loginData:UserRequest): Call<NewsResponse>

    @POST("api/v1/register")
    fun register(@Body loginData:UserRequest): Call<NewsResponse>
}

data class UserRequest(
    val login: String,
    val password: String
)

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(
        GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(Date::class.java, CustomDateTypeAdapter())
                .create()
        )
    )
    .build()

val serverApi = retrofit.create(GetNewsDataService::class.java)

fun test() {
    serverApi.getOldNews().enqueue( object: retrofit2.Callback<NewsResponse> {
        override fun onResponse(call: Call<NewsResponse>, response: retrofit2.Response<NewsResponse>) {
            println("onResponse: ${response.code()} ${response.body()}")
        }

        override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
            println("onFailure: $t")
        }

    })
    serverApi.getNewNews()
}


val userApi = retrofit.create(UsersDataService::class.java)
fun test2() {
    userApi.login(UserRequest("login", "password"))
    userApi.register(UserRequest("login", "password"))
}