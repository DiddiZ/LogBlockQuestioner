package de.diddiz.LogBlockQuestioner;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class LogBlockQuestioner extends JavaPlugin
{
	private final List<Question> questions = new ArrayList<Question>();

	public String askQuestion(Player respondent, String questionMessage, String... answers) throws QuestionerException {
		try {
			final Question question = new Question(respondent, questionMessage, answers);
			questions.add(question);
			return question.ask();
		} catch (final IllegalArgumentException ex) {
			throw new QuestionerException("Illegal Argument");
		} catch (final InterruptedException ex) {
			throw new QuestionerException("Interrupted");
		}
	}

	@Override
	public void onDisable() {
		getServer().getLogger().info("LogBlockQuestioner disabled");
	}

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvent(Type.PLAYER_COMMAND_PREPROCESS, new LogBlockQuestionerPlayerListener(questions), Priority.Normal, this);
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new QuestionsReaper(questions), 15000, 15000);
		getServer().getLogger().info("LogBlockQuestioner v" + getDescription().getVersion() + " enabled");
	}
}
