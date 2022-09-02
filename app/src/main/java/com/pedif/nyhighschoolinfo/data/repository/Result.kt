package com.pedif.nyhighschoolinfo.data.repository

/**
 * A simple class representing the data our functions would
 * receive after interacting with the repository
 * @param isSuccessful whether the task was successful
 * @param data the complementary data of the task if available
 */
data class Result(val isSuccessful:Boolean, val data:Any?)
