package codeview.main.common.presentation.page;

import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

@Getter
public class PageUtils<T>{

    public static <T> void modelPagingAndModel(Page<T> dtos, Model model, String dtosName) {

        int start = (int) (Math.floor(dtos.getTotalElements() / 10) * 10 + 1);
        int last = start + 9 < dtos.getTotalPages() ? start + 9 : dtos.getTotalPages();

        model.addAttribute(dtosName, dtos);
        model.addAttribute("start", start);
        model.addAttribute("last", last);
    }
}
