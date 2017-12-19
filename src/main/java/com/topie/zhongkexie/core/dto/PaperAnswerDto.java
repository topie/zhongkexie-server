package com.topie.zhongkexie.core.dto;

import java.util.List;

public class PaperAnswerDto {

    Integer paperId;

    List<AnswerDto> answers;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }
}
