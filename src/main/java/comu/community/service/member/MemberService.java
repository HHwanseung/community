package comu.community.service.member;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.member.MemberEditRequestDto;
import comu.community.dto.member.MemberSimpleResponseDto;
import comu.community.entity.member.Member;

import java.util.List;

public interface MemberService {

    List<MemberSimpleResponseDto> findAllMembers();
    MemberSimpleResponseDto findMember(Long id);
    Member updateMemberInfo(Member member, MemberEditRequestDto updateInfo);
    void deleteMemberInfo(Member member);
    List<BoardSimpleDto> findFavorites(Member member);



}
