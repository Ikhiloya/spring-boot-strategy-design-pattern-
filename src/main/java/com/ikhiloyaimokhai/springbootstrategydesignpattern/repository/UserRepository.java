package com.ikhiloyaimokhai.springbootstrategydesignpattern.repository;

import com.ikhiloyaimokhai.springbootstrategydesignpattern.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ikhiloya Imokhai on 2019-12-20.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

}
