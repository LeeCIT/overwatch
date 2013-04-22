


package overwatch.db;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
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
	
	
	
	
	
	/**
	 * Create a new parameterised SQL query or update.
	 * Don't forget to return the connection to the database after.
	 * @param conn Connection to create PreparedStatement on.  Needed if you want to lock/transact.
	 * @param sql SQL with parameters.
	 */
	public EnhancedPreparedStatement( Connection conn, String sql )
	{
		params = new Hashtable< String, ArrayList<Integer> >();
		generateParamIndexMappings( sql );
		
		String psSql = sql.replaceAll( "<<\\w+>>", "?" );
		
		try {
			 ps = conn.prepareStatement( psSql, PreparedStatement.RETURN_GENERATED_KEYS );
		}
		catch (SQLException ex) {
			throw new DatabaseException( ex );
		}
	}
	
	
	
	
	
	/**
	 * Create a new parameterised SQL query or update.
	 * Automatically acquires/releases the connection during construction.
	 * @param sql SQL with parameters.
	 */
	public EnhancedPreparedStatement( String sql )
	{
		Connection conn = Database.getConnection();
		
		try     { commonConstruct( conn, sql ); }
		finally { Database.returnConnection( conn ); }
	}
	
	
	
	
	/**
	 * Set parameter
	 * @param param
	 * @param bool
	 */
	public void set( String param, Boolean bool )
	{
		checkParam( param );
	
		try {
			for (int i: params.get( param ))
				if (bool != null)
					 ps.setBoolean( i, bool );
				else ps.setNull   ( i, Types.BOOLEAN );
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	
	
	/**
	 * Set parameter
	 * @param param
	 * @param num
	 */
	public void set( String param, Integer num )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				if (num != null)
					 ps.setInt ( i, num );
				else ps.setNull( i, Types.INTEGER );
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	
	
	/**
	 * Set parameter
	 * @param param
	 * @param num
	 */
	public void set( String param, BigDecimal num )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				if (num != null)
					 ps.setBigDecimal( i, num );
				else ps.setNull      ( i, Types.NUMERIC );
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	
	
	/**
	 * Set parameter
	 * @param param
	 * @param str
	 */
	public void set( String param, String str )
	{
		checkParam( param );
		
		try {
			for (int i: params.get( param ))
				if (str != null)
					 ps.setString( i, str );
				else ps.setNull  ( i, Types.VARCHAR );
		}
		catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	
	
	
	/**
	 * Run the query.
	 * @return EnhancedResultSet
	 */
	public EnhancedResultSet query()
	{
		EnhancedResultSet ers = null;
		
		try {
			ResultSet rs = ps.executeQuery();
    			ers = new EnhancedResultSet( rs );
    		rs.close();
		}
		catch (Exception ex) {
			throw new DatabaseException( ex );
		}
    	
    	return ers;
	}
	
	
	
	
	
	/**
	 * Run the update.
	 * @return int rows modified by update
	 */
	public int update()
	{
		try {
			return ps.executeUpdate();
		}
		catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ex) {
			throw new DatabaseIntegrityException( ex );
		}
		catch (Exception ex) {
			throw new DatabaseException( ex );
		}
	}
	
	
	
	
	
	/**
	 * Close the internal PreparedStatement.
	 * After calling this the object can no longer be used.
	 */
	public void close() {
		
		try {
			ps.close();
		}
		catch (Exception ex) {
			throw new DatabaseException( ex );
		}
	}
	
	
	
	
	
	/**
	 * Get auto-generated primary key from an insert statement, if there is one.
	 * @param param
	 */
	public Integer getGeneratedKey()
	{
		try {
			ResultSet rs = ps.getGeneratedKeys();
				EnhancedResultSet ers = new EnhancedResultSet( rs );
			rs.close();
			
			return (int) (long) ers.getElemAs( 0, Long.class );
		}
		catch (SQLException ex) {
			throw new DatabaseException( ex );
		}
	}
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////	
	
	private void commonConstruct( Connection conn, String sql )
	{
		params = new Hashtable< String, ArrayList<Integer> >();
		generateParamIndexMappings( sql );
		
		String psSql = sql.replaceAll( "<<\\w+>>", "?" );
		
		try {
			 ps = conn.prepareStatement( psSql, PreparedStatement.RETURN_GENERATED_KEYS );
		}
		catch (SQLException ex) {
			throw new DatabaseException( ex );
		}
	}
	
	
	
	
	
	private void checkParam( String param ) {
		if ( ! params.containsKey( param ))			
			throw new RuntimeException( "Param '" + param + "' doesn't exist.  Valid params are: " + params.keySet() );
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
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Test
	/////////////////////////////////////////////////////////////////////////	
	
	public static void main( String[] args )
	{
		EnhancedPreparedStatement psQuery = new EnhancedPreparedStatement(
			"select *      " +
			"from Vehicles " +
			"where pilot = <<pilot>>   " +
			"   or name like(<<name>>);"
		);
		
		psQuery.set( "pilot",  (Integer) null );
		psQuery.set( "name",   "%ank%");
		
		System.out.println( psQuery.query() );
		
		
		
		
		
		EnhancedPreparedStatement psUpdate = new EnhancedPreparedStatement(
			"insert into Vehicles " +
			"values( default, <<name>>, <<pilot>> );"
		);
		
		psUpdate.set( "name",  "Whatever"     );
		psUpdate.set( "pilot", (Integer) null );
		
		System.out.println( psUpdate.update() + " rows modified." );
		System.out.println( psUpdate.getGeneratedKey() + " was the primary key generated." );
	}
}
























































