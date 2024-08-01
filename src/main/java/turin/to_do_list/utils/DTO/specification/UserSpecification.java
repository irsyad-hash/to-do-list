package turin.to_do_list.utils.DTO.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import turin.to_do_list.model.Todo;
import turin.to_do_list.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {
    public static Specification<User> getSpecification(String name) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

