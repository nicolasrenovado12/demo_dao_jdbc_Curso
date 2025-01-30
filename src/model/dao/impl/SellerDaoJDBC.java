package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC  implements SellerDao  {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public void insert(Seller obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ " VALUES (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId());


			int rowsAffected = st.executeUpdate(); 

			if (rowsAffected > 0) {
				rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1); 
					obj.setId(id);

				}
			DB.closeResultSet(rs);
			} else {
				throw new DbException("Unexpected error! No rows affected! ");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());

		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);

		}
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
				Department department = instantiateDepartment(rs);
				Seller seller = instantiateSeller(rs, department);
				
				return seller;
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} 
		finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		
		
		return null;
	}

	private Seller instantiateSeller(ResultSet rs, Department department ) {
		Seller seller = null;
		try {
			seller = new Seller(
					rs.getInt("Id"), 
					rs.getString("Name"),
					rs.getString("Email"),
					new java.sql.Date
					(rs.getDate("BirthDate").getTime()),
					rs.getDouble("BaseSalary"), 
					department);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return seller;
	}

	private Department instantiateDepartment(ResultSet rs) {
		Department dep=null;
		try {
			dep = new Department(rs.getInt("DepartmentId"),
					   			rs.getString("DepName"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		List<Seller> all = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n"
					+ "FROM seller INNER JOIN department\n"
					+ "ON seller.DepartmentId = department.Id\n"
					+ "WHERE departmentId = departmentId "
					+ "ORDER BY Name;");
					

			
			rs = pst.executeQuery();
			
			Map<Integer, Department> map = new HashMap();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
		
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				
				
				Seller seller = instantiateSeller(rs, dep);
				all.add(seller);
				

			}
			return all;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}

	}

	public List<Seller> findByDepartment(Department department) {
		int idDepartment = department.getId();
		List<Seller> all = new ArrayList<>();
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		try {
			pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName\n"
					+ "FROM seller INNER JOIN department\n"
					+ "ON seller.DepartmentId = department.Id\n"
					+ "WHERE departmentId = ? "
					+ "ORDER BY Name;");
					
			pst.setInt(1, idDepartment);
			
			rs = pst.executeQuery();
			
			Map<Integer, Department> map = new HashMap();
			
			while (rs.next()) {
				
				Department dep = map.get(rs.getInt("DepartmentId"));
		
				if(dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
					
				}
				
				
				Seller seller = instantiateSeller(rs, dep);
				all.add(seller);
				

			}
			return all;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
	}

	
	
}
