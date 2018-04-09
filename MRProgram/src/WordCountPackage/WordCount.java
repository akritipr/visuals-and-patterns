package WordCountPackage;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
	public static void main(String[] args) throws Exception {
		Configuration c = new Configuration();
		String[] files = new GenericOptionsParser(c, args).getRemainingArgs();
		Path input = new Path(files[0]);
		Path output = new Path(files[1]);
		Job j = new Job(c, "wordcount");
		j.setJarByClass(WordCount.class);
		j.setMapperClass(MapForWordCount.class);
		j.setReducerClass(ReduceForWordCount.class);
		j.setOutputKeyClass(Text.class);
		j.setOutputValueClass(IntWritable.class);
		FileInputFormat.addInputPath(j, input);
		FileOutputFormat.setOutputPath(j, output);
		System.exit(j.waitForCompletion(true) ? 0 : 1);
	}

	/**
	 * Mapper will output all the words in the file as the key with count as 1.
	 * The reducer method will count the number of times the particular word has
	 * occurred in the file and output the word as key and the number of
	 * occurrence as the value.
	 * 
	 * @author akriti
	 * 
	 */
	public static class MapForWordCount extends
			Mapper<LongWritable, Text, Text, IntWritable> {
		public void map(LongWritable key, Text value, Context con)
				throws IOException, InterruptedException {

			// Using compareWords to get the count of only these words being
			// used in the mapper input.

			String[] compareWords = { "loyolachicago", "loyola", "kansas",
					"villanova", "texas" };
			List<String> list = Arrays.asList(compareWords);
			String line = value.toString();
			String[] words = line.split(" ");
			for (String word : words) {
				if (list.contains(word.toLowerCase())) {
					Text outputKey = new Text(word.toUpperCase().trim());
					IntWritable outputValue = new IntWritable(1);
					con.write(outputKey, outputValue);
				}
			}

			// Commented code will output for each word.
			// String line = value.toString();
			// String[] words = line.split(" ");
			// for (String word : words) {
			// Text outputKey = new Text(word.toUpperCase().trim());
			// IntWritable outputValue = new IntWritable(1);
			// con.write(outputKey, outputValue);
			// }
		}
	}

	public static class ReduceForWordCount extends
			Reducer<Text, IntWritable, Text, IntWritable> {
		public void reduce(Text word, Iterable<IntWritable> values, Context con)
				throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable value : values) {
				sum += value.get();
			}
			con.write(word, new IntWritable(sum));
		}
	}
}
