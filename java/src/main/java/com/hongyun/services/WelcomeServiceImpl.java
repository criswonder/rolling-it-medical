/*
  GRANITE DATA SERVICES
  Copyright (C) 2011 GRANITE DATA SERVICES S.A.S.

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

package com.hongyun.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.granite.messaging.service.annotations.RemoteDestination;

import com.hongyun.entities.Welcome;


@Service("welcomeService")
@RemoteDestination(id="welcomeService", source="welcomeService")
public class WelcomeServiceImpl implements WelcomeService {

    @PersistenceContext
    private EntityManager entityManager;
    

    @Transactional
    public Welcome hello(String name) {
        if (name == null || name.trim().length() == 0)
            throw new RuntimeException("Name cannot be null or empty");
        
        Welcome welcome = null;
        try {
            Query q = entityManager.createQuery("select w from Welcome w where w.name = :name");
            q.setParameter("name", name);
            welcome = (Welcome)q.getSingleResult();
        }
        catch (NoResultException e) {
            welcome = new Welcome();
            welcome.setName(name);
            welcome.setMessage("Welcome " + name);
            entityManager.persist(welcome);
            entityManager.createQuery("select w from Welcome w").getResultList();
        }
        return welcome;
    }
    
    public List<Welcome> getList(){
    	return (ArrayList<Welcome>)entityManager.createQuery("select w from Welcome w").getResultList();
//    	return null;
    }
    @Transactional
    public void deleteIt(Welcome wel){
    	if(wel==null || wel.getName()==null)
    		return;
    	Query q = entityManager.createQuery("delete  from Welcome w where w.name = :name");
        q.setParameter("name", wel.getName());
        q.executeUpdate();
    }
    
    @Transactional
    public void updateIt(Welcome wel){
    	if(wel==null || wel.getName()==null)
    		return;
    	Query q = entityManager.createQuery("update Welcome w set w.message = :message where w.name = :name");
        q.setParameter("name", wel.getName());
        q.setParameter("message", wel.getMessage());
        q.executeUpdate();
    }
}
