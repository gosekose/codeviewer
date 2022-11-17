package codeview.main.membergroup.presentation.util;

import codeview.main.membergroup.presentation.dto.GroupForPageDto;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

@Getter
public class MemberGroupsPageUtil {

    public static void modelPagingAndModel(Page<GroupForPageDto> groupForPageDtos, Model model) {

        int start = (int) (Math.floor(groupForPageDtos.getTotalElements() / 10) * 10 + 1);
        int last = start + 9 < groupForPageDtos.getTotalPages() ? start + 9 : groupForPageDtos.getTotalPages();

        model.addAttribute("memberGroups", groupForPageDtos);
        model.addAttribute("start", start);
        model.addAttribute("last", last);
    }

}
