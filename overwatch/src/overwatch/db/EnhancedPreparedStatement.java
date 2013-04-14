


package overwatch.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;





/**
 * A nicer interface for PreparedStatements.
 * 
 * In this class there is no index-based parameterisation.
 * Instead use  <<namehere>> for parameters.
 * If multiple parameters have the same name, a set assigns to all of them at once. 
 * 
 * @author  Lee Coakley
 * @version 1
 */





public class EnhancedPreparedStatement
{
	private PreparedStatement ps;
	private Hashtable< String, ArrayList<Integer> > params;
	
	
	
	
	
	public EnhancedPreparedStatement( String sql )
	{
		params = new Hashtable< String, ArrayList<Integer> >();
		generateParamIndexMappings( sql );
		
		String     psSql = sql.replaceAll( "<<\\w+>>", "?" );
		Connection conn  = null;
		
		try {
			 conn = Database.getConnection();
			 ps   = conn.prepareStatement( psSql );
		}
		catch (SQLException ex) {
			throw new RuntimeException( ex );
		}
		finally {
			Database.returnConnection( conn );
		}
	}
	
	
	
	
	
	public void set( String param, Boolean bool )
	{
		checkParam( param );
	
		try {
			for (int i: params.get( param ))
				ps.setBoolean( i, bool );
		}
		catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	
	
	
	
	public void set( String param, Integer num )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				ps.setInt( i, num );
		}
		catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	
	
	
	
	public void set( String param, BigDecimal num )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				ps.setBigDecimal( i, num );
		}
		catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	
	
	
	
	public void set( String param, String num )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				ps.setString( i, num );
		}
		catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
	}
	
	
	
	
	
	private void checkParam( String param ) {
		if ( ! params.containsKey( param )) {
			String keys = "";
			
			for (String str: params.keySet())
				keys += " " + str;
			
			throw new RuntimeException( "Param doesn't exist.  Valid params are: " + keys );
		}
	}
	
	
	
	
	
	private void generateParamIndexMappings( String sql )
	{
		Pattern p = Pattern.compile( "<<\\w+>>" );
		Matcher m = p.matcher( sql );
		int     i = 1;
		
		while (m.find()) {
			String name = m.group().replaceAll( "<<|>>", "" );
			
			if ( ! params.containsKey( name ))
				params.put( name, new ArrayList<Integer>() );
			
			params.get( name ).add( i++ );
		}
	}
	
	
	
	
	
	public static void main( String[] args )
	{
		new EnhancedPreparedStatement(
			"select *            " +
			"from whatever       " +
			"where x = <<COOL>>  " +
			"  and y = <<COOL>>  " +
			"  and z = <<OTHER>> "
		);
	}
}
























































