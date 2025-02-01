package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program_departmentDao {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);

		DepartmentDao deparmentDao = DaoFactory.createDepartmentDao();
		
		
		System.out.println("=== DEPARTMENT ===");
		System.out.println("=== TEST 1: department findById =====");
		Department department = deparmentDao.findById(3);
		System.out.println(department);

		
		System.out.println("=== TEST 2: department findAll =====");
		List<Department> departmentList = deparmentDao.findAll();
		System.out.println(departmentList);
		
		System.out.println("=== TEST 3: department insert =====");
		Department newDepartment = new Department(null, "Bottles");
		deparmentDao.insert(newDepartment);
		System.out.println("Inserted complete, id = " + newDepartment.getId());
		
		System.out.println("=== TEST 4: department update =====");
		department = deparmentDao.findById(1);
		department.setName("Martha Waine");
		deparmentDao.update(department);
		System.out.println("Update completed");

		System.out.println("\n=== TEST 5: department delete =====");
		System.out.println("Enter id for delete test: ");
		int id = sc.nextInt();
		deparmentDao.deleteById(id);
		System.out.println("Delete completed");
		

		sc.close();	
		
		
		
	}
	
	
}
