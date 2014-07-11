package edu.ubbcluj.emotion;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import edu.ubbcluj.emotion.ck.file.info.FileDatabaseInformationFactory;
import edu.ubbcluj.emotion.ck.file.manager.FileResourceManagerFactory;
import edu.ubbcluj.emotion.database.file.info.DatabaseInformationFactory;
import edu.ubbcluj.emotion.database.file.manager.ResourceManagerFactory;
import edu.ubbcluj.emotion.swing.MainWindow;

public class ViewerActivator implements BundleActivator {

	@Override
	public void start(final BundleContext context) throws Exception {
		final ResourceManagerFactory rmf = getService(ResourceManagerFactory.class, context);
		final DatabaseInformationFactory dif = getService(DatabaseInformationFactory.class, context);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MainWindow f = new MainWindow(dif, rmf);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(700, 600);
				f.setResizable(false);
				f.setVisible(true);
			}
		});

	}

	@Override
	public void stop(final BundleContext context) throws Exception {
	}

	private <T> T getService(final Class<T> service, final BundleContext context) {
		final ServiceReference<T> reference = context.getServiceReference(service);
		return context.getService(reference);
	}

	public static void main(String[] args) {
		final ResourceManagerFactory rmf = new FileResourceManagerFactory();
		final DatabaseInformationFactory dif = new FileDatabaseInformationFactory();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final MainWindow f = new MainWindow(dif, rmf);
				f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				f.setSize(700, 600);
				f.setResizable(false);
				f.setVisible(true);
			}
		});
	}

}
