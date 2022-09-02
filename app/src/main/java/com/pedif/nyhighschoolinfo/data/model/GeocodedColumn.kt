package com.pedif.nyhighschoolinfo.data.model

/**
 * Model class used in Highschool
 * @see HighSchool
 */
data class GeocodedColumn(
    val coordinates: List<Double>,
    val type: String
)