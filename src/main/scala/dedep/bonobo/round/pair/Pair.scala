package dedep.bonobo.round.pair

import dedep.bonobo._match.Match
import dedep.bonobo.round.RoundUnit
import dedep.bonobo.team.Team

case class Pair(p: (Team, Team)) extends RoundUnit {
  override val fixtures = List(List(new Match(p._1, p._2)), List(new Match(p._2, p._1)))

  override val teams = List(p._1, p._2)
}

