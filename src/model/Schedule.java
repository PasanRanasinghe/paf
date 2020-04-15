package model;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



import beans.ScheduleBean;
import util.DBConnection;

public class Schedule {
	
	//Insert a Schedule
	public String insertSchedule(ScheduleBean schB)
	{

		String output = "";
		
		try 
		{
			Connection con = DBConnection.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			
				
			String query =" INSERT INTO `Schedule`"
						+ "(`ScheduleID`, `ScheduleDate`, `ScheduleTime`, `ScheduleType`) "
						+ "values (?,?,?,?)";
			
			PreparedStatement preparedSt = con.prepareStatement(query);
			
			
			//blinding values
			preparedSt.setInt(1, 0);
			preparedSt.setString(2, schB.getScheduleDate());
			preparedSt.setString(3, schB.getScheduleTime());
			preparedSt.setString(4, schB.getScheduleType());
			
			//Execute the prepared statements
			preparedSt.execute();
			con.close();
			output = "Inserted successfully";
			
			
		} catch (Exception e) {
			
			output = "Error while inserting the schedule.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public List<ScheduleBean> viewSchedule() {
		
		return	viewSchedule(0);

	}
	
	public ScheduleBean viewScheduleById(int id) {
	List<ScheduleBean> list =viewSchedule(id);
		if(!list.isEmpty()) {
			return	list.get(0);
		}
		return null;
	}
	
	//View the Schedule
		public List<ScheduleBean> viewSchedule(int id) {
				List <ScheduleBean> ScheduleList = new ArrayList<>();
				
			try 
			{
				Connection con = DBConnection.connect();
				if (con == null) {
					
					System.out.println("Error While reading from database");
					return ScheduleList;
				}
				String query;
				if(id == 0) {
					query = "select * from Schedule";
				}else {
					query = "select * from Schedule where ScheduleID="+id;
				}
				
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);

				while (rs.next()) 
				{
					ScheduleBean schB = new ScheduleBean
							(
									rs.getInt("ScheduleID"),
									rs.getString("ScheduleDate"),
									rs.getString("ScheduleTime"),
									rs.getString("ScheduleType"),
									query
									);
												
										
					ScheduleList.add(schB);

				}
				con.close();

			}
			catch (Exception e) {
				System.out.println("Error While Reading");
				System.err.println(e.getMessage());
			}
			
			return ScheduleList;
		}
		
		//Update Schedule Details
		public String updateSchedule(ScheduleBean sch) {
			String output = "";
			
			try 
			{
				Connection con = DBConnection.connect();
				
				if (con == null) {
					return "Error while connecting to the database for updating.";
				}
				
				
			
				String query = "UPDATE `Schedule` SET"
								+"`s_date`=?,`s_time`=?,`s_type`=?,"
								+"WHERE s_id = ? ";
						

				PreparedStatement preparedSt = con.prepareStatement(query);

				
				// binding values
				preparedSt.setString(1, sch.getScheduleDate());
				preparedSt.setString(2, sch.getScheduleTime());
				preparedSt.setString(3, sch.getScheduleType());			
				preparedSt.setInt(4, sch.getId());
				
				

				// Prepared Statement Execution
				preparedSt.execute();
				con.close();
				output = "Updated successfully";
				
			} 
			catch (Exception e) {
				output = "Error while updating the Schedule.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}

		
		//remove schedule
		public String removeSchedule(String ScheduleID) {
			String output = "";
			
			try 
			{
				Connection con = DBConnection.connect();
			
				if (con == null) {
					return "Error while connecting to the database for deleting.";
				}
				
				
				// Prepared Statement
				String query = "delete from Schedule where ScheduleID=?";
				
				PreparedStatement preparedSt = con.prepareStatement(query);

				
				// Binding values
				preparedSt.setInt(1, Integer.parseInt(ScheduleID));

				
				// execute the statement
				preparedSt.execute();
				con.close();
				output = "Deleted successfully";
				
			} 
			catch (Exception e) {
				output = "Error while deleting the Schedule.";
				System.err.println(e.getMessage());
			}
			
			return output;
		}


		

		
		
}
