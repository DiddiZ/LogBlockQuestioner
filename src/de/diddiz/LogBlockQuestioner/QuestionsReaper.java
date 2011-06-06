package de.diddiz.LogBlockQuestioner;

import java.util.List;

class QuestionsReaper implements Runnable
{
	private final List<Question> questions;

	public QuestionsReaper(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public void run() {
		for (final Question question : questions)
			if (question.isExpired())
				questions.remove(question);
	}
}
