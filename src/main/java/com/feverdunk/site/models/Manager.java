package com.feverdunk.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Set;
import java.util.stream.Collectors;

@Document(collection = "managers")
@Data
public class Manager {

    @Id
    private String id;

    @Field("manager_nome")
    @NotBlank
    private String nome;

    @Field("manager_email")
    @NotBlank
    private String email;

    @Field("manager_senha")
    @NotBlank
    private String senha;

    @Field("dinheiro")
    @NotNull
    private int dinheiro;

    @Field("premium")
    @NotNull
    private boolean premium;

    @Field("manager_id_time")
    @DBRef
    private Time time;

    @Field("perfil")
    private Set<Integer> perfis;

    public Set<Perfil> getPerfis(){
        return perfis.stream().map(Perfil::toEnum).collect(Collectors.toSet());
    }

    public void addProfile(Perfil p){
        this.perfis.add(p.getCodigo());
    }
}