package vtan.nickel.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonValue

data class Category(@get:[JsonIgnore JsonValue] val name: String)
