package uk.co.webamoeba.mockito.collections.sample;

public interface SampleListener {

	@Deprecated
	void eventOccured(SampleData data);

	void eventOccured(String someEvent);

}
