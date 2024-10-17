import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


class DatabaseHelper {

    fun getConnection(): Connection? {
        val url = "jdbc:mysql://localhost:3306/riddle" // Replace with your actual connection details
        val username = "your_username" // Replace with your database username
        val password = "your_password" // Replace with your database password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance()
            return DriverManager.getConnection(url, username, password)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getRiddle(riddleId: Int): Riddle? {
        val connection = getConnection() ?: return null

        try {
            val statement = connection.createStatement()
            val query = "SELECT * FROM questions WHERE riddle_id = $riddleId"
            val resultSet = statement.executeQuery(query)

            if (resultSet.next()) {
                val riddleId = resultSet.getInt("riddle_id")
                val questionText = resultSet.getString("riddle_question")
                val choices = parseChoices(resultSet.getString("riddle_choices")) // Parse choices
                val answer = resultSet.getString("riddle_answer")
                val points = resultSet.getInt("riddle_points")
                val difficulty = resultSet.getInt("riddle_difficulty")

                return Riddle(riddleId, questionText, choices, answer, points, difficulty)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return null
    }

    fun getAllRiddles(): List<Riddle> {
        val riddles = mutableListOf<Riddle>()
        val connection = getConnection() ?: return riddles // Return empty list if connection fails

        try {
            val statement = connection.createStatement()
            val query = "SELECT * FROM questions"
            val resultSet = statement.executeQuery(query)

            while (resultSet.next()) {
                val riddleId = resultSet.getInt("riddle_id")
                val questionText = resultSet.getString("riddle_question")
                val choices = parseChoices(resultSet.getString("riddle_choices")) // Parse choices
                val answer = resultSet.getString("riddle_answer")
                val points = resultSet.getInt("riddle_points")
                val difficulty = resultSet.getInt("riddle_difficulty")

                riddles.add(Riddle(riddleId, questionText, choices, answer, points, difficulty))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            connection.close()
        }

        return riddles
    }

    private fun parseChoices(choicesString: String): List<String> {
        // Extract choices from the JSON string in the database
        if (choicesString.isEmpty()) {
            return emptyList()
        }

        // Assuming your choices are stored in a JSON array
        //help me pl
        val jsonArray = JsonParser.parseString(choicesString).asJsonArray
        val choices = jsonArray.map { it.asString } // Convert each element to a string

        return choices
    }
}



data class Riddle(
    val id: Int,
    val question: String,
    val choices: List<String>,
    val answer: String,
    val points: Int,
    val difficulty: Int
)