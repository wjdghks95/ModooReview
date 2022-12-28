package wjdghks95.project.rol.domain.entity;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String originFileName;

    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Image(String originFileName, String storeFileName) {
        this.originFileName = originFileName;
        this.storeFileName = storeFileName;
    }
}
