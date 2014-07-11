package edu.ubbcluj.emotion.test;

import static edu.ubbcluj.emotion.util.StringUtil.debug;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.model.Emotion;
import edu.ubbcluj.emotion.test.halftraining.ClosestDistanceTest;

public class TestActivator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		final ResourceLoaderFactory resourceLoaderFactory = getService(ResourceLoaderFactory.class, context);

		Test test = new ClosestDistanceTest();
		double[] result = test.run(resourceLoaderFactory, "openimajdiff2", 20);

		for (int i = 0; i < result.length; i++) {
			debug("Emotion " + Emotion.values()[i] + " recognised in " + result[i] * 100 + "%");
		}

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// TODO Auto-generated method stub

	}

	private <T> T getService(final Class<T> service, final BundleContext context) {
		final ServiceReference<T> reference = context.getServiceReference(service);
		return context.getService(reference);
	}

}
