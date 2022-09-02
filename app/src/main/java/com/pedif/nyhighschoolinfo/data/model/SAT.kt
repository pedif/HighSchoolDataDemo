package com.pedif.nyhighschoolinfo.data.model

/**
 * Model class representing a SAT object.
 * Modeled based on API's json data
 */
data class SAT(
    val dbn: String,
    val num_of_sat_test_takers: String,
    val sat_critical_reading_avg_score: String,
    val sat_math_avg_score: String,
    val sat_writing_avg_score: String,
    val school_name: String
)