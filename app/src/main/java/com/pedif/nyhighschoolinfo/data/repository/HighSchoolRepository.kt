package com.pedif.nyhighschoolinfo.data.repository

import com.pedif.nyhighschoolinfo.data.network.HighSchoolApi
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class HighSchoolRepository @Inject constructor(private val api: HighSchoolApi) : Repository {

    override suspend fun getHighSchoolData(): Result {
        val response = try {
            api.getHighSchools()
        } catch (e: IOException) {
            Timber.e("IOException, ${e.message}")
            return Result(false, "IO ERROR")
        } catch (e: HttpException) {
            Timber.e("HttpException, ${e.message}")
            return Result(false, "HTTP ERROR")
        }
        return if (response.isSuccessful && response.body() != null) {
            Result(true, response.body()!!)
        } else {
            Timber.e("Response not successful")
            Result(false, "Response ERROR")
        }
    }

    override suspend fun getHighSchoolSatData(schoolDbn: String): Result {
        val response = try {
            api.getHighSchoolSATs(schoolDbn)
        } catch (e: IOException) {
            Timber.e("IOException, ${e.message}")
            return Result(false, "IO ERROR")
        } catch (e: HttpException) {
            Timber.e("HttpException, ${e.message}")
            return Result(false, "HTTP ERROR")
        }
        return if (response.isSuccessful && response.body() != null) {
            Result(true, response.body()!!)
        } else {
            Timber.e("Response not successful")
            Result(false, "Response ERROR")
        }
    }

    override suspend fun getHighSchoolByDbn(schoolDbn: String): Result {
        val response = try {
            api.getHighSchoolByDbn(schoolDbn)
        } catch (e: IOException) {
            Timber.e("IOException, ${e.message}")
            return Result(false, "IO ERROR")
        } catch (e: HttpException) {
            Timber.e("HttpException, ${e.message}")
            return Result(false, "HTTP ERROR")
        }
        return if (response.isSuccessful && response.body() != null) {
            /*The api would return an array but we know dbns are unique
                so we just need to get the first item within that array
             */
            Result(true, response.body()!![0])
        } else {
            Timber.e("Response not successful")
            Result(false, "Response ERROR")
        }
    }

}