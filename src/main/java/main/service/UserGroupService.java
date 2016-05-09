/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import java.io.Serializable;
import javax.ejb.Stateless;
import main.dao.UserGroupDao;
import main.domain.UserGroup;

/**
 *
 * @author maikel
 */
@Stateless
public class UserGroupService extends UserGroupDao implements Serializable{

    @Override
    protected Class<UserGroup> getEntityClass() {
        return UserGroup.class; 
    }
    
}
