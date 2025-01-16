package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n"
					+ "FROM seller INNER JOIN department\n"
					+ "ON seller.DepartmentId = department.Id\n"
					+ "WHERE seller.Id = ?;");
					
			pst.setInt(1, id);
			
			rs = pst.executeQuery();
			if (rs.next()) {
				Department department = new Department(rs.getInt("DepartmentId"),
													   rs.getString("DepName"));
				Seller seller = new Seller(
						rs.getInt("Id"), 
						rs.getString("Name"),
						rs.getString("Email"),
						new java.sql.Date
						(rs.getDate("BirthDate").getTime()),
						rs.getDouble("BaseSalary"), 
						department);
				
				return seller;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
			DB.closeConnection();
		}
		
		
		return null;
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
