package core.breaking

import com.android.breakingbad.domain.model.BreakingBadDataItem
import com.android.breakingbad.domain.model.BreakingBadPayLoad
import com.google.gson.*
import java.lang.reflect.Type

class BreakingBadCustomDeserialier : JsonDeserializer<BreakingBadPayLoad> {
    var listOfBreakingBadItems = mutableListOf<BreakingBadDataItem>()
    var breakingBadPayLoad = BreakingBadPayLoad()
    var gson = Gson()

    @Throws(JsonParseException::class)
    override fun deserialize(
        jElement: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): BreakingBadPayLoad? {
        var jObject = JsonObject()
        var jsonArray: JsonArray
        if (jElement is JsonObject) {
            jObject = jElement.getAsJsonObject()
        } else if (jElement is JsonArray) {
            jsonArray = jElement.getAsJsonArray()
            jsonArray.forEach {
                listOfBreakingBadItems.add(
                    gson.fromJson(it?.asJsonObject, BreakingBadDataItem::class.java)
                )
            }
        }

        breakingBadPayLoad.list = listOfBreakingBadItems.apply {
            filter { !it.appearance.isNullOrEmpty() }.map {
                breakingBadPayLoad.hashSetSeasons.addAll(
                    it.appearance
                )
            }
        }

        return breakingBadPayLoad
    }
}




