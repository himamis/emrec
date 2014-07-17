package edu.ubbcluj.emotion.crossvalidation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.openimaj.data.RandomData;
import org.openimaj.data.dataset.GroupedDataset;
import org.openimaj.data.dataset.ListBackedDataset;
import org.openimaj.data.dataset.ListDataset;
import org.openimaj.data.dataset.MapBackedDataset;
import org.openimaj.experiment.validation.DefaultValidationData;
import org.openimaj.experiment.validation.ValidationData;
import org.openimaj.experiment.validation.cross.CrossValidationIterable;
import org.openimaj.experiment.validation.cross.CrossValidator;

public class GroupedRandomSplitHalf<KEY, INSTANCE> implements CrossValidator<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>> {

	private class GroupedRandomSplitHalfIterable implements CrossValidationIterable<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>> {

		private GroupedDataset<KEY, ? extends ListDataset<INSTANCE>, INSTANCE>	dataset;

		private int																iterations;

		/**
		 * Construct the {@link GroupedRandomSplitHalfIterable} with the given
		 * dataset.
		 * 
		 * @param dataset
		 *            the dataset.
		 */
		public GroupedRandomSplitHalfIterable(GroupedDataset<KEY, ? extends ListDataset<INSTANCE>, INSTANCE> dataset, int iterations) {
			this.dataset = dataset;
			this.iterations = iterations;
		}

		/**
		 * Get the number of iterations that the {@link Iterator} returned by
		 * {@link #iterator()} will perform.
		 * 
		 * @return the number of iterations that will be performed
		 */
		@Override
		public int numberIterations() {
			return iterations;
		}

		@Override
		public Iterator<ValidationData<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>>> iterator() {
			return new Iterator<ValidationData<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>>>() {
				int	index	= 0;

				@Override
				public boolean hasNext() {
					return index < iterations;
				}

				@Override
				public ValidationData<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>> next() {
					final Map<KEY, ListDataset<INSTANCE>> train = new HashMap<KEY, ListDataset<INSTANCE>>();
					final Map<KEY, ListDataset<INSTANCE>> valid = new HashMap<KEY, ListDataset<INSTANCE>>();

					for (KEY k : dataset.getGroups()) {
						final ListDataset<INSTANCE> instances = dataset.getInstances(k);
						final ListDataset<INSTANCE> trainInstances = new ListBackedDataset<INSTANCE>();
						final ListDataset<INSTANCE> validInstances = new ListBackedDataset<INSTANCE>();
						final int size = instances.size();
						final int[] ids = RandomData.getUniqueRandomIntsA(size, 0, size);
						for (int i = 0; i < ids.length / 2; i++) {
							trainInstances.add(instances.get(ids[i]));
						}
						for (int i = ids.length / 2; i < ids.length; i++) {
							validInstances.add(instances.get(ids[i]));
						}
						train.put(k, trainInstances);
						valid.put(k, validInstances);
					}

					final GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE> cvTrain = new MapBackedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>(
							train);
					final GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE> cvValid = new MapBackedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>(
							valid);

					index++;

					return new DefaultValidationData<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>>(cvTrain, cvValid);
				}

				@Override
				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
	}

	private int	iterations;

	public GroupedRandomSplitHalf(int iterations) {
		this.iterations = iterations;
	}

	@Override
	public CrossValidationIterable<GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE>> createIterable(
			GroupedDataset<KEY, ListDataset<INSTANCE>, INSTANCE> data) {
		return new GroupedRandomSplitHalfIterable(data, iterations);
	}

	@Override
	public String toString() {
		return "Split-Half cross validation for grouped data with " + iterations + " number of iterations.";
	}

}
