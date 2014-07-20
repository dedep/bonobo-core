package dedep.bonobo

import dedep.bonobo._match.evaluator.NormalDistributionBasedMatchEvaluator
import _match.Match
import dedep.bonobo.round.result.FootballPointsGrantingStrategy
import dedep.bonobo.team.Team
import dedep.bonobo.round.promotion.PointsPromotionStrategy

object Common {
  type Pot = List[Team]
  type Group = List[Team]
  type Fixture = List[Match]

  implicit val pointsGrantingStrategy = FootballPointsGrantingStrategy
  implicit val matchEvaluator = NormalDistributionBasedMatchEvaluator
  implicit val promotionsStrategy = PointsPromotionStrategy
}
