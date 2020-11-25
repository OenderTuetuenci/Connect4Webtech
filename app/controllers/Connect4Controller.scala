package controllers

import Connect4._
import controller.controllerComponent.ControllerInterface
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import model.gridComponent.GridInterface
import play.api.libs.json.{JsNumber, JsObject, JsValue, Json, Writes}


class Connect4Controller @Inject() (cc:ControllerComponents) extends AbstractController(cc){
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
  def gridToJson: Action[AnyContent] = Action {
    Ok(Json.toJson(grid))
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
