package dedep.bonobo._match

import com.typesafe.scalalogging.slf4j.Logger
import dedep.bonobo._match.evaluator.MatchEvaluator
import dedep.bonobo._match.result.MatchResult
import dedep.bonobo.team.Team
import org.slf4j.LoggerFactory

class Match(val aTeam: Team, val bTeam: Team) {

  val log = Logger(LoggerFactory.getLogger(this.getClass))

  def eval(implicit matchEvaluator: MatchEvaluator): MatchResult = matchEvaluator.eval(this)

  override def toString = aTeam.toString + " vs " + bTeam.toString
}

object Match {
  def apply(aTeam: Team, bTeam: Team) = new Match(aTeam, bTeam)
}