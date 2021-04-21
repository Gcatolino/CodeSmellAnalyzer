package it.unisa.testSmellDiffusion.testMutation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.lucene.queryParser.ParseException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import it.unisa.testSmellDiffusion.beans.*;
import it.unisa.testSmellDiffusion.luceneElements.LuceneIndexer;
import it.unisa.testSmellDiffusion.parser.ClassParser;
import it.unisa.testSmellDiffusion.parser.CodeParser;


public class TestMutationUtilities {

	public ArrayList<ClassBean> getTestCases(Vector<PackageBean> pProject) {
		ArrayList<ClassBean> testCases = new ArrayList<ClassBean>();



		for(PackageBean packageBean: pProject) {
			for(ClassBean classBean: packageBean.getClasses()) {

				if(classBean.getTextContent().contains("EvoSuiteTest {")) {
					testCases.add(classBean);
				}

				/*if(classBean.getTextContent().contains(" extends TestCase")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@Test")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@BeforeClass")) {
					testCases.add(classBean);
				}*/
			}
		}

		return testCases;
	}
	
	public ArrayList<ClassBean> getManuallyWrittenTestCases(Vector<PackageBean> pProject) {
		ArrayList<ClassBean> testCases = new ArrayList<ClassBean>();



		for(PackageBean packageBean: pProject) {
			for(ClassBean classBean: packageBean.getClasses()) {

				if(classBean.getTextContent().contains(" extends TestCase")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@Test")) {
					testCases.add(classBean);
				} else if(classBean.getTextContent().contains("@BeforeClass")) {
					testCases.add(classBean);
				}
			}
		}

		return testCases;
	}

	public static ClassBean getProductionClassBy(String pTestSuiteName, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {

				String productionClassName = pTestSuiteName.replace("EvoSuiteTest", "");

				if(productionClassName.equals(classBean.getName())) 
					return classBean;

			}
		}

		return null;
	}
	
	public static ClassBean getManuallyWrittenProductionClassBy(String pTestSuiteName, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {

				String testSuiteName = pTestSuiteName.replace("Test", "");
						
				if(classBean.getName().toLowerCase().equals(testSuiteName.toLowerCase())) 
					return classBean;

			}
		}

		return null;
	}
	

	public static MethodBean getProductionMethodBy(String pTestMethodCall, Vector<PackageBean> pSystem) {

		for(PackageBean packageBean : pSystem) {
			for(ClassBean classBean : packageBean.getClasses()) {
				for(MethodBean methodBean : classBean.getMethods()) {
					if(methodBean.getName().equals(pTestMethodCall)) 
						return methodBean;
				}
			}
		}

		return null;
	}

	public static MethodBean getProductionMethodBy(String pTestMethodCall) {
		try {
			List<String> documents = LuceneIndexer.getAllDocumentContents();
			CodeParser parser = new CodeParser();

			for(String document: documents) {

				CompilationUnit compilationUnit = parser.createParser(document);

				if(compilationUnit.types().size() > 0) {
					TypeDeclaration typeDeclaration = (TypeDeclaration)compilationUnit.types().get(0);
					Vector<String> imports = new Vector<String>();

					ClassBean classBean = ClassParser.parse(typeDeclaration, "src", imports);

					for(MethodBean methodBean : classBean.getMethods()) {
						if(methodBean.getName().equals(pTestMethodCall)) 
							return methodBean;
					}
				}
			}

		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}

		return null;

	}
}