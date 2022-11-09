package codeview.main.memberclass.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.memberclass.application.MemberCreateService;
import codeview.main.memberclass.application.dto.CreateClassesForm;
import codeview.main.memberclass.domain.MemberClasses;
import codeview.main.memberclass.domain.MemberClassesVisibility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/classes/new")
public class MemberClassCreateController {

    private final MemberCreateService memberCreateService;
    private final MemberService memberService;

    @ModelAttribute("visibilities")
    public Map<String, MemberClassesVisibility> regions() {
        Map<String, MemberClassesVisibility> visibilities = new LinkedHashMap<>();
        visibilities.put("공개", MemberClassesVisibility.VISIBLE);
        visibilities.put("비공개", MemberClassesVisibility.HIDDEN);
        return visibilities;
    }



    @GetMapping
    public String getCreateClasses(Model model) {

        model.addAttribute("createClassesForm", new CreateClassesForm());

        return "create-my-classes";
    }


    @GetMapping("/{id}")
    public String getClassesById(Model model, @PathVariable String id) {

        model.addAttribute("createClassesForm", new CreateClassesForm());

        return "create-my-classes";
    }



    @PostMapping
    public String postCreateClasses(@AuthenticationPrincipal PrincipalUser principalUser,
                                    @ModelAttribute CreateClassesForm createClassesForm, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {

        if (!StringUtils.hasText(createClassesForm.getName())) {
            bindingResult.rejectValue("name", "required");
        }

        if (createClassesForm.getMaxMember() == null || 10 > createClassesForm.getMaxMember() || createClassesForm.getMaxMember() > 1000000) {
            bindingResult.rejectValue("maxMember","range", new Object[]{10, 1000000}, null);
        }

        if (createClassesForm.getMemberClassesVisibility() == null) {
            bindingResult.rejectValue("memberClassesVisibility", "required");
        }

        if (createClassesForm.getJoinClosedTime() != null) {
            if (createClassesForm.getJoinClosedTime().isBefore(LocalDateTime.now()))
                bindingResult.rejectValue("joinClosedTime", "min", new Object[] {DateTimeFormatter.ofPattern("yyyy-MM-dd hh:MM").format(LocalDateTime.now())}, null);
        }


        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "create-my-classes";
        }


        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        MemberClasses memberClasses = memberCreateService.createdClassesSave(member.getId(), createClassesForm);
        redirectAttributes.addAttribute("id", memberClasses.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/api/v1/classes/{id}";
    }


}
