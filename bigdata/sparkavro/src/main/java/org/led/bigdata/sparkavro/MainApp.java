package org.led.bigdata.sparkavro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.avro.Schema;
import org.apache.avro.mapred.AvroKey;
import org.apache.avro.mapred.AvroValue;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.led.bigdata.sparkavro.avro.ReceptionReport;
import org.led.bigdata.sparkavro.data.AvroReceptionReportKey;
import org.led.bigdata.sparkavro.data.AvroReceptionReportKeyInputFormat;
import org.led.bigdata.sparkavro.data.AvroValueInputFormat;
import org.led.bigdata.sparkavro.data.EmptyWritable;
import org.led.bigdata.sparkavro.data.SerialClientId;
import org.led.bigdata.sparkavro.data.SerialReceptionReport;

import scala.Tuple2;

public class MainApp {
	// mvn clean install
	// jar umf src/META-INF/MANIFEST.MF target/sreporting-0.0.1-SNAPSHOT.jar
	// go cp target/sreporting-0.0.1-SNAPSHOT.jar 200:/var/tmp/reporting
	// /opt/spark/default/bin/spark-submit --class "org.led.sreporting.MainApp"
	// --master local[4] sreporting-0.0.1-SNAPSHOT.jar 2>&1 | grep "==="
	private static void countRecord() {
		long start = System.currentTimeMillis();
		SparkConf conf = new SparkConf().setAppName("CountRecord");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaPairRDD<String, String> logs = sc.wholeTextFiles("/user/reporting").cache();

		long numAs = logs.keys().filter(new Function<String, Boolean>() {
			public Boolean call(String s) {
				return s.contains("a");
			}
		}).count();

		long numBs = logs.keys().filter(new Function<String, Boolean>() {
			public Boolean call(String s) {
				return s.contains("b");
			}
		}).count();
		sc.close();
		long cost = System.currentTimeMillis() - start;
		System.out.println("Lines with a: " + numAs + ", lines with b: " + numBs + " with time cost: " + cost);

	}

	private static void testAvro() {
		String fp = "/var/reporting/content/avro/132/132_BDC100_127.0.0.1.avro";
		System.out.println("=======test avro start===========");
		long start = System.currentTimeMillis();
		SparkConf conf = new SparkConf().setAppName("CountRecord");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaPairRDD<AvroReceptionReportKey, EmptyWritable> data = sc.newAPIHadoopFile(fp,
				AvroReceptionReportKeyInputFormat.class, AvroReceptionReportKey.class, EmptyWritable.class,
				sc.hadoopConfiguration());
		List<AvroReceptionReportKey> keys = data.keys().collect();
		for (AvroKey key : keys) {
			System.out.println("==avro key==" + key.datum().toString());
		}
		System.out.println("=======test avro stop===========");
	}

