package parallel;

import parallel.multiprocess.MultiProcess;
import parallel.multithread.MultiThread;
import parallel.reentrant.ReentrantTest;
import parallel.sync.Sync;

public class MainApp {
	
	public static void test(String ts) {
		switch(ts) {
		case "multi-process":
			MultiProcess.test();
			break;
		case "multi-thread":
			MultiThread.test();
			break;
		case "sync-unsync":
			Sync.test1();
			/*
			 * Output:
			 *  before 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:31:21 PM
				before 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:31:21 PM
				after 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:31:23 PM
				after 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:31:27 PM
			 */
			break;
		case "sync-inner-sync":
			Sync.test2();
			/*
			 * Output:
			 *  before 2 SA obj: parallel.sync.Item@ee107d9 ; now: 2:50:49 PM
			    after 2 SA obj: parallel.sync.Item@ee107d9 ; now: 2:50:55 PM
				before 2 SA obj: parallel.sync.Item@ee107d9 ; now: 2:50:55 PM
				after 2 SA obj: parallel.sync.Item@ee107d9 ; now: 2:50:57 PM
			 */
			break;
		case "sync-outer-sync":
			Sync.test3();
			/*
			 * Output:
			 *  before 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:32:45 PM
				after 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:32:51 PM
				before 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:32:51 PM
				after 1 SA obj: parallel.sync.Item@1efa9557 ; now: 3:32:53 PM
			 */
			break;
		case "sync-class-sync":
			Sync.test4();
			/*
			 * Output:
			 *  before 1 SA obj: parallel.sync.Item@586f403e ; now: 3:48:26 PM
				after 1 SA obj: parallel.sync.Item@586f403e ; now: 3:48:32 PM
				before 1 SA obj: parallel.sync.Item@14bf711e ; now: 3:48:32 PM
				after 1 SA obj: parallel.sync.Item@14bf711e ; now: 3:48:34 PM
			 */
			break;
		case "sync-different-inner-sync":
			Sync.test5();
			/*
			 * Output:
			 *  before 2 SA obj: parallel.sync.Item@626664fc ; now: 4:12:50 PM
				after 2 SA obj: parallel.sync.Item@626664fc ; now: 4:12:56 PM
				before 3 SA obj: parallel.sync.Item@626664fc ; now: 4:12:56 PM
				after 3 SA obj: parallel.sync.Item@626664fc ; now: 4:12:58 PM
			 */
			break;
		case "sync-reentrant-no-lock":
			ReentrantTest.test0();
			break;
		case "sync-reentrant-sync":
			ReentrantTest.test1();
			break;
		case "sync-reentrant-lock":
			ReentrantTest.test2();
			break;
		case "sync-reentrant-read-write-lock":
			ReentrantTest.test3();
			break;
		case "sync-reentrant-lock-interrupt":
			ReentrantTest.test4();
			break;
		case "sync-reentrant-lock-condition":
			ReentrantTest.test5();
			break;
		default:
			break;
		}
	}
	
	public static void main(String[] args) {
		boolean bp = false;
		System.out.println("===Start testing");
		
		//test("sync-reentrant-no-lock");
		//test("sync-reentrant-sync");
		//test("sync-reentrant-lock");
		test("sync-reentrant-read-write-lock");
		//test("sync-reentrant-lock-interrupt");
		//test("sync-reentrant-lock-condition");
		
		//System.out.println("===End of testing");
	}
}


