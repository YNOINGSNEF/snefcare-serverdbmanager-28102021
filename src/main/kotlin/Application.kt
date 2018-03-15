import anfr.AnfrDatabase
import java.util.concurrent.TimeUnit

private val databases = listOf<Database>(
//        ComsisDatabase
        AnfrDatabase
)

fun main(args: Array<String>) {
    println("--> Initialising tasks")
    val startTimeMillis = System.currentTimeMillis()

    databases.forEach { db ->
        println("--> Starting update of ${db::class.java.simpleName}")
        if (db.update()) {
            println("--> Finished ${db::class.java.simpleName} update")
        } else {
            println("--> Ignored ${db::class.java.simpleName} update, no new dump available")
        }
    }

    val diff = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeMillis)
    println("--> Completed all tasks in $diff seconds")
}