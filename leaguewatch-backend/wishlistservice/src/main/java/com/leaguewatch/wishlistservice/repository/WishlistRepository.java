package com.leaguewatch.wishlistservice.repository;

import com.leaguewatch.wishlistservice.model.Wishlist;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends CrudRepository<Wishlist, String> {
    @Query("SELECT w FROM Wishlist w WHERE w.id_league = ?1")
    Wishlist findByIdLeague(String id_league);

    @Query("SELECT w FROM Wishlist w WHERE ?1 MEMBER OF w.users")
    List<Wishlist> findByUserId(String user);
}
