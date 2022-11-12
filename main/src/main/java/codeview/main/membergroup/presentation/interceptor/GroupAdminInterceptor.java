package codeview.main.membergroup.presentation.interceptor;

import codeview.main.member.application.MemberService;
import codeview.main.member.domain.Member;
import codeview.main.membergroup.application.MemberGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class GroupAdminInterceptor implements HandlerInterceptor {

    private final static String MEMBER = "member";
    private final static String CREATOR = "creator";

    @Resource
    private MemberGroupService memberGroupService;

    @Resource
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String email = request.getUserPrincipal().getName();
        String[] uri = request.getRequestURI().split("/");
        Long uriId = Long.valueOf(Integer.parseInt(uri[uri.length - 1]));

        try {
            Member member = memberService.findByEmail(email);
            Member creator = memberGroupService.findById(uriId).getCreator();

            if (member.getId() != creator.getId()) {
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
        log.info("model = {},  handler = {}", modelAndView, handler);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }



}