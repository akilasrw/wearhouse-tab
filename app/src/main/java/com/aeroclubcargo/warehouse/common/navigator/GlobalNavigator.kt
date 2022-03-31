package com.aeroclubcargo.warehouse.common.navigator

object GlobalNavigator {

    private var handler: GlobalNavigationHandler? = null

    fun registerHandler(handler: GlobalNavigationHandler) {
        this.handler = handler
    }

    fun unregisterHandler() {
        handler = null
    }

    fun logout() {
        handler?.logout()
    }
}

