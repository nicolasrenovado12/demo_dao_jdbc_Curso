package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("INSERT INTO department"
					+ "(Name)"
					+ " VALUES (?)",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());

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


		}

	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("UPDATE department"
					+ " SET Name = ?"
					+ " WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());

			st.executeUpdate(); 

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	
	}


	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("DELETE FROM department"
					+ " WHERE Id = ?",
					Statement.RETURN_GENERATED_KEYS);
			st.setInt(1, id);

			int rows = st.executeUpdate(); 

			if (rows == 0) {
				throw new DbException("Unexpected error");
			}

		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
	}



	@Override
	public Department findById(Integer id) {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement("SELECT * FROM department"
					+ " WHERE Id = ?");
			pst.setInt(1, id);
			
			rs = pst.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				return department;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);

		}
		return null;
	
	}

	@Override
	public List<Department> findAll() {
		List<Department> listDepartment = new ArrayList();
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = conn.prepareStatement("SELECT * FROM department");
			rs = pst.executeQuery();

			if (rs.next()) {
				Department department = instantiateDepartment(rs);
				listDepartment.add(department);
				return listDepartment;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(pst);
			DB.closeResultSet(rs);
		}
		return null;
	}

	private Department instantiateDepartment(ResultSet rs) {
		Department dep=null;
		try {
			dep = new Department(rs.getInt("Id"),
					   			rs.getString("Name"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dep;
	}

}
