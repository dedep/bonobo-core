package dedep.bonobo._match.evaluator

import com.typesafe.scalalogging.slf4j.Logger
import dedep.bonobo._match.Match
import dedep.bonobo._match.result.{Draw, MatchResult, WinA, WinB}
import org.slf4j.LoggerFactory

import scala.util.Random

object NormalDistributionBasedMatchEvaluator extends MatchEvaluator {

  val log = Logger(LoggerFactory.getLogger(NormalDistributionBasedMatchEvaluator.getClass))

  override def eval(m: Match): MatchResult = {
    require(m.bTeam.value > 0 && m.aTeam.value > 0)
    log.info("Evaluating result of the following match: {}", m)

    val bp = calcBalancePoint(m)
    log.debug("Balance point for match: {} equals: {}", m, bp.underlying())

    val mp = drawMatchPoint(bp)
    log.debug("Match point for match: {} equals: {}", m, mp.underlying())

    val result = createResultForMatchPoint(mp)
    log.info("Match: {} result has been evaluated: {}", m, result)

    result
  }

  def calcBalancePoint(m: Match): Double =
    log2(m.aTeam.value.toDouble / m.bTeam.value.toDouble) / 2

  private def log2(x: Double) = Math.log(x) / Math.log(2)

  def drawMatchPoint(balancePoint: Double): Double = Random.nextGaussian() + balancePoint

  def calcGoalsDiff(matchPoint: Double): Int =
    if (matchPoint >= 0) Math.floor(3 * Math.pow(matchPoint, 1)).toInt
    else -Math.floor(3 * Math.pow(-matchPoint, 1)).toInt

  def drawGoalsConstantComponent: Int = Math.round(Math.abs(Random.nextGaussian())).toInt

  def createResultForMatchPoint(matchPoint: Double) = {
    val c = drawGoalsConstantComponent
    log.debug("Goals constant equals: {}", c.underlying())

    val d = calcGoalsDiff(matchPoint)
    log.debug("Goals difference equals: {}", d.underlying())

    if (d > 0) WinA(d + c, c)
    else if (d < 0) WinB(c, c - d)
    else Draw(c)
  }
}