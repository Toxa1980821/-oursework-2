package org.skypro.exam.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.exam.exception.QuestionsAreNotEnoughException;
import org.skypro.exam.model.Question;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExaminerServiceImplTest {

    @Mock
    private QuestionService questionService;

    @InjectMocks
    private ExaminerServiceImpl examinerService;

    private final Question q1 = new Question("Вопрос 1", "Ответ 1");
    private final Question q2 = new Question("Вопрос 2", "Ответ 2");

    @Test
    void getQuestionsThrowsWhenAmountIsZero() {
        assertThrows(QuestionsAreNotEnoughException.class,
                () -> examinerService.getQuestions(0));
    }

    @Test
    void getQuestionsThrowsWhenAmountMoreThanAvailable() {
        when(questionService.getAll()).thenReturn(List.of(q1, q2));

        assertThrows(QuestionsAreNotEnoughException.class,
                () -> examinerService.getQuestions(3));
    }

    @Test
    void getQuestionsReturnsUniqueQuestions() {
        when(questionService.getAll()).thenReturn(List.of(q1, q2));
        when(questionService.getRandomQuestion()).thenReturn(q1, q1, q2, q2);

        Collection<Question> result = examinerService.getQuestions(2);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(q1, q2);
    }

    @Test
    void getQuestionsReturnsSingleQuestion() {
        when(questionService.getAll()).thenReturn(List.of(q1));
        when(questionService.getRandomQuestion()).thenReturn(q1);

        assertThat(examinerService.getQuestions(1)).containsExactly(q1);
    }
}

