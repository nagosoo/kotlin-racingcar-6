package racingcar.view

import camp.nextstep.edu.missionutils.Console

class View {
     fun displayMsg(msg: String="") {
        println(msg)
    }

    fun readUserInput(): String {
        return Console.readLine()
    }
}