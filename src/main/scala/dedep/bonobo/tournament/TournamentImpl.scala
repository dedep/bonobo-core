package dedep.bonobo.tournament

import dedep.bonobo.Common._
import dedep.bonobo.round.Round
import dedep.bonobo.round.group.GroupRound
import dedep.bonobo.round.pair.PlayoffRound
import dedep.bonobo.team.Team
import dedep.bonobo.utils.MathUtils

case class TournamentImpl(override val teams: List[Team], override val rounds: List[Round] = Nil) extends Tournament {
  require(teams.size >= 2)

  def doStep(): Tournament = {
    val lastRound: Option[Round] = rounds.headOption

    if (lastRound.isEmpty) createFirstRound()
    else if (lastRound.get.isFinished()) {
      lastRound.get match {
        case round: PlayoffRound =>
          if (round.preliminary)
            createNextRound(teams.diff(lastRound.get.teams) ++ lastRound.get.getPromotedTeams)
          else
            createNextRound(lastRound.get.getPromotedTeams)
        case _ => createNextRound(lastRound.get.getPromotedTeams)
      }
    }
    else TournamentImpl(teams, rounds.updated(rounds.indexOf(lastRound), lastRound.get.doStep()))
  }

  private def createFirstRound(): Tournament =
    if (isPreliminaryRoundRequired)
      TournamentImpl(teams, PlayoffRound(getPreliminaryRoundTeams, preliminary = true) :: rounds)
    else
      createNextRound(teams)

  private def createNextRound(teams: List[Team]): Tournament =
    if (teams.length >= 32)
      TournamentImpl(teams, GroupRound(teams) :: rounds)
    else
      TournamentImpl(teams, new PlayoffRound(teams) :: rounds)

  private def isPreliminaryRoundRequired: Boolean = !MathUtils.isPowerOfTwo(teams.size)

  private def getPreliminaryRoundTeams: List[Team] = teams.sortBy(_.points).take(getPreliminaryRoundTeamsNumber)

  private def getPreliminaryRoundTeamsNumber: Int = (teams.length - MathUtils.getFloorPowerOfTwoNumber(teams.length)) * 2

  override def isFinished(): Boolean = rounds.headOption match {
    case None => false
    case Some(r: Round) => r.isFinished() && r.isFinalRound()
  }
}
