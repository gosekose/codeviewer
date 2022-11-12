package codeview.main.membergroup.presentation;

import codeview.main.auth.domain.users.PrincipalUser;
import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.MemberGroupCreateService;
import codeview.main.membergroup.domain.MemberGroup;
import codeview.main.membergroup.domain.MemberGroupVisibility;
import codeview.main.membergroup.presentation.dto.CreateGroupForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/api/v1/groups/new")
public class MemberGroupCreateController {

    private final MemberGroupCreateService memberGroupCreateService;
    private final MemberService memberService;

    @ModelAttribute("visibilities")
    public Map<String, MemberGroupVisibility> regions() {
        Map<String, MemberGroupVisibility> visibilities = new LinkedHashMap<>();
        visibilities.put("공개", MemberGroupVisibility.VISIBLE);
        visibilities.put("비공개", MemberGroupVisibility.HIDDEN);
        return visibilities;
    }



    @GetMapping
    public String getCreateGroups(Model model) {

        model.addAttribute("createGroupForm", new CreateGroupForm());
        return "groups/create-my-group";
    }


    @GetMapping("/{id}")
    public String getGroupsById(Model model, @PathVariable String id) {

        model.addAttribute("createGroupForm", new CreateGroupForm());
        return "groups/create-my-group";
    }

    @PostMapping
    public String postCreateGroups(@AuthenticationPrincipal PrincipalUser principalUser,
                                   @Validated @ModelAttribute CreateGroupForm createGroupForm, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

         if (bindingResult.hasErrors()) {
            log.info("errors = {}", bindingResult);
            return "groups/create-my-group";
        }

        Member member = memberService.findByRegisterId(principalUser.getProviderUser().getId());

        MemberGroup memberGroup = memberGroupCreateService.createdGroupSave(member.getId(), createGroupForm);
        redirectAttributes.addAttribute("id", memberGroup.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/api/v1/groups/admin/{id}";
    }





}
