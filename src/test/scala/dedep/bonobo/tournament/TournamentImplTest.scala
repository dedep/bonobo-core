package dedep.bonobo.tournament

import dedep.bonobo.round.group.GroupRound
import dedep.bonobo.round.pair.PlayoffRound
import dedep.bonobo.team.Team
import org.scalatest.FunSuite

class TournamentImplTest extends FunSuite {

  test("should create preliminary playoff round") {
    //given
    val t1 = Team(1, 1)
    val t2 = Team(2, 2)
    val t3 = Team(3, 3)
    val t4 = Team(4, 4)
    val t5 = Team(5, 5)
    val t6 = Team(6, 6)
    val t7 = Team(7, 7)
    val t8 = Team(8, 8)
    val t9 = Team(9, 9)
    val t10 = Team(10, 0)

    val t = TournamentImpl(List(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10))

    //when
    val tournament = t.doStep()

    //then
    assert(tournament.rounds.size == 1)
    assert(!tournament.isFinished())
    assert(tournament.rounds(0).isInstanceOf[PlayoffRound])
    assert(tournament.rounds(0).teams.size == 4)
    assert(tournament.rounds(0).teams.count(t => t == t10 || t == t1 || t == t2 || t == t3) == 4)
  }

  test("should not create preliminary round - create group round instead") {
    //given
    val t1 = Team(1, 1)
    val t2 = Team(2, 2)
    val t3 = Team(3, 3)
    val t4 = Team(4, 4)
    val t5 = Team(5, 5)
    val t6 = Team(6, 6)
    val t7 = Team(7, 7)
    val t8 = Team(8, 8)
    val t9 = Team(1, 1)
    val t10 = Team(2, 2)
    val t11 = Team(3, 3)
    val t12 = Team(4, 4)
    val t13 = Team(5, 5)
    val t14 = Team(6, 6)
    val t15 = Team(7, 7)
    val t16 = Team(8, 8)
    val t17 = Team(1, 1)
    val t18 = Team(2, 2)
    val t19 = Team(3, 3)
    val t20 = Team(4, 4)
    val t21 = Team(5, 5)
    val t22 = Team(6, 6)
    val t23 = Team(7, 7)
    val t24 = Team(8, 8)
    val t25 = Team(1, 1)
    val t26 = Team(2, 2)
    val t27 = Team(3, 3)
    val t28 = Team(4, 4)
    val t29 = Team(5, 5)
    val t30 = Team(6, 6)
    val t31 = Team(7, 7)
    val t32 = Team(8, 8)

    val t = TournamentImpl(List(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19,
      t20, t21, t22, t23, t24, t25, t26, t27, t28, t29, t30, t31, t32))

    //when
    val tournament = t.doStep()

    //then
    assert(tournament.rounds.size == 1)
    assert(tournament.rounds(0).isInstanceOf[GroupRound])
    assert(tournament.rounds(0).teams.size == 32)
  }

  test("should not create preliminary round - create playoff round instead") {
    //given
    val t1 = Team(1, 1)
    val t2 = Team(2, 2)
    val t3 = Team(3, 3)
    val t4 = Team(4, 4)
    val t5 = Team(5, 5)
    val t6 = Team(6, 6)
    val t7 = Team(7, 7)
    val t8 = Team(8, 8)

    val t = TournamentImpl(List(t1, t2, t3, t4, t5, t6, t7, t8))

    //when
    val tournament = t.doStep()

    //then
    assert(tournament.rounds.size == 1)
    assert(!tournament.isFinished())
    assert(tournament.rounds(0).isInstanceOf[PlayoffRound])
    assert(tournament.rounds(0).teams.size == 8)
  }

  test("should process tournament") {
    //given
    val t1 = Team(1, 1)
    val t2 = Team(2, 2)
    val t3 = Team(3, 3)
    val t4 = Team(4, 4)
    val t5 = Team(5, 5)
    val t6 = Team(6, 6)
    val t7 = Team(7, 7)
    val t8 = Team(8, 8)
    val t9 = Team(9, 0)

    val t = TournamentImpl(List(t1, t2, t3, t4, t5, t6, t7, t8, t9))

    val tournament = t.doStep()

    assert(tournament.rounds.size == 1)
    assert(!tournament.isFinished())
    assert(tournament.rounds(0).isInstanceOf[PlayoffRound])
    assert(tournament.rounds(0).teams.size == 2)
    assert(!tournament.rounds(0).isFinished())

    val tournament1 = tournament.doStep().doStep().doStep().doStep()

    assert(tournament1.rounds.size == 1)
    assert(!tournament1.isFinished())
    assert(tournament1.rounds(0).isFinished())

    val tournament2 = tournament1.doStep()

    assert(tournament2.teams.size == 8)
    assert(!tournament2.isFinished())
    assert(tournament2.rounds.size == 2)
    assert(!tournament2.rounds(0).isFinished())

    val tournament3 = tournament2.doStep().doStep().doStep().doStep()

    assert(!tournament3.isFinished())
    assert(tournament3.rounds.size == 2)
    assert(tournament3.rounds(0).isFinished())

    val tournament4 = tournament3.doStep()

    assert(tournament4.teams.size == 4)
    assert(!tournament4.isFinished())
    assert(tournament4.rounds.size == 3)
    assert(!tournament4.rounds(0).isFinished())

    val tournament5 = tournament4.doStep().doStep().doStep().doStep()

    assert(!tournament5.isFinished())
    assert(tournament5.rounds.size == 3)
    assert(tournament5.rounds(0).isFinished())

    val tournament6 = tournament5.doStep()

    assert(tournament6.teams.size == 2)
    assert(!tournament6.isFinished())
    assert(tournament6.rounds.size == 4)
    assert(!tournament6.rounds(0).isFinished())
    assert(tournament6.rounds(0).isFinalRound())

    val tournament7 = tournament6.doStep().doStep().doStep().doStep()

    assert(tournament7.isFinished())
    assert(tournament7.rounds.size == 4)
    assert(tournament7.rounds.forall(_.isFinished()))
  }
}
