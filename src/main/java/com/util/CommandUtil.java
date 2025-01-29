package com.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CommandUtil {
	public static String run(String cmd) {
		
		StringBuilder output = new StringBuilder();
		ProcessBuilder processBuilder = new ProcessBuilder();
	    processBuilder.command("bash", "-c", cmd);
	    
	    BufferedReader reader = null;
	    InputStreamReader isr = null;
	    InputStream in = null;
	    try {
            Process process = processBuilder.start();
            in = process.getInputStream();
            isr = new InputStreamReader(in);
            reader = new BufferedReader(isr);
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            
            int exitCode = process.waitFor();
            if(exitCode != 0) {
            	throw new RuntimeException("执行命令：“" + cmd + "”时出现错误");
            }
            //System.out.println("Exit Code: " + exitCode);
            //System.out.println("Output:\n" + output.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader != null) {
					reader.close();
				}
				if(isr != null) {
					isr.close();
				}
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output.toString();
	}
}
