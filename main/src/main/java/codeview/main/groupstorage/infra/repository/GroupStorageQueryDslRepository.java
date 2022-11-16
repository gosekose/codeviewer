package codeview.main.groupstorage.infra.repository;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.domain.QGroupStorage;
import codeview.main.groupstorage.presentation.dto.GroupStorageByGroupCondition;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.QMemberGroup;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static codeview.main.groupstorage.domain.QGroupStorage.groupStorage;

@Repository
@RequiredArgsConstructor
public class GroupStorageQueryDslRepository {

    private final EntityManager em;

    public List<GroupStorage> findAllByMemberGroup(GroupStorageByGroupCondition condition) {

        QGroupStorage groupStorage = QGroupStorage.groupStorage;
        QMemberGroup memberGroup = QMemberGroup.memberGroup;

        JPAQueryFactory query = new JPAQueryFactory(em);

        return query.selectFrom(groupStorage)
                .where(memberGroupEq(condition.getMemberGroup()))
                .fetch();

    }

    private BooleanExpression memberGroupEq(MemberGroup memberGroup) {
        return memberGroup != null ? groupStorage.memberGroup.eq(memberGroup) : null;
    }


}
