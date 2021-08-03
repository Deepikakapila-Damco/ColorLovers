import com.google.gson.annotations.SerializedName

data class Hsv (

	@SerializedName("hue") val hue : Int,
	@SerializedName("saturation") val saturation : Int,
	@SerializedName("value") val value : Int
)