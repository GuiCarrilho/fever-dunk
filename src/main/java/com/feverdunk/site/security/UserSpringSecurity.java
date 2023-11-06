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
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> autoridades;

    public UserSpringSecurity(Long id, String email, String senha, Set<Perfil> perfis) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.autoridades = perfis.stream().map(p ->
                new SimpleGrantedAuthority(p.getDescricao())).collect(Collectors.toList());
    }

    public boolean hasHole(Perfil p){
        return getAuthorities().contains(new SimpleGrantedAuthority(p.getDescricao()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return autoridades;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
