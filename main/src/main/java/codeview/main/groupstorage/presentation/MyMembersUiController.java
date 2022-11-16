package codeview.main.groupstorage.presentation;

import codeview.main.groupstorage.application.GroupStorageService;
import codeview.main.groupstorage.domain.GroupStorage;
import codeview.main.groupstorage.presentation.dto.GroupForPageAboutMembersDto;
import codeview.main.membergroup.application.GroupService;
import codeview.main.membergroup.domain.MemberGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequestMapping("/api/v1/groups/admin/{groupId}/members")
@RequiredArgsConstructor
public class MyMembersUiController {

    private final GroupStorageService groupStorageService;
    private final GroupService groupService;

    @GetMapping
    public String getGroupMembersPage(
            @PathVariable("groupId") Integer groupId,
            Model model) {

        MemberGroup memberGroup = groupService.findById(Long.valueOf(groupId));

        List<GroupStorage> groupStorages = groupStorageService.findByMemberGroupUsingDsl(memberGroup);

        List<GroupForPageAboutMembersDto> dtos = groupStorages.stream()
                .map((g) -> new GroupForPageAboutMembersDto(g))
                .collect(Collectors.toList());

        model.addAttribute("members", dtos);

        return "groups/admins/my-group-members";
    }
}
