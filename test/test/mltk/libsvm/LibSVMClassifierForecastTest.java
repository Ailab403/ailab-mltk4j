package test.mltk.libsvm;

import org.junit.Test;
import org.mltk.libsvm.LibSVMClassifierForecast;
import org.mltk.libsvm.model.ClassifyRes;
import org.mltk.libsvm.model.TestDataItem;

/**
 * 
 * @author superhy
 *
 */
public class LibSVMClassifierForecastTest {

	@Test
	public void testLibSVMForecast() {

		Double[] vector = new Double[] { 0.0, 0.0 };
		TestDataItem testDataItem = new TestDataItem("1", vector, 2);

		LibSVMClassifierForecast classifierForecast = new LibSVMClassifierForecast();
		ClassifyRes classifyRes = classifierForecast.exec(
				"./file/libsvm/svm_model_file", testDataItem);

		System.out.println(classifyRes.toString());
	}
}
