package codeview.main.memberclass.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/classes")
public class MemberClassMainController {


    @GetMapping
    public String index(Model model) {

        return "my-classes";
    }

}
