package dedep.bonobo.round

import dedep.bonobo.Common._
import dedep.bonobo.round.promotion.PromotionsStrategy
import dedep.bonobo.team.Team

trait Round {
  val teams: List[Team]
  val pots: List[Pot]
  val units: List[RoundUnit]
  val stepIndex: Int

  def isFinalRound(): Boolean

  def isFinished(): Boolean

  def getPromotedTeamsCount: Int

  def getPromotedTeams(implicit strategy: PromotionsStrategy): List[Team] =
    if (!isFinished()) throw new IllegalStateException("Round has not been finished yet")
    else strategy.arbitratePromotions(this)

  def doStep(): Round = {
    if (isFinished()) throw new IllegalStateException("Trying to do step in finished round")

    if (pots.isEmpty) drawPots()
    else if (units.isEmpty) drawUnits()
    else playFixture()
  }

  def drawUnits(): Round

  def drawPots(): Round

  def playFixture(): Round
}
