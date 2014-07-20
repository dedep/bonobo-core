package dedep.bonobo._match.evaluator

import dedep.bonobo._match.Match
import dedep.bonobo._match.result.MatchResult

trait MatchEvaluator {
  def eval(m: Match): MatchResult
}
