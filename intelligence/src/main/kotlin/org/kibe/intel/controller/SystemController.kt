package org.kibe.intel.controller

import org.eclipse.jetty.http.HttpStatus
import org.kibe.common.response.RESTResponse
import spark.Request
import spark.Response
import spark.Spark.get

class SystemController {

    private val PREFIX = "/system"

    fun testSystem(request: Request, response: Response) =
        RESTResponse.Builder().code(HttpStatus.OK_200).build()


    fun init() {
        get("$PREFIX/", this::testSystem)
    }

}