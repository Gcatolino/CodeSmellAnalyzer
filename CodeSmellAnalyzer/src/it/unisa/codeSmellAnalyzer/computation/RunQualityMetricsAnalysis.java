package it.unisa.codeSmellAnalyzer.computation;

import java.io.File;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.beans.PackageBean;
import it.unisa.codeSmellAnalyzer.metrics.CKMetrics;

public class RunQualityMetricsAnalysis {

	public static void main(String args[]) {
		
		// Path to the directory containing all the projects under analysis 
		//String pathToDirectory = "/Users/fabiopalomba/Documents/workspace/TACO-detector/";
		String pathToDirectory = "/Users/fabiopalomba/Documents/workspace/SWUM/";
		
		File experimentDirectory = new File(pathToDirectory);
		
		for(File project: experimentDirectory.listFiles()) {
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				
				String output = "className;LOC;LCOM;CBO;WMC;RFC\n";
				
				for(PackageBean packageBean: packages) {
					for(ClassBean classBean: packageBean.getClasses()) {
									
						output+=packageBean.getName() +"."+classBean.getName() + ";" + CKMetrics.getLOC(classBean) + ";" 
								+ CKMetrics.getLCOM2(classBean) + ";" + CKMetrics.getCBO(classBean) + ";" 
								+ CKMetrics.getWMC(classBean) + ";" + CKMetrics.getRFC(classBean) + "\n";
						
						// Other metrics are available in the CKMetrics class.
						
					}
				}	
				
				FileUtility.writeFile(output, "/Users/fabiopalomba/Desktop/results/" + project.getName() + ".csv");
			} catch (CoreException e) {
				e.printStackTrace();
			}	
		}
	}
}
