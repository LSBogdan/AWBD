package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.Token;
import com.ProjX.projxpersitance.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    List<Token> findAllByUserIdAndLoggedOutFalse(String userId);

    Optional<Token> findByToken(String token);
}
