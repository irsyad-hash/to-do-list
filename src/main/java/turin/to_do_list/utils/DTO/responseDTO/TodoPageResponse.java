package turin.to_do_list.utils.DTO.responseDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
public class TodoPageResponse<T> {
    private List<T> items;
    private Integer size;
    private Integer totalPages;
    private Integer page;

    public TodoPageResponse(Page<T> page) {
        this.items = page.getContent();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
    }
}