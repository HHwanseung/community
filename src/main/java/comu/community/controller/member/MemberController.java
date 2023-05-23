package comu.community.controller.member;

import comu.community.dto.member.MemberEditRequestDto;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotFoundException;
import comu.community.repository.member.MemberRepository;
import comu.community.response.Response;
import comu.community.service.member.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Api(value = "Member Controller", tags = "Member")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @ApiOperation(value = "전체 회원 조회", notes = "전체 회원을 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members")
    public Response findAllMembers() {
        return Response.success(memberService.findAllMembers());
    }

    @ApiOperation(value = "개인 회원 조회", notes = "개인 회원을 조회")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/members/{id}")
    public Response findMember(@ApiParam(value = "User ID", readOnly = true) @PathVariable Long id) {
        return Response.success(memberService.findMember(id));
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원의 정보를 수정")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping ("/members")
    public Response updateMemberInfo(@RequestBody MemberEditRequestDto memberEditRequestDto) {
        Member member =  getPrincipal();
        return Response.success(memberService.updateMemberInfo(member, memberEditRequestDto));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "회원을 탈퇴 시킴")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/members")
    public Response deleteMemberInfo() {
        Member member = getPrincipal();
        memberService.deleteMemberInfo(member);
        return Response.success();
    }

    public Member getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = memberRepository.findByUsername(authentication.getName())
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

}
