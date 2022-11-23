package codeview.main.groupstorage.presentation.util;

import codeview.main.groupstorage.infra.repository.GroupStorageQueryDslRepository;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupCondition;
import codeview.main.groupstorage.infra.repository.query.member.MembersOfGroupPageDto;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

@Getter
public class GroupStoragePage {


    public static Page<MembersOfGroupPageDto> getMembersOfGroupByPage(
            GroupStorageQueryDslRepository groupStorageQueryDslRepository,
            MembersOfGroupCondition condition,
            Pageable pageable,
            Model model) {

        Page<MembersOfGroupPageDto> membersOfGroupPageDtos = groupStorageQueryDslRepository.searchMemberOfGroupComplex(condition, pageable);

        int start = (int) (Math.floor(membersOfGroupPageDtos.getTotalElements() / 10) * 10 + 1);
        int last = start + 9 < membersOfGroupPageDtos.getTotalPages() ? start + 9 : membersOfGroupPageDtos.getTotalPages();

        model.addAttribute("members", membersOfGroupPageDtos);
        model.addAttribute("start", start);
        model.addAttribute("last", last);

        return membersOfGroupPageDtos;
    }


}
