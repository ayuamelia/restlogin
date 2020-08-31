package com.ayu.restservice.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ayu.restservice.entity.User;

@Mapper
public interface UserMapper {

	public List listUser();
	
	public User findUser(String username);
}
