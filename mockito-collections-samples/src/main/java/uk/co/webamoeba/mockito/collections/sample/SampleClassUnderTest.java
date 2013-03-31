package uk.co.webamoeba.mockito.collections.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SampleClassUnderTest {

	private SampleCollaborator sampleCollaborator;

	private Collection<SampleListener> sampleListeners;

	private List<SampleDataProvider> sampleDataProviders;

	public SampleData callCollaboratorAndSampleListeners(SampleData data) {
		SampleData newData = sampleCollaborator.method(data);
		for (SampleListener listener : sampleListeners) {
			listener.eventOccured(newData);
		}
		return newData;
	}

	public List<SampleData> callSampleDataProvidersAndGetSampleData() {
		List<SampleData> sampleData = new ArrayList<SampleData>();
		for (SampleDataProvider provider : sampleDataProviders) {
			sampleData.add(provider.getSampleData());
		}
		return sampleData;
	}

	public void setSampleCollaborator(SampleCollaborator sampleCollaborator) {
		this.sampleCollaborator = sampleCollaborator;
	}

	public void setSampleListeners(Collection<SampleListener> sampleListeners) {
		this.sampleListeners = sampleListeners;
	}

	public void setSampleDataProviders(List<SampleDataProvider> sampleDataProviders) {
		this.sampleDataProviders = sampleDataProviders;
	}
}
