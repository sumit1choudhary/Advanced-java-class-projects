package com.shopsphere.service;
import com.shopsphere.dao.UserDAO; import com.shopsphere.model.User; import com.shopsphere.util.PasswordUtil;
public class AuthService { private final UserDAO dao=new UserDAO();
 public boolean register(User u)throws Exception{u.setPassword(PasswordUtil.hash(u.getPassword()));return dao.register(u);}
 public User login(String e,String p)throws Exception{return dao.login(e,PasswordUtil.hash(p));}
}