	@SuppressWarnings({ "serial", "resource", "unchecked" })
	private static void testAvro2() {
		String fp = "/var/erreporting/content/avro/132/132_BDC100_127.0.0.1.avro";
		System.out.println("=======test avro start===========");
		long start = System.currentTimeMillis();
		SparkConf conf = new SparkConf().setAppName("CountRecord");
		JavaSparkContext sc = new JavaSparkContext(conf);
		Configuration hadoopConf = new Configuration();
		hadoopConf.set("avro.schema.input.key", Schema.create(org.apache.avro.Schema.Type.NULL).toString());
		hadoopConf.set("avro.schema.input.value", ReceptionReport.SCHEMA$.toString());

		JavaPairRDD<NullWritable, AvroValue<ReceptionReport>> avroRDD = sc.newAPIHadoopFile(fp,
				AvroValueInputFormat.class, NullWritable.class, AvroValue.class, hadoopConf);

		// ============display the clientId of each Reception
		// Report====================
		avroRDD.foreach(new VoidFunction<Tuple2<NullWritable, AvroValue<ReceptionReport>>>() {
			public void call(Tuple2<NullWritable, AvroValue<ReceptionReport>> arg0) throws Exception {
				ReceptionReport rr = arg0._2().datum();
				System.out.println("=1==client id===" + rr.getClientId());
			}
		});

		// ============transform ==================
		JavaPairRDD<String, SerialReceptionReport> avroParsedData = avroRDD.mapToPair(
				new PairFunction<Tuple2<NullWritable, AvroValue<ReceptionReport>>, String, SerialReceptionReport>() {
					private static final long serialVersionUID = 1L;

					public Tuple2<String, SerialReceptionReport> call(
							Tuple2<NullWritable, AvroValue<ReceptionReport>> t) throws Exception {
						ReceptionReport oriData = t._2().datum();
						SerialReceptionReport data = new SerialReceptionReport(oriData);

						return new Tuple2<String, SerialReceptionReport>(String.valueOf(data.getSessionStartTime()),
								data);
						// return new Tuple2<String, SerialReceptionReport>(
						// String.valueOf(data.getClientId()), data );
					}
				});
		avroParsedData.foreach(new VoidFunction<Tuple2<String, SerialReceptionReport>>() {
			public void call(Tuple2<String, SerialReceptionReport> arg0) throws Exception {
				String cid = arg0._1();
				String srr = arg0._2().toString();
				System.out.println("=2==start time===" + cid + ": " + srr);
				// System.out.println("=2==client id===" + cid + ": " + srr);
			}
		});

		// ========================self transform==========
		/*
		 * JavaPairRDD<String, SerialClientId> avroSelfData =
		 * avroParsedData.mapValues( new Function<SerialReceptionReport,
		 * SerialClientId>() {
		 * 
		 * private static final long serialVersionUID = 1L;
		 * 
		 * public SerialClientId call( SerialReceptionReport rrs ) throws
		 * Exception { SerialClientId obj = new SerialClientId();
		 * obj.add(String.valueOf(rrs.getClientId())); return obj; } } );
		 * avroSelfData.foreach(new VoidFunction<Tuple2<String,
		 * SerialClientId>>() { public void call(Tuple2<String, SerialClientId>
		 * arg0) throws Exception { String st = arg0._1(); String cl =
		 * arg0._2().toString(); System.out.println("=2.5==self start time===" +
		 * st + ": " + cl); //System.out.println("=2.5==self client id===" + st
		 * + ": " + cl); } });
		 */

		// =====================group==================
		JavaPairRDD<String, Iterable<SerialReceptionReport>> avroGroupedByKeyData = avroParsedData.groupByKey();
		/*
		 * System.out.println("=3==try start time=== 1: "); if
		 * (avroGroupedByKeyData == null) { System.out.println(
		 * "=3==try start time=== 2: key is empty"); } else { JavaRDD<String>
		 * keys = avroGroupedByKeyData.keys(); System.out.println(
		 * "=3==try start time=== 3 keys: " + keys.toDebugString()); try {
		 * List<String> kl = keys.collect(); System.out.println(
		 * "=3==try start time=== 4 key size: " + kl.size()); for(String key :
		 * kl) { System.out.println("	=3==try start time=== 5 keys: " + key);
		 * } } catch(Exception e) { System.out.println(
		 * "=3==try start time=== 6 ERROR: " + e); }
		 * 
		 * }
		 */

		avroGroupedByKeyData.foreach(new VoidFunction<Tuple2<String, Iterable<SerialReceptionReport>>>() {
			public void call(Tuple2<String, Iterable<SerialReceptionReport>> arg0) throws Exception {
				String st = arg0._1();
				List<SerialReceptionReport> rrList = createList(arg0._2());
				System.out.println("=3==start time===" + st + "; size: " + rrList.size());
				for (SerialReceptionReport srr : rrList) {
					System.out.println("		=3==client id===" + srr.getClientId());
				}
			}
		});

		JavaPairRDD<String, SerialClientId> avroMappedValueData = avroGroupedByKeyData
				.mapValues(new Function<Iterable<SerialReceptionReport>, SerialClientId>() {

					private static final long serialVersionUID = 1L;

					public SerialClientId call(Iterable<SerialReceptionReport> rrs) throws Exception {
						List<SerialReceptionReport> rrList = createList(rrs);
						SerialClientId obj = new SerialClientId();
						if (rrList.size() > 0) {
							for (SerialReceptionReport srr : rrList) {
								obj.add(String.valueOf(srr.getClientId()));
							}
						}
						return obj;
					}
				});
		avroMappedValueData.foreach(new VoidFunction<Tuple2<String, SerialClientId>>() {
			public void call(Tuple2<String, SerialClientId> arg0) throws Exception {
				String st = arg0._1();
				String cl = arg0._2().toString();
				System.out.println("=4==start time===" + st + ": " + cl);
			}
		});

		// ======================reduceby
		System.out.println("=======test avro stop===========");
		long cost = System.currentTimeMillis() - start;
		System.out.println("===testAvro2===" + " with time cost: " + cost);
	}

