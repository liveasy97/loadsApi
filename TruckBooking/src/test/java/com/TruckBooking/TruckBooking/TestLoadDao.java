
package com.TruckBooking.TruckBooking;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;

//import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Sort;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.Status;
import com.TruckBooking.TruckBooking.Entities.Load.UnitValue;



@DataJpaTest
public class TestLoadDao {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LoadDao loadDao;
	
	public static void wait(int ms)
	{
	    try
	    {
	        Thread.sleep(ms);
	    }
	    catch(InterruptedException ex)
	    {
	        Thread.currentThread().interrupt();
	    }
	}
	
	
	
	
		
	@Test
	public void testfindByloadid()
	{
		List<Load> loads = createLoads();
		
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		List<Load> load = Arrays.asList(savedindb2);

		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = (Pageable) PageRequest.of(0, CommonConstants.pagesize);
		Optional<Load> Loads = loadDao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000002");
		
		
		
		assertThat(Arrays.asList(Loads.get())).isEqualTo(load);
		
	}
	

	@Test
	public void testfindByLoadingPointCityAndUnloadingPointCityAndStatus() throws InterruptedException 
	{

		List<Load> loads = new ArrayList<Load>();
		
		for(int i=1; i<=9; i++) {
			
				
					
					Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
							"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
							));
					entityManager.flush();
					loads.add(savedindb);
					wait(1);
			}
			
		
		
		Collections.reverse(loads);
	

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		
		List<Load> Loads1 = loadDao.findByLoadingPointCityAndUnloadingPointCityAndStatus("Nagpur", "Raipur", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByLoadingPointCityAndUnloadingPointCityAndStatus("Nagpur", "Raipur", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3= loadDao.findByLoadingPointCityAndUnloadingPointCityAndStatus("Nagpur", "Raipur", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}
	
	@Test
	public void testfindByTruckTypeAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
		
			entityManager.flush();
			loads.add(savedindb);
			wait(1);
			
			 
			
		
		}
		
 
		Collections.reverse(loads);
		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByTruckTypeAndStatus("OPEN_HALF_BODY", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByTruckTypeAndStatus("OPEN_HALF_BODY", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3= loadDao.findByTruckTypeAndStatus("OPEN_HALF_BODY", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
		
		
		
	}
	
	@Test
	public void testfindByPostLoadIdAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			
			 
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByPostLoadIdAndStatus("id:1", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByPostLoadIdAndStatus("id:1", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3= loadDao.findByPostLoadIdAndStatus("id:1", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}
//	
	@Test
	public void testfindByLoadDateAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			 	
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByLoadDateAndStatus("22/01/21", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByLoadDateAndStatus("22/01/21", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByLoadDateAndStatus("22/01/21", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}

	@Test
	public void testfindByLoadingPointCityAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			 	
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByLoadingPointCityAndStatus("Nagpur", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByLoadingPointCityAndStatus("Nagpur", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByLoadingPointCityAndStatus("Nagpur", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}

	//	findByLoadingPointState
	@Test
	public void testfindByLoadingPointStateAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			 	
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByLoadingPointStateAndStatus("Maharashtra", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByLoadingPointStateAndStatus("Maharashtra", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByLoadingPointStateAndStatus("Maharashtra", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}
//
//	findByUnloadingPointCity
	@Test
	public void testfindByUnloadingPointCityAndStatus() 
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			 
			
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByUnloadingPointCityAndStatus("Raipur", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByUnloadingPointCityAndStatus("Raipur", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByUnloadingPointCityAndStatus("Raipur", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}
	
//	//findByUnloadingPointState
	@Test
	public void testfindByUnloadingPointStateAndStatus()
	{
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);				
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByUnloadingPointStateAndStatus("Chhattisgarh", Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByUnloadingPointStateAndStatus("Chhattisgarh", Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByUnloadingPointStateAndStatus("Chhattisgarh", Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
	}
	
	@Test
	public void testfindByStatus() {
		
		List<Load> loads = new ArrayList<Load>();
		for(int i=1; i<=9; i++) {
			
			Load savedindb = entityManager.persist(new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" +i,"Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
					));
			entityManager.flush();
			loads.add(savedindb);
			wait(1);			 	
		}
		
 
		Collections.reverse(loads);

		
		PageRequest firstPage = PageRequest.of(0, 5, Sort.Direction.DESC, "timestamp"),
			    secondPage = PageRequest.of(1, 5, Sort.Direction.DESC, "timestamp"),
			    thirdPage = PageRequest.of(2, 5, Sort.Direction.DESC, "timestamp");
		
		List<Load> Loads1 = loadDao.findByStatus( Load.Status.PENDING, firstPage);
		assertThat(loads.subList(0, 5)).isEqualTo(Loads1);
		List<Load> Loads2 = loadDao.findByStatus( Load.Status.PENDING, secondPage);
		assertThat(loads.subList(5, 9)).isEqualTo(Loads2);
		List<Load> Loads3 = loadDao.findByStatus( Load.Status.PENDING, thirdPage);
		assertThat(loads.subList(9, 9)).isEqualTo(Loads3);
		
	}
		



	public List<Load> createLoads()
	{
		List<Load> loads = Arrays.asList(
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000001","Nagpur","Mumbai","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
			"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")
			), 
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000002","Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
					"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134")),
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000003","Nagpur","Nagpur","Maharashtra","id:2","Raipur","Raipur","Chhattisgarh",
							"Metal Scrap","OPEN_HALF_BODY","4","10000kg","no comments","22/01/21",(long) 505500,UnitValue.PER_TON, Load.Status.PENDING, Timestamp.valueOf("2021-07-28 23:28:50.134"))
		); 
		
		return loads;
	}
	
}


