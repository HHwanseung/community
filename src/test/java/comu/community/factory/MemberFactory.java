package comu.community.factory;

import comu.community.entity.member.Role;
import comu.community.entity.member.Member;

public class MemberFactory {

    public static Member createUserWithAdminRole() {
        Member member = new Member("username", "password", Role.ROLE_USER);
        member.setName("name1");
        member.setNickname("nickname1");
        return member;
    }

    public static Member createMember() {
        Member member = Member.builder()
                .username("username1")
                .name("name1")
                .password("1234")
                .role(Role.ROLE_USER)
                .build();
        return member;
    }

    public static Member createUser(String username, String password) {
        Member member = new Member(username, password, Role.ROLE_ADMIN);
        member.setName("name1");
        member.setNickname("nickname1");
        return member;
    }

    public static Member createUser2() {
        Member member = new Member("username2", "password2", Role.ROLE_ADMIN);
        member.setName("name2");
        member.setNickname("nickname2");
        return member;
    }

    public static Member createAdmin() {
        Member member = new Member("admin1","admin1",Role.ROLE_ADMIN);
        member.setName("admin1");
        member.setNickname("admin1");
        return member;
    }


}
