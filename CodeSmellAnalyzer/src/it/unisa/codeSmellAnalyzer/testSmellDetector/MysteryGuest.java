package it.unisa.codeSmellAnalyzer.testSmellDetector;

import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.beans.MethodBean;

public class MysteryGuest {

	public boolean isMysteryGuest(ClassBean pClassBean) {
		boolean mysteryGuest = false;

		for (MethodBean mb : pClassBean.getMethods()) {
			String body = mb.getTextContent();

			if (!mysteryGuest){
				if (body.contains(" File ") || body.contains(" File(") || body.contains("db")){
					mysteryGuest = true;
				}
			}
		}

		return mysteryGuest;
	}

	public boolean isMysteryGuest(MethodBean pMethodBean) {
		boolean mysteryGuest = false;
		String body = pMethodBean.getTextContent();

		if (!mysteryGuest){
			if (body.contains(" File ") || body.contains(" File(") || body.contains("db")){
				mysteryGuest = true;
			}
		}

		return mysteryGuest;
	}
}