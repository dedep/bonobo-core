package dedep.bonobo.round.promotion

import dedep.bonobo.round.Round
import dedep.bonobo.team.Team

trait PromotionsStrategy {
  def arbitratePromotions(unit: Round): List[Team]
}
