package turin.to_do_list.utils.DTO.responseDTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class UserPageResponse<T> {
    private List<T> users;
    private Integer size;
    private Integer totalPages;
    private Integer page;

    public UserPageResponse(Page<T> page) {
        this.users = page.getContent();
        this.size = page.getSize();
        this.totalPages = page.getTotalPages();
        this.page = page.getNumber();
    }
}
