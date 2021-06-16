package com.folksdev.account.dto

import java.math.BigDecimal
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

data class CreateAccountRequest(
    @field:NotBlank
    val customerId: String,
    @field:Min(0)
    val initialCredit: BigDecimal
)