package turin.to_do_list.utils.DTO.specification;


import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import turin.to_do_list.model.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TodoSpecification {

    private static final Set<String> ALLOWED_SORT_ATTRIBUTES = Set.of("status", "title", "dueDate", "createAt", "user");

    public static Specification<Todo> getSpecification(String status, String sortBy, String order, Integer userId) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("status"), status));
            }

            if (userId != null) {
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }

            query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

            if (sortBy != null && !sortBy.isEmpty() && ALLOWED_SORT_ATTRIBUTES.contains(sortBy)){
                if (order != null && order.equalsIgnoreCase("desc")) {
                    query.orderBy(criteriaBuilder.desc(root.get(sortBy)));
                } else {
                    query.orderBy(criteriaBuilder.asc(root.get(sortBy)));
                }
            }
            return query.getRestriction();
        };
    }
}
