package engine;

public class MessageFormatter {
	
	public static String exception(String msg, Throwable ex)
	{
	    StringBuilder sb = new StringBuilder();
	    for (StackTraceElement element : ex.getStackTrace()) {
	        sb.append(element.toString());
	        sb.append("\n");
	    }
	    
	    String returnMsg = String.format(
	    		"EXCEPTION: %s\n MESSAGE: %s\n STACKTRACE: %s", 
	    		new Object[] { msg, ex.getMessage(), sb.toString() });
	    return returnMsg;
	}
}


