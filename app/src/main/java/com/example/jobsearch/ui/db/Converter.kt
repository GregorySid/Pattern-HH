package com.example.jobsearch.ui.db

import androidx.room.TypeConverter
import com.example.jobsearch.ui.dModel.DataAddress
import com.example.jobsearch.ui.dModel.DataExperience

class Converter {
    @TypeConverter
    fun fromAddress(address: DataAddress): String{
        return address.town
    }

    @TypeConverter
    fun toAddress(town: String): DataAddress{
        return DataAddress(town)
    }

    @TypeConverter
    fun fromExperience(experience: DataExperience): String{
        return experience.text
    }

    @TypeConverter
    fun toExperience(text: String): DataExperience {
        return DataExperience(text, text)
    }
}