package dedep.bonobo.round.promotion

import dedep.bonobo.round.Round
import dedep.bonobo.team.Team

import scala.util.Random

object PointsPromotionStrategy extends PromotionsStrategy {
  override def arbitratePromotions(round: Round): List[Team] = {
    round.units
      .map(ru => Random.shuffle(ru.results))
      .map(_
        .sortBy(r => r.goalsConceded - r.goalsScored)
        .sortBy(-_.points)
      )
      .flatMap(_.take(round.getPromotedTeamsCount))
      .map(_.team)
  }
}