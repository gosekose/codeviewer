package codeview.main.businessservice.membergroup.presentation.interceptor;

import codeview.main.businessservice.member.application.MemberService;
import codeview.main.businessservice.member.domain.Member;
import codeview.main.businessservice.membergroup.application.GroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class GroupAdminInterceptor implements HandlerInterceptor {

    private final static String MEMBER = "member";
    private final static String CREATOR = "creator";

    @Resource
    private GroupService groupService;

    @Resource
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Map<?, ?> pathVariables = (Map<?, ?>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("pathVariables = {}", pathVariables);

        String email = request.getUserPrincipal().getName();
        Long uriId = Long.valueOf(String.valueOf(pathVariables.get("groupId")));
        log.info("uriId = {}", uriId);

        try {
            Member member = memberService.findByEmail(email);
            Member creator = groupService.findById(uriId).getCreator();

            log.info("member.getId() = {}", member.getId());
            log.info("creator.getId() = {}", creator.getId());

            if (!Objects.equals(member.getId(), creator.getId())) {
                response.sendRedirect("/api/v1/groups/admin/errors?status=403");
                return false;
            }

        } catch (IllegalArgumentException e) {
            e.getMessage();
            response.sendRedirect("/api/v1/groups/admin/errors?status=404");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        log.info("model = {},  handler = {}", modelAndView, handler);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }



}