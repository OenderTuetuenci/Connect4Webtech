package controllers

import Connect4._
import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}


class Connect4Controller @Inject() (cc:ControllerComponents) extends AbstractController(cc){
  val gameController = Connect4.controller

  def connect4Test = gameController.grid.toString

  def connect4 = Action{
    Ok(connect4Test)
  }

}
