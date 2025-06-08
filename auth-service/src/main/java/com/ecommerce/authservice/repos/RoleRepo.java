package com.ecommerce.authservice.repos;

import com.ecommerce.authservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
