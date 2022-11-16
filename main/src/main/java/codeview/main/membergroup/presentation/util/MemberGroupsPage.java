package codeview.main.membergroup.presentation.util;

import codeview.main.membergroup.infra.repository.MemberGroupQueryDslRepository;
import codeview.main.membergroup.presentation.dao.MemberGroupSearchCondition;
import codeview.main.membergroup.presentation.dto.GroupForPageDto;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

@Getter
public class MemberGroupsPage {

    public static Page<GroupForPageDto> getMemberGroupsPage(MemberGroupQueryDslRepository memberGroupQueryDslRepository,
                                                            MemberGroupSearchCondition condition,
                                                            Pageable pageable,
                                                            Model model) {

        Page<GroupForPageDto> groupForPageDtos = memberGroupQueryDslRepository.searchPageComplex(condition, pageable);

        int start = (int) (Math.floor(groupForPageDtos.getTotalElements() / 10) * 10 + 1);
        int last = start + 9 < groupForPageDtos.getTotalPages() ? start + 9 : groupForPageDtos.getTotalPages();

        model.addAttribute("memberGroups", groupForPageDtos);
        model.addAttribute("start", start);
        model.addAttribute("last", last);

        return groupForPageDtos;
    }

}
