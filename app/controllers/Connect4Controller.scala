package controllers

import Connect4._
import controller.controllerComponent.ControllerInterface
import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}


class Connect4Controller @Inject() (cc:ControllerComponents) extends AbstractController(cc){
  val gameController:ControllerInterface = Connect4.controller

  def gridText: String = gameController.grid.toString

  def connect4: Action[AnyContent] = Action{
    Ok(gridText)
  }
  def put(column: Int): Action[AnyContent] = Action{
    gameController.move(column)
    Ok(gridText)
  }
  def about(): Action[AnyContent] = Action {
    Ok(views.html.index())
  }

}
