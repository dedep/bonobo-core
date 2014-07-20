package dedep.bonobo.team

case class Team(value: Int, points: Int) {
  //todo: przy rozbudowywaniu klasy zaimplementować
  override def toString: String = points.toString
}
