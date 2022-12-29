package comu.community.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import comu.community.dto.user.UserEditRequestDto;
import comu.community.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private boolean reported;

    @Builder
    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.reported = false;
        this.role = role;
    }

    public boolean isReported() {
        return this.reported;
    }

    public void editUser(UserEditRequestDto req) {
        name = req.getName();
        nickname = req.getNickname();
    }

    public void unlockReport() {
        this.reported = false;
    }

    public boolean isReportMySelf(Long id) {
        return this.id == id;
    }

    public void setStatusIsBeingReported() {
        this.reported = true;
    }

}
