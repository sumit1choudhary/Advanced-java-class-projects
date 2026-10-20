package com.shopsphere.dao;
import com.shopsphere.model.User; import com.shopsphere.util.DB; import java.sql.*;
public class UserDAO {
 public boolean register(User u) throws SQLException {
  String q="INSERT INTO users(name,email,password,mobile) VALUES(?,?,?,?)";
  try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(q)){p.setString(1,u.getName());p.setString(2,u.getEmail());p.setString(3,u.getPassword());p.setString(4,u.getMobile());return p.executeUpdate()>0;}
 }
 public User login(String email,String password)throws SQLException{
  String q="SELECT * FROM users WHERE email=? AND password=? AND status=1";
  try(Connection c=DB.getConnection();PreparedStatement p=c.prepareStatement(q)){p.setString(1,email);p.setString(2,password);try(ResultSet r=p.executeQuery()){if(r.next()){User u=new User();u.setUserId(r.getInt("user_id"));u.setName(r.getString("name"));u.setEmail(r.getString("email"));u.setRole(r.getString("role"));u.setMobile(r.getString("mobile"));return u;}}}return null;
 }
}
