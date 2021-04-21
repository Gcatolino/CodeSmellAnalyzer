package it.unisa.codeSmellAnalyzer.computation;

import java.io.File;
import java.util.Vector;

import org.eclipse.core.runtime.CoreException;

import it.unisa.codeSmellAnalyzer.metrics.CKMetrics;
import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.beans.MethodBean;
import it.unisa.codeSmellAnalyzer.beans.PackageBean;
import it.unisa.codeSmellAnalyzer.testSmellDetector.*;

public class RunCodeSmellDetection {
	public static void main(String args[]) {

		// Path to the directory containing all the projects under analysis 
		String pathToDirectory = "/Users/fabiopalomba/Documents/workspace/SWUM/";
		File experimentDirectory = new File(pathToDirectory);

		// Output file declaration
		String output = "testCase,assertionRoulette,mysteryGuest,eagerTest,sensitiveEquality,lazyTest,resourceOptimistism\n";
		String outputFile = "/Users/fabiopalomba/Desktop/output.csv";

		// Declaring Class-level test smell objects.
		AssertionRoulette assertionRoulette = new AssertionRoulette();
		MysteryGuest mysteryGuest = new MysteryGuest();
		EagerTest eagerTest = new EagerTest();
		SensitiveEquality sensitiveEquality = new SensitiveEquality();
		LazyTest lazyTest = new LazyTest();
		ResourceOptimistism resourceOptimistism = new ResourceOptimistism();
		
		TestMutationUtilities testMutationUtilities = new TestMutationUtilities();

		ClassDataShouldBePrivateRule cdsbp = new ClassDataShouldBePrivateRule();
		ComplexClassRule complexClass = new ComplexClassRule();
		FunctionalDecompositionRule functionalDecomposition = new FunctionalDecompositionRule();
		GodClassRule godClass = new GodClassRule();
		SpaghettiCodeRule spaghettiCode = new SpaghettiCodeRule(); 

		for(File project: experimentDirectory.listFiles()) {
			try {
				// Method to convert a directory into a set of java packages.
				Vector<PackageBean> packages = FolderToJavaProjectConverter.convert(project.getAbsolutePath());
				//ArrayList<ClassBean> productionClasses = packages.getClasses();
				ArrayList<ClassBean> testClasses = testMutationUtilities.getTestCases(packages);

				for(ClassBean classBean: testClasses) {
					ClassBean productionClass = testMutationUtilities.getProductionClass(classBean.getName(), packages);

					boolean isClassDataShouldBePrivate = cdsbp.isClassDataShouldBePrivate(classBean);
					boolean isComplexClass = complexClass.isComplexClass(classBean);
					boolean isFunctionalDecomposition = functionalDecomposition.isFunctionalDecomposition(classBean);
					boolean isGodClass = godClass.isGodClass(classBean);
					boolean isSpaghettiCode = spaghettiCode.isSpaghettiCode(classBean);

					double loc = CKMetrics.getLOC(classBean);
					
					double lcom2 = CKMetrics.getLCOM2(classBean);
					double lcom5 = CKMetrics.getLCOM5(classBean);
					double connectivity = CKMetrics.getConnectivity(classBean);
					double tcc = CKMetrics.getTCC(classBean);
					double lcc = CKMetrics.getLCC(classBean);

					double cbo = CKMetrics.getCBO(classBean);
					
					double wmc = CKMetrics.getMcCabeMetric(classBean);
					double rfc = CKMetrics.getRFC(classBean);
					double mpc = CKMetrics.getMPC(classBean);
					
					double halsteadVocabulary = CKMetrics.getHalsteadVocabulary(classBean);
					double halsteadLenght = CKMetrics.getHalsteadLenght(classBean);
					double halsteadVolume = CKMetrics.getHalsteadVolume(classBean);

					for(MethodBean testCase: classBean.getMethods()) {
		
						boolean isAssertionRoulette = assertionRoulette.isAssertionRoulette(testCase);
						boolean isMysteryGuest = mysteryGuest.isMysteryGuest(testCase);

						boolean isEagerTest = eagerTest.isEagerTest(testCase, productionClass);

						boolean isSensitiveEquality = sensitiveEquality.isSensitiveEquality(testCase);
						boolean isLazyTest = lazyTest.isLazyTest(testCase, productionClass);
						boolean isResourceOptimistism = resourceOptimistism.isResourceOptimistism(testCase);
					
						double tloc = testCase.getTextContent.split("\n");
						double tmcCabe = CKMetrics.getMcCabeMetric(testCase);
						double assertionDensity = CKMetrics.getAssertionDensity2(testCase);

						output += classBean.getBelongingPackage() + "." + classBean.getName() + "." + testCase.getName() + ","
							+ tloc + "," + lmcCabe + "," + assertionDensity + "," + isAssertionRoulette + "," + isMysteryGuest 
							+ "," + isEagerTest + "," + isSensitiveEquality + "," + isLazyTest + "," + isResourceOptimistism 
							+ "," + loc + "," + lcom2 + "," + lcom5 + "," + connectivity + "," + tcc + "," + lcc + "," + cbo 
							+ "," + wmc + "," + rfc + "," + mpc + "," + halsteadVocabulary + "," + halsteadLenght + "," + halsteadVolume 
							+ "," + isClassDataShouldBePrivate + "," + isComplexClass + "," + isFunctionalDecomposition 
							+ "," + isGodClass + "," + isSpaghettiCode + "\n";	
					}
				}

				FileUtility.writeFile(output, outputFile);

		} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}
}
