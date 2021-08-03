import com.google.gson.annotations.SerializedName

data class Rgb (

	@SerializedName("red") val red : Int,
	@SerializedName("green") val green : Int,
	@SerializedName("blue") val blue : Int
)