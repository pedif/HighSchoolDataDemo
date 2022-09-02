package com.pedif.nyhighschoolinfo.data.repository

/**
 * The repository would be the only
 * source of truth for our app
 */
interface Repository {

    /**
     * Returns data regarding all available high schools
     * @see Result
     */
    suspend fun getHighSchoolData():Result

    /**
     * Returns data regarding all SATs available for a high school
     * @param schoolDbn the unique dbn of a school
     * @see Result
     */
    suspend fun getHighSchoolSatData(schoolDbn:String): Result

    /**
     * Returns data regarding a school
     * @param schoolDbn the unique dbn of a school
     * @see Result
     */
    suspend fun getHighSchoolByDbn(schoolDbn: String):Result

}