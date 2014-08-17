package sample.snake

import javafx.application.Application
import javafx.stage.Stage
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.Group
import javafx.scene.shape.Rectangle
import javafx.scene.shape.StrokeType
import javafx.animation.Timeline
import javafx.animation.KeyFrame
import javafx.util.Duration
import javafx.animation.TranslateTransition
import javafx.scene.shape.Circle
import javafx.animation.ParallelTransition
import javafx.animation.Animation
import javafx.scene.input.KeyCode
import java.util.concurrent.CopyOnWriteArrayList
import java.util.EventListener
import javafx.scene.input.KeyEvent
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.LinkedList
import javafx.scene.text.Text
import javafx.scene.control.Label
import java.util.Random
import javafx.geometry.Pos
import javafx.beans.value.ObservableStringValue
import javafx.beans.property.SimpleStringProperty
import javafx.scene.layout.BorderPane
import javafx.scene.layout.StackPane

/**
 * Created by Nikita.Skvortsov
 * date: 10.08.2014.
 */


public fun main(args: Array<String>) {
    Application.launch(javaClass<Snake>(), *(args as Array<String?>))
}


public class Snake() : Application() {

    override fun start(primaryStage: Stage) {
        val BOARD_SIZE = 12
        val SNAKE_LEN = 5
        val TICK = Duration.seconds(0.2)

        var scoreNum = 0
        val score = SimpleStringProperty("Score: 0")

        val initial_d = Pair(1, 0)

        val keyQueue = ConcurrentLinkedQueue<KeyCode>()
        val snake: LinkedList<Pair<Int, Int>> = linkedListOf(initial_d)

        val root = Group()
        val scene = Scene(root, 800.0, 800.0, Color.BLACK)
        primaryStage.setScene(scene)

        root.getChildren().add(Rectangle(scene.getWidth(), scene.getHeight(), Color.BLACK))
        val squares = Group()
        root.getChildren().add(squares)

        val ar = Array(BOARD_SIZE) { Array(BOARD_SIZE) {  Rectangle(scene.getWidth() / BOARD_SIZE,
                                                                    scene.getHeight() / BOARD_SIZE,
                Color.WHITE)  } }
        ar.withIndices().forEach { p ->
            val (j, ar1) = p
            ar1.withIndices().forEach { q ->
                val (k, square) = q
                squares.getChildren().add(square)
                with(square) {
                    setX(k * getWidth())
                    setY(j * getHeight())
                    setStrokeType(StrokeType.INSIDE);
                    setStroke(Color.BLACK);
                    setStrokeWidth(1.0);
                }
            }
        }


        var hd = Pair(3,3)
        var tl = Pair(3,3)

        fun addVect(point: Pair<Int, Int>, dvect: Pair<Int, Int>): Pair<Int, Int> {
            val (dx, dy) = dvect
            val (x, y) = point
            return Pair((x + dx + BOARD_SIZE) % BOARD_SIZE, (y + dy + BOARD_SIZE) % BOARD_SIZE)
        }

        val scoreL = Label()
        with(scoreL) {
            textProperty()?.bind(score)
            setTextFill(Color.BLUE);
            setStyle("-fx-font-size: 4em;");
        }
        root.getChildren().add(scoreL)

        val gameTick = Timeline()

        val list = ar.flatMap { ar1 -> ar1.toList() } filter { it.getFill() == Color.WHITE }
        list.drop(Random().nextInt(list.size - 1)).first!!.setFill(Color.GREEN)

        gameTick.getKeyFrames().add(KeyFrame(TICK, {

            when (keyQueue.poll()) {
                KeyCode.UP ->    if (Pair(0,1) != snake.first) snake.set(0, Pair(0,-1))
                KeyCode.DOWN ->  if (Pair(0,-1) != snake.first) snake.set(0, Pair(0, 1))
                KeyCode.LEFT ->  if (Pair(1,0) != snake.first) snake.set(0, Pair(-1, 0))
                KeyCode.RIGHT -> if (Pair(-1,0) != snake.first) snake.set(0, Pair(1, 0))
            }

            val first = snake.first!!
            val newHd = addVect(hd, first)
            val (newHdX, newHdY) = newHd
            val nextSq = ar[newHdY][newHdX]

            when(nextSq.getFill()) {
                Color.WHITE -> {
                    nextSq.setFill(Color.BLACK)
                    nextSq.setStroke(Color.WHITE)
                }
                Color.GREEN -> {
                    nextSq.setFill(Color.BLACK)
                    snake.add(Pair(0,0))
                    val list = ar.flatMap { ar1 -> ar1.toList() } filter { it.getFill() == Color.WHITE }
                    list.drop(Random().nextInt(list.size - 1)).first!!.setFill(Color.GREEN)
                    scoreNum += 10
                    score.setValue("Score: " + scoreNum)
                }
                Color.BLACK -> {
                    gameTick.stop()
                    score.setValue("GAME OVER. Score: " + scoreNum)
                    scoreL.setTextFill(Color.RED)
                }
            }

            hd = newHd
            snake.add(0, first)

            if (snake.size > SNAKE_LEN) {
                val last = snake.removeLast()
                val newTl = addVect(tl, last)
                val (newTlX, newTlY) = newTl

                ar[newTlY][newTlX].setFill(Color.WHITE);
                ar[newTlY][newTlX].setStroke(Color.BLACK);
                tl = newTl
            }

        }, array()));

        gameTick.setCycleCount(Animation.INDEFINITE)
        gameTick.play()

        scene.setOnKeyPressed { (key:KeyEvent?):Unit ->
            val keyCode = key?.getCode()
            if (keyCode != null && keyCode in listOf(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT, KeyCode.RIGHT)) {
                keyQueue.add(keyCode)
            }
        }


        primaryStage.show()
    }


}
