package dedep.bonobo._match.result

case class Draw(goals: Int) extends MatchResult {
   override val bGoals: Int = goals
   override val aGoals: Int = goals
 }
