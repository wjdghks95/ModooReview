package wjdghks95.project.rol.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import wjdghks95.project.rol.domain.BaseTimeEntity;

import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor
public class CommentDto extends BaseTimeEntity {

    @NotBlank(message = "댓글을 입력해주세요.")
    private String content;
}
