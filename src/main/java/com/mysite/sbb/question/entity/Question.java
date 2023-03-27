package com.mysite.sbb.question.entity;

import com.mysite.sbb.answer.entity.Answer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Setter
@Getter
@ToString
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime create_date;

    // OneToMany는 자바세상에서의 편의를 위해서 필드를 생성했다.
    // 이 녀석은 실제 DB칼럼이 생성되지 않는다.
    // DB는 배열이나 리스트를 저장할 수 없다.
        // 칼럼에 저장할 수 없다.
    // 만들어도 되고 안 만들어도 됩니다.
    // 다만 만들면 자바에서 해당 객체(질문객체)에서 관련된 답변들을 찾을 때 편하다.
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) //@OneToMany 애너테이션에 사용된 mappedBy는 참조 엔티티의 속성명을 의미
    // 질문이 삭제되면 달렸던 답변들도 다 삭제되게 함
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer a) {
        a.setQuestion(this);
        answerList.add(a);
    }
}
