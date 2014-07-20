package dedep.bonobo._match

import dedep.bonobo._match.evaluator.MatchEvaluator
import dedep.bonobo._match.result.MatchResult
import dedep.bonobo.team.Team

case class PlayedMatch(override val aTeam: Team, override val bTeam: Team, result: MatchResult)
  extends Match(aTeam, bTeam) {

  override def eval(implicit matchEvaluator: MatchEvaluator): MatchResult =
    throw new IllegalStateException("Cannot eval played match")
}