package com.example.monolithfinancialsystem.persistence.specification;

import com.example.model.UserSearchParams;
import com.example.monolithfinancialsystem.persistence.model.EmailData;
import com.example.monolithfinancialsystem.persistence.model.PhoneData;
import com.example.monolithfinancialsystem.persistence.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@Validated
@Component
@RequiredArgsConstructor
public class UserJpaSpecificationServiceImpl implements UserJpaSpecificationService {

    @Override
    public Specification<User> buildSpecBy(UserSearchParams params) {
        return (root, query, cb) -> {
            query.distinct(true);
            Map<String, Join<?, ?>> reusableJoins = new ConcurrentHashMap<>();
            Predicate[] predicates = Stream.of(
                    Optional.ofNullable(params.getDateOfBirth()).map(date -> cb.greaterThanOrEqualTo(root.get(User.Fields.DATE_OF_BIRTH), date)),
                    Optional.ofNullable(params.getPhone()).map(phone -> buildPhonePredicate(root, cb, reusableJoins, phone)),
                    Optional.ofNullable(params.getEmail()).map(email -> buildEmailPredicate(root, cb, reusableJoins, email)),
                    Optional.ofNullable(params.getName()).map(name -> cb.like(cb.upper(root.get(User.Fields.NAME)), "%" + name.toUpperCase() + "%"))
            )
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toArray(Predicate[]::new);
            return cb.and(predicates);
        };
    }

    private Predicate buildPhonePredicate(Root<User> root, CriteriaBuilder cb, Map<String, Join<?, ?>> joins, String phone) {
        Join<?, ?> phoneDataJoin = joins.computeIfAbsent(User.Fields.PHONE_DATA, root::join);
        return cb.equal(phoneDataJoin.get(PhoneData.Fields.PHONE), phone);
    }

    private Object buildEmailPredicate(Root<User> root, CriteriaBuilder cb, Map<String, Join<?, ?>> joins, String email) {
        Join<?, ?> phoneDataJoin = joins.computeIfAbsent(User.Fields.EMAIL_DATA, root::join);
        return cb.equal(phoneDataJoin.get(EmailData.Fields.EMAIL), email);
    }
}