	private static <T> List<T> createList(Iterable<T> iter) {
		ArrayList<T> list = new ArrayList<T>();
		for (T item : iter) {
			list.add(item);
		}
		return list;
	}

	private static void test() {
		long start = System.currentTimeMillis();
		SparkConf conf = new SparkConf().setAppName("CountRecord");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaPairRDD<String, String> logs = sc.wholeTextFiles("/user/reporting").cache();
		System.out.println("===app=== values: " + logs.values());

		List<String> contents = logs.values().collect();
		List<String> lines = new ArrayList<String>();
		for (String content : contents) {
			String[] subLines = content.split("\n");
			lines.addAll(Arrays.asList(subLines));
		}
		JavaRDD<String> fullLog = sc.parallelize(lines);

		long numAs = fullLog.filter(new Function<String, Boolean>() {
			public Boolean call(String s) {
				//System.out.println("	===temp check=== fullLog line: " + s);
				return s.contains("a");
			}
		}).count();

		
		//========================flatMapValue work with new Function (not FlatMapFunction)======
		JavaPairRDD<String, String> lineLog = logs.flatMapValues(new Function<String, Iterable<String>>() {
			public Iterable<String> call(String arg0) throws Exception {
				String[] lines = arg0.split("\n");
				return Arrays.asList(lines);
			}
		});
		
		JavaPairRDD<String, String> wordLog = lineLog.flatMapValues(new Function<String, Iterable<String>>() {
			public Iterable<String> call(String arg0) throws Exception {
				String[] items = arg0.trim().split(" ");
				return Arrays.asList(items);
			}
		});
		
		JavaPairRDD<String, String> fullWordLog = wordLog.filter(new Function<Tuple2<String, String>, Boolean>() {
			public Boolean call(Tuple2<String, String> arg0) throws Exception {
				if ((arg0._2() == null) || (arg0._2().trim().equals("")) ) {
					//System.out.println("	===temp check=== invalid word: <" + arg0._2() + ">");
					return false;
				} else {
					return true;
				}
			}			
		});
		
		JavaPairRDD<String, Integer> wordList = fullWordLog.mapToPair(new PairFunction<Tuple2<String, String>, String, Integer>(){
			public Tuple2<String, Integer> call(Tuple2<String, String> t) throws Exception {
				return new Tuple2<String, Integer> (t._2(), new Integer(1));
			}			
		});
		
		JavaPairRDD<String, Integer> keyRed = wordList.reduceByKey(new Function2<Integer, Integer, Integer>() {
			public Integer call(Integer v1, Integer v2) throws Exception {
				return v1 + v2;
			}			
		});
				
		//============tuple2 aggregate=================
		//====In aggregate seq function, the function only returns the last result as the input of comb function
		//====That's why the tuple2 aggregate does not work===
		Function2<Tuple2<String, Integer>, Tuple2<String, String>, Tuple2<String, Integer>> tSeqOp = new Function2<Tuple2<String, Integer>, Tuple2<String, String>, Tuple2<String, Integer>>() {
			public Tuple2<String, Integer> call(Tuple2<String, Integer> v1, Tuple2<String, String> v2)
					throws Exception {
				//System.out.println("	===app tSeqOp=== key: <" + v2._1() + ">, value: <" + v2._2() + ">");
				return new Tuple2<String, Integer>(v2._2(), new Integer(1));
			}			
		};
		
		Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>> tCombOp = new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
			public Tuple2<String, Integer> call(Tuple2<String, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
				//System.out.println("	===app tCombOp=== key: <" + v2._1() + ">, value: <" + v2._2() + ">");
				if(v2._1().equals("the")){
					//System.out.println("	===app tCombOp=== key: <" + v2._1() + ">, value: <" + v2._2() + ">");
					return new Tuple2<String, Integer>("the", v1._2() + v2._2());
				}
				return v1;
			}			
		};
		
		
		Tuple2<String, Integer> tAgg = fullWordLog.aggregate(new Tuple2<String, Integer>("the", new Integer(0)), tSeqOp, tCombOp);
		System.out.println("	===app tuple2 aggregation=== key: <" + tAgg._1() + ">, value: <" + tAgg._2() + ">");
		
