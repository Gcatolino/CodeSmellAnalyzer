package it.unisa.codeSmellAnalyzer.testSmellDetector;

import it.unisa.codeSmellAnalyzer.beans.ClassBean;
import it.unisa.codeSmellAnalyzer.metrics.CKMetrics;

public class AssertionRoulette {

	public boolean isAssertionRoulette(ClassBean pClassBean) {
		boolean assertionRoulette = false;

		for (MethodBean mb : pClassBean.getMethods()) {
			try {
				if (!assertionRoulette) {
					String methodBody = mb.getTextContent();
					methodBody = methodBody.replace(mb.getName(),
							"");

					while (methodBody.contains("assert")) {
						int indexStart = methodBody
								.indexOf("assert");
						int indexEnd = indexStart;
						char c = methodBody.charAt(indexStart);
						String substring = c + "";
						while (c != ';') {
							indexEnd++;
							c = methodBody.charAt(indexEnd);
							substring += c + "";
						}

						if (!substring.contains("\"")) {
							assertionRoulette = true;
						}

						methodBody = methodBody.replaceFirst(
								"assert", "");
					}

					while (methodBody.contains("fail(")) {
						int indexStart = methodBody
								.indexOf("fail(");
						int indexEnd = indexStart;
						char c = methodBody.charAt(indexStart);
						String substring = c + "";
						while (c != ';') {
							indexEnd++;
							c = methodBody.charAt(indexEnd);
							substring += c + "";
						}

						if (!substring.contains("\"")) {
							assertionRoulette = true;
						}

						methodBody = methodBody.replaceFirst(
								"fail\\(", "");
					}

					while (methodBody.contains("fail (")) {
						int indexStart = methodBody
								.indexOf("fail (");
						int indexEnd = indexStart;
						char c = methodBody.charAt(indexStart);
						String substring = c + "";
						while (c != ';') {
							indexEnd++;
							c = methodBody.charAt(indexEnd);
							substring += c + "";
						}

						if (!substring.contains("\"")) {
							assertionRoulette = true;
						}

						methodBody = methodBody.replaceFirst(
								"fail \\(", "");
					}
				}
			} catch(StringIndexOutOfBoundsException e) {
				return false;
			}
		}

		return assertionRoulette;
	}

	public boolean isAssertionRoulette(MethodBean pMethodBean) {
		boolean assertionRoulette = false;

		try {
			String methodBody = pMethodBean.getTextContent();
			methodBody = methodBody.replace(pMethodBean.getName(),
					"");

			while (methodBody.contains("assert")) {
				int indexStart = methodBody
						.indexOf("assert");
				int indexEnd = indexStart;
				char c = methodBody.charAt(indexStart);
				String substring = c + "";
				while (c != ';') {
					indexEnd++;
					c = methodBody.charAt(indexEnd);
					substring += c + "";
				}

				if (!substring.contains("\"")) {
					assertionRoulette = true;
				}

				methodBody = methodBody.replaceFirst(
						"assert", "");
			}

			while (methodBody.contains("fail(")) {
				int indexStart = methodBody
						.indexOf("fail(");
				int indexEnd = indexStart;
				char c = methodBody.charAt(indexStart);
				String substring = c + "";
				while (c != ';') {
					indexEnd++;
					c = methodBody.charAt(indexEnd);
					substring += c + "";
				}

				if (!substring.contains("\"")) {
					assertionRoulette = true;
				}

				methodBody = methodBody.replaceFirst(
						"fail\\(", "");
			}

			while (methodBody.contains("fail (")) {
				int indexStart = methodBody
						.indexOf("fail (");
				int indexEnd = indexStart;
				char c = methodBody.charAt(indexStart);
				String substring = c + "";
				while (c != ';') {
					indexEnd++;
					c = methodBody.charAt(indexEnd);
					substring += c + "";
				}

				if (!substring.contains("\"")) {
					assertionRoulette = true;
				}

				methodBody = methodBody.replaceFirst(
						"fail \\(", "");
			}
		} catch(StringIndexOutOfBoundsException e) {
			return false;
		}

		return assertionRoulette;
	}

}
