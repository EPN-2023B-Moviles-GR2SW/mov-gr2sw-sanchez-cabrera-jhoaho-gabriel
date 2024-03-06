package com.example.proyecto_02.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.proyecto_02.R
import com.example.proyecto_02.entities.Entrada
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EntriesHolder(view: View): ViewHolder(view) {

    val dayNumber = view.findViewById<TextView>(R.id.tv_day_number)
    val dayOfWeek = view.findViewById<TextView>(R.id.tv_day_week)
    val entryTitle = view.findViewById<TextView>(R.id.tv_entry_title)
    val timeMark = view.findViewById<TextView>(R.id.tv_time)
    val weatherImage = view.findViewById<ImageView>(R.id.iv_weather)
    val moodImage = view.findViewById<ImageView>(R.id.iv_mood)
    val saveImage = view.findViewById<ImageView>(R.id.iv_save)


    fun render(entrada: Entrada){
        val day = entrada.fecha.split("-")
        dayNumber.text = day[2]
        dayOfWeek.text = dateToText(entrada.fecha)
        entryTitle.text = entrada.titulo
        timeMark.text = entrada.hora
        when (entrada.clima) {
            Entrada.Clima.Soleado -> {
                weatherImage.setImageResource(R.drawable.sun_day_weather_symbol)
            }
            Entrada.Clima.Nuboso -> {
                weatherImage.setImageResource(R.drawable.day_cloudy_weather)
            }
            else -> {
                weatherImage.setImageResource(R.drawable.cloudy_rainy_day_weather)
            }
        }
        when (entrada.sentimiento) {
            Entrada.Sentimiento.Feliz -> {
                moodImage.setImageResource(R.drawable.emoji_happy)
            }
            Entrada.Sentimiento.Neutral -> {
                moodImage.setImageResource(R.drawable.neutral_emoji)
            }
            else -> {
                moodImage.setImageResource(R.drawable.emoji_disgusted)
            }
        }

        saveImage.setImageResource(R.drawable.save_icon)

    }

    fun obtenerNombreDia(numeroDiaDeLaSemana: Int): String {
        return when (numeroDiaDeLaSemana) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wen"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            else -> throw IllegalArgumentException("Número de día de la semana no válido: $numeroDiaDeLaSemana")
        }
    }

    fun dateToText(date: String): String {

        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val fecha = formato.parse(date)
        val calendar = Calendar.getInstance()
        calendar.time = fecha
        val numeroDiaDeLaSemana = calendar.get(Calendar.DAY_OF_WEEK)
        return obtenerNombreDia(numeroDiaDeLaSemana)
    }
}