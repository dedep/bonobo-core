package dedep.bonobo.round

import dedep.bonobo.Common._
import dedep.bonobo.round.result.TeamResult
import dedep.bonobo.team.Team
import dedep.bonobo._match.{PlayedMatch, Match}

import scala.annotation.tailrec

trait RoundUnit {
  val fixtures: List[Fixture]
  lazy val results: List[TeamResult] = teams.map(TeamResult(_))

  val teams: List[Team]
  lazy val fixturesCount: Int = fixtures.size

  def playFixture(fixtureNum: Int): RoundUnit = {
    require(fixtureNum < fixturesCount)

    val updatedFixture = fixtures(fixtureNum).map(m => PlayedMatch(m.aTeam, m.bTeam, m.eval))
    val updatedFixtures = fixtures.updated(fixtureNum, updatedFixture)

    val updatedResults = updatedFixtureResults(updatedFixture)

    val parentTeams = teams

    //todo: przemśleć to jeszcze
    new RoundUnit {
      override val teams: List[Team] = parentTeams
      override val fixtures: List[Fixture] = updatedFixtures
      override lazy val results: List[TeamResult] = updatedResults
    }
  }

  def updatedFixtureResults(fixture: Fixture): List[TeamResult] = {
    @tailrec
    def accumulateFixtureResults(remainingMatches: List[Match], acc: List[TeamResult]): List[TeamResult] = remainingMatches match {
      case Nil => acc
      case (h: PlayedMatch) :: t =>
        val aTeamResultIndex = acc.indexWhere(_.team == h.aTeam)
        val aTeamResults = acc(aTeamResultIndex)
        val bTeamResultIndex = acc.indexWhere(_.team == h.bTeam)
        val bTeamResults = acc(bTeamResultIndex)

        accumulateFixtureResults(t, acc.updated(aTeamResultIndex, aTeamResults aPlus h.result)
           .updated(bTeamResultIndex, bTeamResults bPlus h.result))
      case _ => throw new IllegalStateException("Cannot accumulate fixture results for unplayed match")
    }

    accumulateFixtureResults(fixture, results.toList)
  }
}