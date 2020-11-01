package controllers

import Connect4._
import controller.controllerComponent.ControllerInterface
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import model.gridComponent.GridInterface


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

}
