package com.feverdunk.site.security;

import com.feverdunk.site.models.Perfil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
public class UserSpringSecurity implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> autoridades;

    public UserSpringSecurity(Long id, String username, String password, Set<Perfil> perfis) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.autoridades = perfis.stream().map((p) -> {
            return new SimpleGrantedAuthority(p.getDescricao());
        }).collect(Collectors.toList());
    }

    public boolean hasHole(Perfil p) {
        return this.getAuthorities().contains(new SimpleGrantedAuthority(p.getDescricao()));
    }

    public Long getId() {
        return this.id;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.autoridades;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public Collection<? extends GrantedAuthority> getAutoridades() {
        return this.autoridades;
    }
}
