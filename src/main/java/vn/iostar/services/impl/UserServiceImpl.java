package vn.iostar.services.impl;

import vn.iostar.dao.IUserDao;
import vn.iostar.dao.impl.UserDaoImpl;
import vn.iostar.models.UserModel;
import vn.iostar.services.IUserService;

public class UserServiceImpl implements IUserService{
	
	IUserDao userDao = new UserDaoImpl();
	
	@Override
	public UserModel login(String username, String password) {
		UserModel user = this.FindByUsername(username);
		if (user != null && password.equals(user.getPassword())) {
			return user;
		}
		return null;
	}

	@Override
	public UserModel FindByUsername(String username) {
		return userDao.findByUsername(username);
	}
	
}
