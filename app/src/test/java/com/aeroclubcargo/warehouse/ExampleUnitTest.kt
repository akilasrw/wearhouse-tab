package com.aeroclubcargo.warehouse

import com.aeroclubcargo.warehouse.utils.updateTimeOnly
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var dateTime = "2023-07-02T10:30:00"
        var updateTime =  dateTime.updateTimeOnly(3,45)
        assertEquals("2023-07-02T03:45:00", updateTime)
    }
}