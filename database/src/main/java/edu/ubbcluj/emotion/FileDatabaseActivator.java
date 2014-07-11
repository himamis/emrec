package edu.ubbcluj.emotion;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.loader.FileResourceLoaderFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.loader.ResourceLoaderFactory;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;

public class FileDatabaseActivator implements BundleActivator {

	private static final Logger								logger	= LoggerFactory.getLogger(FileDatabaseActivator.class);

	private ServiceRegistration<DatabaseInformationFactory>	databaseInformationFactorySR;
	private ServiceRegistration<ResourceLoaderFactory>		resourceLoaderFactorySR;
	private ServiceRegistration<ResourceManagerFactory>		resourceManagerFactorySR;

	@Override
	public void start(final BundleContext context) throws Exception {
		registerDatabaseInformationFactory(context);
		registerResourceLoaderFactory(context);
		registerResourceManagerFactory(context);
	}

	@Override
	public void stop(final BundleContext context) throws Exception {
		unregisterDatabaseInformationFactory();
		unregisterResourceLoaderFactory();
		unregisterResourceManagerFactory();
	}

	private void registerDatabaseInformationFactory(final BundleContext context) {
		final DatabaseInformationFactory dif = new FileDatabaseInformationFactory();
		databaseInformationFactorySR = registerService(DatabaseInformationFactory.class, dif, context);
		logger.debug("DatabaseInformationFactory registered");

	}

	private void registerResourceLoaderFactory(final BundleContext context) {
		final ResourceLoaderFactory rlf = new FileResourceLoaderFactory();
		resourceLoaderFactorySR = registerService(ResourceLoaderFactory.class, rlf, context);
		logger.debug("ResourceLoaderFactory registered");
	}

	private void registerResourceManagerFactory(final BundleContext context) {
		final ResourceManagerFactory rmf = new FileResourceManagerFactory();
		resourceManagerFactorySR = registerService(ResourceManagerFactory.class, rmf, context);
		logger.debug("ResourceManagerFactory registered");
	}

	private void unregisterDatabaseInformationFactory() {
		unregisterService(databaseInformationFactorySR);
		logger.debug("DatabaseInformationFactory unregistered");
	}

	private void unregisterResourceLoaderFactory() {
		unregisterService(resourceLoaderFactorySR);
		logger.debug("ResourceLoaderFactory unregistered");
	}

	private void unregisterResourceManagerFactory() {
		unregisterService(resourceManagerFactorySR);
		logger.debug("ResourceManagerFactory unregistered");
	}

	private <T> ServiceRegistration<T> registerService(final Class<T> clazz, final T service, final BundleContext context) {
		return context.registerService(clazz, service, null);
	}

	private <T> void unregisterService(final ServiceRegistration<T> serviceRegistration) {
		serviceRegistration.unregister();
	}

}
