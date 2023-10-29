package racingcar.model

import camp.nextstep.edu.missionutils.Randoms
import racingcar.common.GameConst

data class Car(
    private val _name: String,
    private var _forwardCnt: Int = 0,
) {
    val name
        get() = _name
    val forwardCnt: Int
        get() = _forwardCnt

    fun moveForward() {
        val canMove = Randoms.pickNumberInRange(0, 9) >= 4
        if (canMove) _forwardCnt += 1
    }

    fun getCarPosition(): String {
        return ("$name : ${GameConst.FORWARD_STRING.repeat(_forwardCnt)}")
    }
}