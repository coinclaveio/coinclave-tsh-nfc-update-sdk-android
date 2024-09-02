package io.coinclave.crypto.applet.update.dto

import java.io.Serializable

abstract class SmartCardResult: Serializable {

    var errors = ArrayList<Error>()

}