package com.aeroclubcargo.warehouse.domain.model

data class Pagination <T>(
        var pageIndex:Int,
        var pageSize:Int,
        var count:Int,
        var data: List<T>? = null)
