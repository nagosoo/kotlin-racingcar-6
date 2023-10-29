package racingcar.controller

import racingcar.common.ExceptionConst
import racingcar.common.GameConst
import racingcar.model.Car
import racingcar.view.View

class Controller(private val view: View) {

    private val cars = mutableListOf<Car>()
    private var validatedTryCnt: Int = 0

    fun startGame() {
        val carNames = enterCarName()
        validateCarNames(carNames)
        val tryCnt = enterTryCnt()
        validateTryCnt(tryCnt)
        makeCar(carNames)
        doGame()
        showWinner()
    }

    private fun enterCarName(): List<String> {
        view.displayMsg(GameConst.ENTER_CAR_NAME_MSG)
        return view.readUserInput().split(",").map { it.trim() }
    }

    private fun validateCarNames(carNames: List<String>) {
        validateCarNameLength(carNames)
        validateCarNameDistinct(carNames)
    }

    private fun validateCarNameLength(carNames: List<String>) {
        carNames.forEach { name ->
            if ((name.length in 1..5).not()) throw IllegalArgumentException(ExceptionConst.EXCEPTION_CAR_NAME_LENGTH_INVALIDATE)
        }
    }

    private fun validateCarNameDistinct(carNames: List<String>) {
        if (carNames.distinct().size != carNames.size) throw IllegalArgumentException(ExceptionConst.EXCEPTION_CAR_NAME_DUPLICATED)
    }

    private fun validateTryCnt(tryCnt: String) {
        validateTryCntIsNumber(tryCnt)
        validateTryCntIsMoreThanZero()
    }

    private fun validateTryCntIsNumber(tryCnt: String) {
        tryCnt.toIntOrNull()?.let {
            validatedTryCnt = it
            return
        }
        throw IllegalArgumentException(ExceptionConst.EXCEPTION_TRY_CNT_NOT_INT)
    }

    private fun validateTryCntIsMoreThanZero() {
        if (validatedTryCnt < 1) {
            validatedTryCnt = 0
            throw IllegalArgumentException(ExceptionConst.EXCEPTION_TRY_CNT_LESS_THAN_ONE)
        }
    }

    private fun makeCar(carNames: List<String>) {
        cars.addAll(
            carNames.map { name ->
                Car(_name = name)
            }
        )
    }

    private fun enterTryCnt(): String {
        view.displayMsg(GameConst.ENTER_TRY_CNT_MSG)
        return view.readUserInput()
    }

    private fun doGame() {
        view.displayMsg()
        view.displayMsg(GameConst.GAME_RESULT_MSG)
        repeat(validatedTryCnt) {
            cars.forEach { car ->
                car.moveForward()
                view.displayMsg(car.getCarPosition())
            }
            view.displayMsg()
        }
    }

    private fun showWinner() {
        cars.maxOfOrNull { it.forwardCnt }?.let { maxForwardCnt ->
            val winner = cars.filter { car ->
                car.forwardCnt == maxForwardCnt
            }.joinToString { it.name }
            view.displayMsg(GameConst.GAME_WINNER_MSG + winner)
        }
    }
}