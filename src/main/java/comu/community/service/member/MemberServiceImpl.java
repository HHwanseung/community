package comu.community.service.member;

import comu.community.dto.board.BoardSimpleDto;
import comu.community.dto.member.MemberEditRequestDto;
import comu.community.dto.member.MemberSimpleResponseDto;
import comu.community.entity.board.Favorite;
import comu.community.entity.member.Member;
import comu.community.exception.MemberNotEqualsException;
import comu.community.repository.board.FavoriteRepository;
import comu.community.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public List<MemberSimpleResponseDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberSimpleResponseDto> result = members.stream().map(member -> MemberSimpleResponseDto.toDto(member)).collect(Collectors.toList());
        return result;
    }

    @Override
    public MemberSimpleResponseDto findMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(MemberNotEqualsException::new);
        return MemberSimpleResponseDto.toDto(member);
    }

    @Override
    public Member updateMemberInfo(Member member, MemberEditRequestDto memberEditRequestDto) {
        member.editUser(memberEditRequestDto);
        return member;
    }

    @Override
    public void deleteMemberInfo(Member member) {
        memberRepository.delete(member);
    }

    @Override
    public List<BoardSimpleDto> findFavorites(Member member) {
        List<Favorite> favorites = favoriteRepository.findAllByMember(member);
        List<BoardSimpleDto> boardSimpleDtoList = favorites.stream().map(favorite -> new BoardSimpleDto().toDto(favorite.getBoard()))
                .collect(Collectors.toList());
        return boardSimpleDtoList;
    }
}
