package nhn.academy.auth;

import nhn.academy.model.Member;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class AcademyUser implements UserDetails {
    private String memberName;
    private String role;
    private String password;
    public AcademyUser(Member member) {
        this.memberName = member.getId();
        this.role = member.getRole().name();
        this.password = member.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.role;
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return memberName;
    }
}
