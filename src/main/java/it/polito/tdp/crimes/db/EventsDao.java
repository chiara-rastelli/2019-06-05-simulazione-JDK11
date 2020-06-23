package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;



public class EventsDao {
		
	public List<Integer> listAllYears(){
		String sql = "SELECT distinct YEAR(reported_date) AS anno FROM EVENTS ORDER BY anno asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			List<Integer> list = new ArrayList<>();
			ResultSet res = st.executeQuery();
			while(res.next()) {
				try {
					list.add(res.getInt("anno"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllDistrictId(){
		String sql = "SELECT distinct district_id FROM EVENTS ORDER BY district_id asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			List<Integer> list = new ArrayList<>();
			ResultSet res = st.executeQuery();
			while(res.next()) {
				try {
					list.add(res.getInt("district_id"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Integer getDistrictMinoreCriminalita(int anno){
		String sql = 	"	SELECT COUNT(*) AS criminalita, district_id " + 
						"	FROM EVENTS " + 
						"	WHERE YEAR(reported_date) = ? " + 
						"	GROUP BY district_id " + 
						"	ORDER BY criminalita asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			int result = 0;
			ResultSet res = st.executeQuery();
			if(res.next()) {
				try {
					result = res.getInt("district_id");
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return result ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllMonths(){
		String sql = "SELECT DISTINCT MONTH(reported_date) AS mese FROM EVENTS ORDER BY mese asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			List<Integer> list = new ArrayList<>();
			ResultSet res = st.executeQuery();
			while(res.next()) {
				try {
					list.add(res.getInt("mese"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listAllDays(){
		String sql = "SELECT DISTINCT DAY(reported_date) AS giorno FROM EVENTS ORDER BY giorno asc";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			List<Integer> list = new ArrayList<>();
			ResultSet res = st.executeQuery();
			while(res.next()) {
				try {
					list.add(res.getInt("giorno"));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Distretto> listAllDistretti(int year){
		String sql = "	SELECT AVG(geo_lon) AS mediaLon1, AVG(geo_lat) AS mediaLat1, district_id " + 
				"	FROM EVENTS e1 " + 
				"	where YEAR(reported_date) = ? " + 
				"	GROUP BY district_id";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, year);
			List<Distretto> list = new ArrayList<>();
			ResultSet res = st.executeQuery();
			while(res.next()) {
				try {
					Integer id = res.getInt("district_id");
					LatLng posizione = new LatLng(res.getDouble("mediaLat1"), res.getDouble("mediaLon1"));
					list.add(new Distretto(id, posizione));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Event> listAllEventsSimulazione(int anno, int mese, int giorno){
		String sql = 	"SELECT * FROM EVENTS " + 
						"WHERE YEAR(reported_date) = ? " + 
						"AND MONTH(reported_date) = ? " + 
						"AND DAY(reported_date) = ? " + 
						"ORDER BY reported_date asc" ;
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, mese);
			st.setInt(3, giorno);
			List<Event> list = new ArrayList<>() ;
			ResultSet res = st.executeQuery() ;
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			conn.close();
			return list ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}

}
