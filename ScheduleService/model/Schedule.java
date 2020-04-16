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
	public String insertSchedule(ScheduleBean schB) {
		
	
    String output = "";

    try {

    	Connection con = DBConnection.connect();

        if (con == null) {

            return "Error - while inserting the Schedule details.";

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

	            output = "Schedule details inserted successfully.";

	        } catch (Exception e) {

	            output = "Error -  while inserting the Schedule details.";
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
					
					System.out.println("Error - While trying to view from database");
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
				System.out.println("Error - While trying to view schedule details");
				System.err.println(e.getMessage());
			}
			
			return ScheduleList;
		}
		
		//Update Schedule Details
		public String updateSchedule(ScheduleBean schB) {
			String output = "";
			try {
				Connection con = DBConnection.connect();

		            if (con == null) {

		                return "Error -  while updating the Schedule details.";

		            }
			
				String query = "update `Schedule` set"
								+"`s_date`=?,`s_time`=?,`s_type`=?,"
								+"where s_id = ? ";
						

				PreparedStatement preparedSt = con.prepareStatement(query);

				
				// binding values
				preparedSt.setString(1, schB.getScheduleDate());
				preparedSt.setString(2, schB.getScheduleTime());
				preparedSt.setString(3, schB.getScheduleType());			
				preparedSt.setInt(4, schB.getId());
				
				

				// Prepared Statement Execution
				preparedSt.execute();
				con.close();
				output = "Doctor details updated successfully.";

	        } catch (Exception e) {

	            output = "Error -  while updating the Schedule details.";
	            System.err.println(e.getMessage());

	        }

	        return output;

		}

		
		//remove schedule
		public String removeSchedule(String ScheduleID) {
			 String output = "";

		        try {

		        	Connection con = DBConnection.connect();
				if (con == null) {
					return "Error - occurred while deleting the schedule details.";
				}
				
				// Prepared Statement
				String query = "delete from Schedule where ScheduleID=?";
				
				PreparedStatement preparedSt = con.prepareStatement(query);

				
				// Binding values
				preparedSt.setInt(1, Integer.parseInt(ScheduleID));

				
				// execute the statement
				preparedSt.execute();
				con.close();
				 output = "Schedule details deleted successfully.";

	        } catch (Exception e) {

	            output = "Error -  while deleting the schedule details.";
	            System.err.println(e.getMessage());

	        }

	        return output;
		}

		public List<ScheduleBean> getShedulesByDate(String Sch_Date){
			List<ScheduleBean> list = new ArrayList<>();
		
			for(ScheduleBean schB :viewSchedule()){
				if(schB.getScheduleDate()== Sch_Date) {
					list.add(schB);
				}		
			}
			
			return list;
		}
		
		public List<ScheduleBean> getShedulesByTime(String Sch_Time){
			List<ScheduleBean> list = new ArrayList<>();
		
			for(ScheduleBean schB : viewSchedule()){
				if(schB.getScheduleTime()==Sch_Time) {
					list.add(schB);
				}		
			}
			
			return list;
		}
		
		public List<ScheduleBean> getShedulesByType(String Sch_Type){
			List<ScheduleBean> list = new ArrayList<>();
		
			for(ScheduleBean schB : viewSchedule()){
				if(schB.getScheduleType()==Sch_Type) {
					list.add(schB);
				}		
			}
			
			return list;
		}
		
		
		

		
		
}
