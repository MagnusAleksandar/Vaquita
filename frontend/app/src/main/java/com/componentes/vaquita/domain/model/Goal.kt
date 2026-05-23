package com.example.vaquita.model

import android.media.Image
import java.time.LocalDate

data class Goal(val _id: String?,
                var name: String,
                var dueDate: LocalDate,
                var contributions: MutableList<Contribution>,
                var image: Image)
