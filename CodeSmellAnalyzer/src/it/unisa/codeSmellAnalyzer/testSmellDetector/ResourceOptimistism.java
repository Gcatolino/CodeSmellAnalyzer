package it.unisa.codeSmellAnalyzer.testSmellDetector;

import java.io.File;

import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.beans.MethodBean;

public class ResourceOptimistism {

	public boolean isResourceOptimistism(ClassBean pTestSuite) {
		String code = pTestSuite.getTextContent();
		String lines[] = code.split("\n");
		
		for(int k=0; k<lines.length; k++) {
			if(lines[k].contains(" File ")) {
				
				if(lines[k].indexOf("\"") != -1) {
					String definedPath = lines[k].substring(lines[k].indexOf("\"")+1, lines[k].lastIndexOf("\""));
					
					File definedFile = new File(definedPath);
					
					if(! definedFile.exists()) 
						return true;
				}
			}
		}
		
		return false;
	}
	
	public boolean isResourceOptimistism(MethodBean pTestCase) {
		String code = pTestCase.getTextContent();
		String lines[] = code.split("\n");
		
		for(int k=0; k<lines.length; k++) {
			if(lines[k].contains(" File ")) {
				
				if(lines[k].indexOf("\"") != -1) {
					String definedPath = lines[k].substring(lines[k].indexOf("\"")+1, lines[k].lastIndexOf("\""));
					
					File definedFile = new File(definedPath);
					
					if(! definedFile.exists()) 
						return true;
				}
			}
		}
		
		return false;
	}
	
}
