package com.reason.support.common

/**
 * @author impassive
 */
enum class HttpMethod(
    val method: String
) {

    NO_METHOD("ALL"),

    GET("GET"),

    POST("POST"),

    DELETE("DELETE"),

    PUT("PUT"),

    PATCH("PATCH"),

    OPTIONS("OPTIONS"),

    TRACE("TRACE"),

    HEAD("HEADw");


    companion object {

        fun parseMethod(method: String): HttpMethod {
            for (value in values()) {
                if (value.method.lowercase() == method.lowercase()) {
                    return value
                }
            }
            throw IllegalArgumentException("un known method:$method")
        }

    }


}