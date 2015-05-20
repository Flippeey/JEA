package batch;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import javax.batch.api.chunk.AbstractItemReader;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

@Named
public class TweetReader extends AbstractItemReader {

	private JsonReader reader;
	private Gson gson;

	@SuppressWarnings("cdi-ambiguous-dependency")
	@Inject
	private JobContext jobContext;

	@Override
	public Object readItem() throws Exception {
		if (reader.hasNext()) {
			return gson.fromJson(reader, InputTweet.class);
		}
		return null;
	}

	@Override
	public void open(Serializable prevCheckpointInfo) throws Exception {
            System.out.println("Open @ tweetreader");
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties jobParameters = jobOperator.getParameters(jobContext
				.getExecutionId());
		String jsonUrl = (String) jobParameters.get("jsonUrl");
		URL url = new URL(jsonUrl);
		InputStream inputStream = url.openStream();
		InputStreamReader inputStreamReader = new InputStreamReader(
				inputStream, StandardCharsets.UTF_8.name());
		reader = new JsonReader(inputStreamReader);
		reader.beginObject();
		reader.nextName();
		reader.beginArray();
		gson = new Gson();
	}

	public void close() throws Exception {
		reader.close();
	}
}