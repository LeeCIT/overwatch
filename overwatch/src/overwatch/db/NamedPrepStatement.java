


package overwatch.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;





/**
 * Manages name-to-parameter mappings for prepared statements.
 *  
 * @author Lee Coakley
 * @version 1
 */





public class NamedPrepStatement
{
	private PreparedStatement                    ps;
	private Hashtable<String,ArrayList<Integer>> map;
	
	
	
	
	
	public NamedPrepStatement( PreparedStatement ps )
	{
		this.ps  = ps;
		this.map = new Hashtable<String,ArrayList<Integer>>();
	}
	
	
	
	
	
	public PreparedStatement getPs() {
		return ps;
	}
	
	
	
	
	
	public void addParam( String name, int indexMapping ) throws SQLException
	{
		ArrayList<Integer> mapping = map.get( name );
		
		if (null == mapping) {
			mapping = new ArrayList<Integer>();
			map.put( name, mapping );
		}
		
		mapping.add( indexMapping );
	}
	
	
	
	
	
	public void setParam( String param, String str ) throws SQLException
	{
		for (int index: map.get(param)) {
			ps.setString( index, str );
		}
	}
	
	
	
	
	
	public void setParam( String param, int i ) throws SQLException
	{
		for (int index: map.get(param)) {
			ps.setInt( index, i );
		}
	}
	
	
		
	
	
	public void setParam( String param, double d ) throws SQLException
	{
		for (int index: map.get(param)) {
			ps.setDouble( index, d );
		}
	}
	
	
	
	
	
	public void setParam( String param, Date d ) throws SQLException
	{
		for (int index: map.get(param)) {
			ps.setDate( index, new java.sql.Date(d.getTime()) );
		}
	}
	
	
	
	
	
	public EnhancedResultSet executeQuery() throws SQLException {
		return new EnhancedResultSet( ps.executeQuery() );
	}
	
	
	
	
	
	public void executeUpdate() throws SQLException {
		ps.executeUpdate();
	}
}





















































