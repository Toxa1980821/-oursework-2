package org.skypro.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skypro.exam.exception.QuestionNotFoundException;
import org.skypro.exam.model.Question;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JavaQuestionServiceTest {

    private JavaQuestionService questionService;

    @BeforeEach
    void setUp() {
        questionService = new JavaQuestionService();
    }

    @Test
    void getAllReturnsEmptyWhenNothingAdded() {
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void removeFromEmptyCollectionThrows() {
        assertThrows(QuestionNotFoundException.class,
                () -> questionService.remove(new Question("Вопрос", "Ответ")));
    }

    @Test
    void addStoresQuestion() {
        Question added = questionService.add("Вопрос", "Ответ");

        assertThat(questionService.getAll()).containsExactly(added);
    }

    @Test
    void addDuplicateIsIgnored() {
        questionService.add("Вопрос", "Ответ");
        questionService.add("Вопрос", "Ответ");

        assertThat(questionService.getAll()).hasSize(1);
    }

    @Test
    void removeDeletesExistingQuestion() {
        questionService.add("Вопрос", "Ответ");
        Question removed = questionService.remove(new Question("Вопрос", "Ответ"));

        assertThat(removed).isEqualTo(new Question("Вопрос", "Ответ"));
        assertThat(questionService.getAll()).isEmpty();
    }

    @Test
    void getRandomQuestionThrowsWhenCollectionIsEmpty() {
        assertThrows(QuestionNotFoundException.class,
                () -> questionService.getRandomQuestion());
    }

    @Test
    void getRandomQuestionReturnsOneOfAdded() {
        Question q1 = questionService.add("Вопрос 1", "Ответ 1");
        Question q2 = questionService.add("Вопрос 2", "Ответ 2");

        assertThat(questionService.getRandomQuestion()).isIn(q1, q2);
    }
}

