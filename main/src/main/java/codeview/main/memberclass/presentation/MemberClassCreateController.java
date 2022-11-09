package codeview.main.memberclass.presentation;

import codeview.main.memberclass.application.MemberCreateService;
import codeview.main.memberclass.application.dto.CreateClassesForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/classes/new")
public class MemberClassCreateController {

    private final MemberCreateService memberCreateService;

    @GetMapping
    public String getCreateClasses(Model model) {

        model.addAttribute("createClassesForm", new CreateClassesForm());

        return "create-my-classes";
    }

    @PostMapping
    public String postCreateClasses(@RequestBody CreateClassesForm createClassesForm) {

        return "redirect:/ ";
    }


}
