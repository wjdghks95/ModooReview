package wjdghks95.project.rol.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;

@Getter @Setter
@NoArgsConstructor
public class PageDto {
    private final int PAGENUM = 5; // 페이지 몇개로 구성할건지
    private int pageSize; // 페이지당 몇개 표시할건지
    private int startPage;
    private int endPage;
    private int curPage; // 현재 페이지
    private boolean prev, next;
    private long total;

    public PageDto(long total, Pageable pageable) {
        this.total = total;
        this.curPage = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();

        this.endPage = (int) (Math.ceil((curPage+1) / (double) PAGENUM)) * PAGENUM; // 일단 endPage를 5단위로 세팅, view는 1부터 시작이므로 curPage+1
        this.startPage = endPage - (PAGENUM - 1); // 5단위 endPage에서 4를 빼면 시작페이지 구할 수 있음

        int realEnd = (int) (Math.ceil((total * 1.0) / pageSize));

        if (realEnd < endPage) { // 페이지가 5단위로 나누어 떨어지지 않을때 real endPage
            this.endPage = realEnd == 0 ? 1 : realEnd;
        }

        this.prev = (curPage+1) > 1; // view에서는 1부터 시작이므로
        this.next = (curPage+1) < realEnd; // view에서는 1부터 시작이므로
    }
}
