package dedep.bonobo._match

import dedep.bonobo.Common._
import dedep.bonobo._match.result.MatchResult
import dedep.bonobo.team.Team
import org.scalatest.FunSuite

class MatchTest extends FunSuite {
  test("to string format") {
    //given
    val m = Match(Team(100, 12), Team(80, 19))

    //when
    val format = m.toString

    //then
    assert(format == "12 vs 19")
  }

  test("eval generates result") {
    for (i <- 0 to 10) {
      //given
      val m = Match(Team(100, 12), Team(100, 19))

      //when
      val result = m.eval

      //then
      assert(result != null)
      assert(result.isInstanceOf[MatchResult])
      assert(result.aGoals >= 0)
      assert(result.bGoals >= 0)
    }
  }
}
