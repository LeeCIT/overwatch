


package overwatch.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;





/**
 * Provides a nicer interface for reading SQL results.
 * It's not tied to a DB connection, is iterable, has random access, and
 * allows you to access rows and columns as arrays.
 * 
 * Uses zero-based indeces.
 * 
 * @author  Lee Coakley
 * @version 4
 */





public class EnhancedResultSet implements Iterable<Object[]>
{
	private ArrayList<Object[]>	rows;
	private String[]			columnNames;
	private int[]				columnTypes;
	private int					columnCount;
	
	
	
	
	
	public EnhancedResultSet( ResultSet set ) throws SQLException
	{
		populateColumnInfo( set );
		populateRowData   ( set );
	}
	
	
	
	
	
	/**
	 * Access an element in the set.
	 * @param row
	 * @param col
	 * @return Object
	 */
	public Object getElem( int row, int col ) {
		return getRow( row )[ col ];
	}
	
	
	
	
	
	/**
	 * Get total number of rows.
	 */
	public int size() {
		return getRowCount();
	}
	
	
	
	
	
	/**
	 * Access specific row.
	 */
	public Object[] getRow( int row ) {
		return rows.get( row );
	}
	
	
	
	
	
	/**
	 * Get total number of rows.
	 */
	public int getRowCount() {
		return rows.size();
	}
	
	
	
	
	
	/**
	 * Get total number of columns.
	 */
	public int getColumnCount() {
		return columnCount;
	}
	
	
	
	
	
	/**
	 * Get names of each column.
	 */
	public String[] getColumnNames() {
		return columnNames;
	}
	
	
	
	
	
	/**
	 * Get types of each column.
	 * These can be compared with java.sql.Types.
	 */
	public int[] getColumnTypes() {
		return columnTypes;
	}
	
	
	
	
	
	/**
	 * Access an entire column as an array.
	 */
	public Object[] getColumn( int column )
	{
		Object[] columnArray = new Object[ getRowCount() ];
		
		for (int row=0; row<columnArray.length; row++) {
			columnArray[row] = getElem( row, column );
		}
		
		return columnArray;
	}
	
	
	
	
	
	/**
	 * Access an entire column as an array.
	 */
	public Object[] getColumn( String name ) {
		int index = getColumnIndex( name );
		return getColumn( index );
	}
	
	
	
	
	
	/**
	 * Copy column and cast it to a specific type.
	 * Example:
	 *     Integer[] array = ers.getColumnAs( 0, Integer[].class );
	 */
	public <T> T[] getColumnAs( int column, Class<? extends T[]> type ) {
		return Arrays.copyOf( getColumn(column), size(), type );
	}
	
	
	
	
	
	/**
	 * Copy column and cast it to a specific type.
	 */
	public <T> T[] getColumnAs( String name, Class<? extends T[]> type ) {
		return getColumnAs( getColumnIndex(name), type );
	}
	
	
	
	
	
	/**
	 * Given the column name, find the index.
	 * Returns -1 if it can't be found.
	 */
	public int getColumnIndex( String name )
	{
		for (int col=0; col<columnNames.length; col++) 
			if (columnNames[col].equals( name )) 
				return col;
		
		return -1;
	}
	
	
		
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////////////////
	// Internals
	/////////////////////////////////////////////////////////////////////////	
	
	
	
	private void populateColumnInfo( ResultSet set ) throws SQLException
	{
		ResultSetMetaData meta = set.getMetaData();
		
		columnCount = meta.getColumnCount();
		columnNames = new String[ columnCount ];
		columnTypes = new int   [ columnCount ];
		
		for (int i=0; i<columnCount; i++) {
			columnNames[i] = meta.getColumnLabel( sqlIndex(i) );
			columnTypes[i] = meta.getColumnType ( sqlIndex(i) );
		}
	}
	
	
	
	
	
	private void populateRowData( ResultSet set ) throws SQLException
	{
		rows = new ArrayList< Object[] >();
		
		while ( set.next() ) {
			Object[] row = new Object[ columnCount ];
			
			for (int i=0; i<columnCount; i++) 
				row[i] = set.getObject( sqlIndex(i) );
			
			rows.add( row );
		}
	}
	
	
	
	
	
	private int sqlIndex( int zeroIndex ) {
		return ++zeroIndex;
	}
	
	
	
	
	
	/**
	 * Defines iterator so you can do stuff like:
	 * 		for (Object[] row: set)
	 * 			whatever( row );
	 */
	public Iterator<Object[]> iterator()
	{
		return new Iterator<Object[]>()
		{
			private int index = -1;
			
			public boolean  hasNext() {	 return (index + 1) < size();		  		 }
		    public Object[] next()    {  return getRow( ++index );  				 }
			public void     remove()  {  throw new UnsupportedOperationException();  }
		};
	}
}














































