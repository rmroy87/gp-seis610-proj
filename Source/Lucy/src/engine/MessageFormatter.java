package engine;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MessageFormatter {
	
	public static String exception(String msg, Throwable ex)
	{
	    StringBuilder sb = new StringBuilder();
	    for (StackTraceElement element : ex.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    
	    String returnMsg = String.format(
	    		"%s - %s\n STACKTRACE: %s", 
	    		new Object[] { msg, ex.getMessage(), sb.toString() });
	    return returnMsg;
	}
	
	public static String exception(Throwable ex)
	{
	    StringBuilder sb = new StringBuilder();
	    for (StackTraceElement element : ex.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    
	    String returnMsg = String.format(
	    		"%s\n STACKTRACE: %s", 
	    		new Object[] { ex.getMessage(), sb.toString() });
	    return returnMsg;
	}
	
	public static String dateTime(long time)
	{
		DateFormat dateFormat = new SimpleDateFormat(" yyyy/MM/dd HH:mm:ss");
		return dateFormat.format(time);

	}
}


