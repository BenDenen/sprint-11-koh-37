package ru.practicum.sprint_11_koh_33

import android.os.Build
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class NewsResponse(
    val result: String,
    val data: Data
)

data class Data(
    val title: String,
    val items: List<NewsItem>
)

//data class NewsItem(
//    val id: String,
//    val title: String,
//    val type: String,
//    val created: Date,
//    val specificPropertyForSport: String,
//    @SerializedName("specific_property_for_science")
//    val specificPropertyForScience:String
//)

sealed class NewsItem {

    data class Sport(
        val id: Int,
        val title: String,
        val type: String,
        val created: Date,
        val specificPropertyForSport: String
    ) : NewsItem()

    data class Science(
        val id: Int,
        val title: String,
        val type: String,
        val created: Date,
        @SerializedName("specific_property_for_science")
        val specificPropertyForScience: String
    ) : NewsItem()

}

fun test() {
}

class CustomDateTypeAdapter : TypeAdapter<Date>() {

    // https://ru.wikipedia.org/wiki/ISO_8601
    companion object {

        const val FORMAT_PATTERN = "yyyy-MM-DD'T'hh:mm:ss:SSS"
    }

    private val formatter = SimpleDateFormat(FORMAT_PATTERN, Locale.getDefault())
    override fun write(out: JsonWriter, value: Date) {
        out.value(formatter.format(value))
    }

    override fun read(`in`: JsonReader): Date {
        return formatter.parse(`in`.nextString())
    }

}

class NewsDeserializer : JsonDeserializer<NewsItem> {

    override fun deserialize(
        json: JsonElement,
        type: Type,
        context: JsonDeserializationContext
    ): NewsItem {
        val textType = json.asJsonObject.getAsJsonPrimitive("type").asString
        return when (textType) {
            "sport" -> context.deserialize(json, NewsItem.Sport::class.java)
            "science" -> context.deserialize(json, NewsItem.Science::class.java)
            else -> throw IllegalArgumentException(" Че за тип $textType")
        }
    }

}
