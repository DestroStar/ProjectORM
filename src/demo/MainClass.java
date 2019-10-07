package demo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import SQL.EntityDAO;
import demo.models.Client;
import demo.models.Worker;
import storages.Entity;
import storages.MyConnection;
import storages.Table;
import transactions.Transaction;

/**
 * Class demonstrates the job with project main entities
 */
public class MainClass {

	public static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
	private static final String VERSION = "beta version";
	public static final MyConnection connection = new MyConnection(POSTGRESQL_DRIVER);
	private static final MyConnection connectionViaConnectionPool = new MyConnection();

	private static final Client CLIENT = new Client("Ivanov", "Ivan", false);
	private static final Client CLIENT2 = new Client(1, "333", "456", true);

	private static final int ITERATION_NUMBER = 10;

	public static void main(String[] args) throws Exception {

		printHeader();
		doDemo();
		closeResources();

	}

	/*
	 * Method is needed for closing ORM objects after use
	 */
	private static void closeResources() {
		connection.close();
		connectionViaConnectionPool.close();
	}

	private static void printHeader() {
		System.out.println("PROJECT MY-ORM : " + VERSION);
	}

	private static void doDemo() throws Exception {

		 Table.createTableFromEntity(new Entity(Worker.class));
/*
		List<Entity> entities = EntityDAO.getInstance().readAllRecordsOrderedByPK(new Entity(Worker.class));
		Entity entity = EntityDAO.getInstance().selectEntityById(new Entity(Worker.class), 3);
		System.out.println("");*/
		//printReceivedObjects(EntityDAO.getInstance().readAllRecordsOrderedByPK(new Entity(Worker.class)));

//		 Object en = EntityDAO.getInstance().selectEntityById(Worker.class, 7);
//		 System.out.println(en);

//		 Table.createRecordInTable(new Entity(new ImmutableWorker(12, "tes65", true, 600.5)));

//		 Table.createTableFromEntity(new Entity(Client.class));
/*
		Worker objWorker1 = new Worker("work1", true);
		Worker objWorker2 = new Worker("work2", true);
		Worker objWorker3 = new Worker("work3", true);
		Client objClient1 = new Client("sur1", "name1", false);
		Client objClient2 = new Client("sur2", "name2", false);

		 Entity worker1 = new Entity(objWorker1);
		 Entity worker2 = new Entity(objWorker2);
		 Entity worker3 = new Entity(objWorker3);
		 Entity client1 = new Entity(objClient1);
		 Entity client2 = new Entity(objClient2);
		Table.createRecordInTable(worker1);
		Table.createRecordInTable(worker2);
		Table.createRecordInTable(worker3);
		Table.createRecordInTable(client1);
		Table.createRecordInTable(client2);

		worker1.loadManyToMany(1, 1, 2);
		worker2.loadManyToMany(2, 1);
		client1.loadManyToMany(1, 2, 3);

		System.out.println(objWorker1.getClients());
		System.out.println(objWorker2.getClients());
		System.out.println(objClient1.getWorkers());
*/

//		Table.loadManyToMany(worker1,1, 1 , 2);
//		Table.loadManyToMany(client1,1, 1 , 2);

		// Table.deleteEntityTable("worker");
//		 EntityDAO.getInstance().updateRecordInTable(new Entity(new Worker(10, "super_test4",
//		 false, 1000, 23)));
//		 EntityDAO.getInstance().deleteRecordInTableByPK(new Entity(new Worker(10)));
	}

	private static void printReceivedObjects(List<Object> objects)
			throws IllegalArgumentException, IllegalAccessException {
		for (Object o : objects) {
//			Worker o = (Worker) entity.getEntityObject();
			System.out.println(((Worker) o).getId());
			// for (Field field : o.getClass().getDeclaredFields()) {
			// field.setAccessible(true);
			// System.out.printf("%14s", field.get(o));
			// }
			// System.out.println();
		}
//		System.out.println(objects.size());
	}

	private static void tryTransaction() throws Exception {
		Transaction tx = new Transaction();
		tx.openConnection();
		Table.createRecordInTable(new Entity(new Worker(12, "test1", false, 1200.5)));
		// myORM.close();
		EntityDAO.getInstance().updateRecordInTable(new Entity(new Worker(12, "test1", false, 1200.5)));
		try {
			tx.commit();
		} catch (Throwable e) {
			tx.rollback();
			e.printStackTrace();
		}
	}

	/*
	 * Method checks time of execution various ways of connection
	 */
	private static void checkProcuctivity() {

		long start = System.currentTimeMillis();
		for (int i = 0; i < ITERATION_NUMBER; i++) {
			Table.createRecordInTable(new Entity(CLIENT));
		}
		long finish = System.currentTimeMillis();
		System.out.println("Finished in : " + (finish - start) + " millis");

		start = System.currentTimeMillis();
		for (int i = 0; i < ITERATION_NUMBER; i++) {
			Table.createRecordInTable(new Entity(CLIENT));
		}
		finish = System.currentTimeMillis();
		System.out.println("Finished in : " + (finish - start) + " millis");

	}

}