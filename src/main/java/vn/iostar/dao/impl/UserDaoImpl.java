package vn.iostar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import vn.iostar.configs.DBConnectSQL;
import vn.iostar.dao.IUserDao;
import vn.iostar.models.UserModel;

public class UserDaoImpl implements IUserDao{
	
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;

	@Override
	public List<UserModel> findAll() {

		String sql = "SELECT * FROM users";
		
		try {
			conn = new DBConnectSQL().getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			
			List<UserModel> list = new ArrayList<UserModel>();
			
			while (rs.next()) {
				list.add(new UserModel(
						rs.getInt("id"),
						rs.getString("email"),
						rs.getString("username"),
						rs.getString("password"),
						rs.getString("fullname"),
						rs.getString("image"),
						rs.getString("phone"),
						rs.getInt("roleid"),
						rs.getDate("createDate")));
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}

	@Override
	public UserModel findByID(int id) {

		String sql = "SELECT * FROM users WHERE id = ? ";
		
		try {
			conn = new DBConnectSQL().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImage(rs.getString("image"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setCreateDate(rs.getDate("createDate"));
				return user; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}

	@Override
	public void insert(UserModel user) {

		String sql = "INSERT INTO users(username, email, password, fullname, image, phone, roleid, createDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
			conn = new DBConnectSQL().getConnection();
			
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getEmail());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getFullname());
			ps.setString(5, user.getImage());
			ps.setString(6, user.getPhone());
			ps.setInt(7, user.getRoleid());
			ps.setDate(8, user.getCreateDate());
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserModel findByUsername(String username) {
		
		String sql = "SELECT * FROM users WHERE username = ? ";
		
		try {
			conn = new DBConnectSQL().getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				UserModel user = new UserModel();
				user.setId(rs.getInt("id"));
				user.setEmail(rs.getString("email"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setFullname(rs.getString("fullname"));
				user.setImage(rs.getString("image"));
				user.setPhone(rs.getString("phone"));
				user.setRoleid(Integer.parseInt(rs.getString("roleid")));
				user.setCreateDate(rs.getDate("createDate"));
				return user; 
			}
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			IUserDao userDao = new UserDaoImpl();
			System.out.println(userDao.findAll());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
