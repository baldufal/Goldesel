package baldufal.goldesel.model

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    @TypeConverter
    fun long2ldt(value: Long): LocalDateTime {
        return LocalDateTime.ofEpochSecond(value, 0, ZoneOffset.UTC)
    }

    @TypeConverter
    fun ldt2long(value: LocalDateTime): Long {
        return value.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun string2list(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun list2string(value: List<String>): String {
        val sortedTags = value.sorted()
        return value.joinToString(",")
    }
}