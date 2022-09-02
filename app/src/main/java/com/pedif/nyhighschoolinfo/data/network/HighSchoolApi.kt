package com.pedif.nyhighschoolinfo.data.network

import com.pedif.nyhighschoolinfo.common.Constants
import com.pedif.nyhighschoolinfo.data.model.HighSchool
import com.pedif.nyhighschoolinfo.data.model.SAT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit API
 */
interface HighSchoolApi {

    /**
     * get a list of all high schools available
     */
    @GET(Constants.HIGH_SCHOOL_LIST)
    suspend fun getHighSchools(): Response<List<HighSchool>>

    /**
     * Get a list of all available SATs for a certain school based on its dbn
     */
    @GET(Constants.SAT_LIST)
    suspend fun getHighSchoolSATs(@Query("dbn") dbn: String): Response<List<SAT>>

    /**
     * Get a list of all available schools based on its dbn
     */
    @GET(Constants.HIGH_SCHOOL_LIST)
    suspend fun getHighSchoolByDbn(@Query("dbn") dbn: String): Response<List<HighSchool>>
}