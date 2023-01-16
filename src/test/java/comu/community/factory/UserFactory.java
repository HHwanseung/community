package comu.community.factory;

import comu.community.entity.member.Role;
import comu.community.entity.member.Member;

public class UserFactory {

    public static Member createUserWithAdminRole() {
        Member member = new Member("username", "password", Role.ROLE_ADMIN);
        member.setName("name1");
        member.setNickname("nickname1");
        return member;
    }

    public static Member createUser() {
        Member member = new Member("username", "password", Role.ROLE_ADMIN);
        member.setName("name1");
        member.setNickname("nickname1");
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

}
