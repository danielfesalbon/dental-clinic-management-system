/**
 * 
 */
package com.rest.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.app.table.Useraccount;

/**
 * @author danielf
 *
 */
public interface UseraccountRepository extends JpaRepository<Useraccount, String> {

}
