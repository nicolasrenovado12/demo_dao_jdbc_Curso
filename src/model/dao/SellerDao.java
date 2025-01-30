package model.dao;

import java.util.List;

import db.DbException;
import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	
	void insert(Seller obj);
	void update(Seller obj);
	void deleteById(Integer id);
	List<Seller>  findByDepartment(Department department);
	Seller findById(Integer id);
	List<Seller> findAll();
	
}
