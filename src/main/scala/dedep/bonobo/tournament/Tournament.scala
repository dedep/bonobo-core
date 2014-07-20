package dedep.bonobo.tournament

import dedep.bonobo.round.Round
import dedep.bonobo.team.Team

trait Tournament {
  val teams: List[Team]
  val rounds: List[Round]

  def doStep(): Tournament

  def isFinished(): Boolean
}
