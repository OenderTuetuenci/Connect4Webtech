package controllers

import Connect4._
import controller.controllerComponent.ControllerInterface
import controller.updateGridEvent

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, WebSocket}
import model.gridComponent.GridInterface
import play.api.libs.json.{JsNumber, JsObject, JsValue, Json, Writes}
import play.api.libs.streams.ActorFlow
import akka.actor.ActorSystem
import akka.stream.Materializer
import akka.actor._
import play.api.Play.materializer

import scala.swing.Reactor



class Connect4Controller @Inject() (cc:ControllerComponents)(implicit  system: ActorSystem, mat: Materializer) extends AbstractController(cc){
  val gameController:ControllerInterface = Connect4.controller
  def grid:GridInterface = gameController.grid
  def players:List[Int] = gameController.players

  def connect4: Action[AnyContent] = Action{
    Ok(views.html.connect4(grid,players.head.toString))
  }
  def put(column: Int): Action[AnyContent] = Action{
    gameController.move(column)
    Ok(views.html.connect4(grid,gameController.players.head.toString))
  }
  def about(): Action[AnyContent] = Action {
    Ok(views.html.index())
  }
  def gridToJson : Action[AnyContent] = Action {
    Ok(Json.toJson(grid))
  }
  def gridToJsonWithMove(column:Int): Action[AnyContent] = Action {
    gameController.move(column)
    Ok(Json.toJson(grid))
  }
  def socket: WebSocket = WebSocket.accept[String,String]{ request =>
    ActorFlow.actorRef {out =>
      println("Connect received")
      Connect4WebSocketActorFactory.create(out)
    }
  }
  object Connect4WebSocketActorFactory {
    def create(out: ActorRef): Props = {
      Props(new Connect4WebSocketActor(out))
    }
  }

  class Connect4WebSocketActor(out:ActorRef) extends Actor with Reactor{
    listenTo(gameController)

    override def receive: Receive = {
      case msg: String =>
        out ! Json.toJson(grid)
        println("Sent Json to Client: "+msg)
    }

    reactions += {
      case e: updateGridEvent=>sendJsonToClient()
      //case e: endGameEvent=>endGame()
      //case e: blockedColumnEvent=>blockedColumn()
      //case e: saveGameEvent=>saveGame()
      //case e: updateAllGridEvent =>updateAllGrid()
    }
    def sendJsonToClient():Unit = {
      println("Received event from Controller")
      out ! Json.toJson(grid).toString
    }
  }
  implicit val gridWrites: Writes[GridInterface] = new Writes[GridInterface] {
    def writes(grid: GridInterface): JsObject = Json.obj(
      "grid"->Json.obj(
        "cells"->Json.toJson( for{
          row <- 0 until 6
          col <- 0 until 7
        }yield{
          Json.obj(
            "row"->row,
            "col"->col,
            "val"->grid.grid(row)(col)
          )
        })
      )
    )
  }

}
