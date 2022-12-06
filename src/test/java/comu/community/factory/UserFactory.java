package comu.community.factory;

import comu.community.entity.user.Role;
import comu.community.entity.user.User;

public class UserFactory {

    public static User createUserWithAdminRole() {
        User user = new User("username", "password", Role.ROLE_ADMIN);
        user.setName("name1");
        user.setNickname("nickname1");
        return user;
    }

    public static User createUser() {
        User user = new User("username", "password", Role.ROLE_ADMIN);
        user.setName("name1");
        user.setNickname("nickname1");
        return user;
    }

    public static User createUser(String username, String password) {
        User user = new User(username, password, Role.ROLE_ADMIN);
        user.setName("name1");
        user.setNickname("nickname1");
        return user;
    }

    public static User createUser2() {
        User user = new User("username2", "password2", Role.ROLE_ADMIN);
        user.setName("name2");
        user.setNickname("nickname2");
        return user;
    }

}
