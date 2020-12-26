package com.micrommer.filing.domain

import java.util.*

/**
 * Project : filing
 * Ashiyane: com.micrommer.filing.domain
 * @author : iman
 * @since : December/26/2020 22:34
 */
data class LogDetail(
        val username : String,
        val path : String,
        val method : String,
        val date : Date
)