package com.bms.moviebook.model.cast


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CastResponse(
    @SerializedName("cast")
    val cast: MutableList<Cast> = mutableListOf<Cast>(),
    @SerializedName("crew")
    val crew: MutableList<Crew> = mutableListOf<Crew>(),
    @SerializedName("id")
    val id: Int = 0
) {
    @Keep
    data class Cast(
        @SerializedName("cast_id")
        val castId: Int = 0,
        @SerializedName("character")
        val character: String = "",
        @SerializedName("credit_id")
        val creditId: String = "",
        @SerializedName("gender")
        val gender: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("order")
        val order: Int = 0,
        @SerializedName("profile_path")
        val profilePath: String = ""
    )

    @Keep
    data class Crew(
        @SerializedName("credit_id")
        val creditId: String = "",
        @SerializedName("department")
        val department: String = "",
        @SerializedName("gender")
        val gender: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("job")
        val job: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("profile_path")
        val profilePath: Any = Any()
    )
}