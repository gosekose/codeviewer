package codeview.main.groupstorage.application;

import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.membergroup.domain.MemberGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupMembersService {

    private final GroupStorageService groupStorageService;

    public List<GroupStorage> findGroupMembers(MemberGroup memberGroup) {

        List<GroupStorage> findMemberGroup = groupStorageService.findByMemberGroupUsingDsl(memberGroup);

        if (findMemberGroup == null) {
            return null;
        }

        return findMemberGroup;

    }

}
