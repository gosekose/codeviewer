package codeview.main.groupstorage.presentation;

import codeview.main.groupstorage.infra.repository.GroupStorageQueryDslRepositoryImpl;
import codeview.main.groupstorage.infra.repository.query.MembersOfGroupCondition;
import codeview.main.groupstorage.presentation.dto.MembersOfGroupPageDto;
import codeview.main.groupstorage.presentation.util.GroupStoragePage;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members")
@RequiredArgsConstructor
public class MembersOfGroupUiController {

    private final GroupStorageQueryDslRepositoryImpl groupStorageQueryDslRepository;
    private final GroupService groupService;

    @GetMapping
    public String getGroupMembersPage(
            @PathVariable("groupId") Integer groupId,
            @PageableDefault Pageable pageable,
            MembersOfGroupCondition condition,
            Model model) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));
        condition.setMemberGroup(memberGroup);

        Page<MembersOfGroupPageDto> membersOfGroupByPage = GroupStoragePage.getMembersOfGroupByPage(groupStorageQueryDslRepository, condition, pageable, model);

        model.addAttribute("groupId", memberGroup.getId());

        log.info("size = {}", membersOfGroupByPage.getContent().size());
        log.info("members.name = {}", membersOfGroupByPage.getContent().get(0).getName());

        return "groups/admins/my-group-members";

    }
}