		//============map aggregate=================
		Function2<Map<String, Integer>, Tuple2<String, String>, Map<String, Integer>> mSeqOp = new Function2<Map<String, Integer>, Tuple2<String, String>, Map<String, Integer>>() {
			public Map<String, Integer> call(Map<String, Integer> v1, Tuple2<String, String> v2)
					throws Exception {
				String word = v2._2();
				if (v1.containsKey(word)) {
					Integer count = v1.get(word) + 1;
					v1.put(word, count);
				} else {
					v1.put(word, new Integer(1));
				}
				return v1;
			}			
		};
		Function2<Map<String, Integer>, Map<String, Integer>, Map<String, Integer>> mCombOp = new Function2<Map<String, Integer>, Map<String, Integer>, Map<String, Integer>>() {
			public Map<String, Integer> call(Map<String, Integer> v1, Map<String, Integer> v2) throws Exception {
				for (String key : v2.keySet()) {
					if (v1.containsKey(key)) {
						v1.put(key,  v1.get(key) + v2.get(key));
					} else {
						v1.put(key, v2.get(key));
					}
				}
				return v1;
			}			
		};
		Map<String, Integer> mAgg = fullWordLog.aggregate(new HashMap<String, Integer>(), mSeqOp, mCombOp);
		for (String key : mAgg.keySet()) {
			System.out.println("	===app map aggregation=== key: <" + key + ">, value: <" + mAgg.get(key) + ">");
		}
		
		
		//=============reduce function=== almost not useful=========
		/*Tuple2<String, Integer> valueRed = wordList.reduce(new Function2<Tuple2<String, Integer>, Tuple2<String, Integer>, Tuple2<String, Integer>>() {
			public Tuple2<String, Integer> call(Tuple2<String, Integer> v1, Tuple2<String, Integer> v2) throws Exception {
				return new Tuple2<String, Integer>(v1._1(), v1._2() + v2._2() );
			}			
		});
		System.out.println("	===app reduce=== key: <" + valueRed._1() + ">, value: <" + valueRed._2() + ">");*/

		
		//==================display the output======================
		long count = lineLog.count();
		/*List<Tuple2<String, Integer>> contents2 = keyRed.collect();
		for (Tuple2<String, Integer> content : contents2) {
			System.out.println("	===app=== key: <" + content._1() + ">, value: <" + content._2() + ">");
		}*/
		
		long cost = System.currentTimeMillis() - start;
		System.out.println("===app=== count: " + count + " with time cost: " + cost);
	}

	public static void main(String[] args) {
		test();
		// countRecord();
		// testAvro2();
	}
}
