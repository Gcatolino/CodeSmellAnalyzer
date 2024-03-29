package it.unisa.codeSmellAnalyzer.testSmellDetector;

import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.metrics.CKMetrics;

import java.util.Vector;

public class EagerTest {

	public boolean isEagerTest(ClassBean pClassBean, ClassBean pProductionClass) {
		boolean eagerTest = false;

		for (MethodBean mb : pClassBean.getMethods()) {
			if (!mb.getName().equals(pClassBean.getName())
					&& !mb.getName().toLowerCase()
					.equals("setup")
					&& !mb.getName().toLowerCase()
					.equals("teardown") && !eagerTest) {
				Vector<MethodBean> calledMethods = (Vector<MethodBean>) mb
						.getMethodCalls();



				if (calledMethods.size() > 1){
					Vector<MethodBean> cbMethods = (Vector<MethodBean>) pProductionClass.getMethods();
					int count = 0;
					for(MethodBean cm : calledMethods){
						for (MethodBean cbMethod : cbMethods){
							if (cbMethod.getName().toLowerCase().equals(cm.getName().toLowerCase())){
								count++;
							}
						}
					}

					if(count > 1)
						eagerTest = true;
				}
			}
		}

		return eagerTest;
	}

	public boolean isEagerTest(MethodBean pMethodBean, ClassBean pProductionClass) {
		boolean eagerTest = false;

		if (!pMethodBean.getName().equals(pMethodBean.getBelongingClass().getName())
				&& !pMethodBean.getName().toLowerCase()
				.equals("setup")
				&& !pMethodBean.getName().toLowerCase()
				.equals("teardown")) {
			Vector<MethodBean> calledMethods = (Vector<MethodBean>) pMethodBean
					.getMethodCalls();

			if (calledMethods.size() > 1){
				Vector<MethodBean> cbMethods = (Vector<MethodBean>) pProductionClass.getMethods();
				int count = 0;
				for(MethodBean cm : calledMethods){
					for (MethodBean cbMethod : cbMethods){
						if (cbMethod.getName().toLowerCase().equals(cm.getName().toLowerCase())){
							count++;
						}
					}
				}

				if(count > 1)
					eagerTest = true;
			}
		}

		return eagerTest;
	}

}
