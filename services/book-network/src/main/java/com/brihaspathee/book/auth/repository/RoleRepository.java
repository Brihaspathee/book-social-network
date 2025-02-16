package com.brihaspathee.book.auth.repository;

import com.brihaspathee.book.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created in Intellij IDEA
 * User: Balaji Varadharajan
 * Date: 2/12/25
 * Time: 7:02 PM
 * Project: book-social-network
 * Package Name: com.brihaspathee.book.auth.repository
 * To change this template use File | Settings | File and Code Template
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String name);
}
