/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.service;

import java.io.Serializable;
import javax.ejb.Stateless;
import main.dao.DriverDao;
import main.domain.Driver;

/**
 *
 * @author maikel
 */
@Stateless
public class DriverService extends DriverDao implements Serializable{

    @Override
    protected Class<Driver> getEntityClass() {
        return Driver.class;
    }
    
}
