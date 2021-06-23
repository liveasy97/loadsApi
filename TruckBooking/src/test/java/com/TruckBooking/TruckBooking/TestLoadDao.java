package com.TruckBooking.TruckBooking;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.TruckBooking.TruckBooking.Constants.CommonConstants;
import com.TruckBooking.TruckBooking.Dao.LoadDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.Load.UnitValue;


@DataJpaTest
public class TestLoadDao {
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private LoadDao loadDao;
	
	@Test
	public void testFindbyLoadId()
	{
		Load load = createLoads().get(0);
		Load savedindb = entityManager.persist(load);
		Load getfromdb = loadDao.findByLoadId(savedindb.getLoadId()).get();

		assertThat(getfromdb).isEqualTo(savedindb);
	}
	
	@Test
	public void testFindByAll()
	{
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		List<Load> loads = createLoads();

		for(Load i:loads)
		{
			Load savedindb = entityManager.persist(i);
		}
		List<Load> getfromdb = loadDao.findByAll(currentPage);
		assertThat(getfromdb).isEqualTo(loads);
		
	}
	
	@Test
	public void testpaginationworking()
	{
		Pageable firstPage = PageRequest.of(0, CommonConstants.pagesize), secondPage = PageRequest.of(1, CommonConstants.pagesize)
				, thirdPage = PageRequest.of(2, CommonConstants.pagesize);
		List<Load> loadlist = createLoads();

		
		for(int i=1; i<=16; i++)
		{
			Load load = new Load("load:0a5f1700-041a-43d4-b3eb-00000000000" + i, "Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh",
					"Metal Scrap", "OPEN_HALF_BODY", "4", "10000kg","01/01/21", "no comments", "pending",  (long) 505500, UnitValue.PER_TON);
			Load savedindb = entityManager.persist(load);
		}
		List<Load> getfromdb = loadDao.findByAll(firstPage);
		List<Load> getfromdb1 = loadDao.findByAll(secondPage);
		List<Load> getfromdb2 = loadDao.findByAll(thirdPage);
		assertThat(getfromdb.size()).isEqualTo(15);
		assertThat(getfromdb1.size()).isEqualTo(1);
		assertThat(getfromdb2.size()).isEqualTo(0);
	}
	
	@Test
	public void findBypostloadid()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = (Pageable) PageRequest.of(0, CommonConstants.pagesize);
		Iterable<Load> allLoads = loadDao.findByPostLoadId("id:1", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	public void findByLoadAndUnloadPoint()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointCityAndUnloadingPointCity("Nagpur", "Raipur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(3);
		
	}
	
	@Test
	public void findByTruckType()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByTruckType("OPEN_HALF_BODY", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);
	}
	
	@Test
	public void findbyloadid()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Optional<Load> load = loadDao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000002");

		assertThat(loads.get(1)).isEqualTo(load.get());
	}
	
	@Test
	public void findByLoadDate()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByLoadDate("22/01/21", currentPage);
		List<Load> list = new ArrayList<>(), resultlist = new ArrayList<>();
		resultlist.add(loads.get(0));
		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list).isEqualTo(resultlist);
	}
	
	//findByLoadingPointCity
	@Test
	public void findByLoadingPointCity()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointCity("Nagpur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}

		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByLoadingPointState
	@Test
	public void findByLoadingPointState()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByLoadingPointState("Maharashtra", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByUnloadingPointCity
	@Test
	public void findByUnloadingPointCity()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByUnloadingPointCity("Raipur", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	//findByUnloadingPointState
	@Test
	public void findByUnloadingPointState()
	{
		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));
		
		Pageable currentPage = PageRequest.of(0, CommonConstants.pagesize);
		
		Iterable<Load> allLoads = loadDao.findByUnloadingPointState("Chhattisgarh", currentPage);
		List<Load> list = new ArrayList<>();

		for (Load t : allLoads) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(3);
	}
	
	@Test
	public void testUpdate() {
		
		List<Load> loads = createLoads();
		entityManager.persist(loads.get(0));
		entityManager.persist(loads.get(1));
		entityManager.persist(loads.get(2));
		
		Load getFromDb = loadDao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000001").get();

		getFromDb.setWeight("999kg");

		entityManager.persist(getFromDb);
		
		Load getFromDb1 = loadDao.findByLoadId("load:0a5f1700-041a-43d4-b3eb-000000000001").get();
		assertThat(getFromDb1.getWeight()).isEqualTo(getFromDb.getWeight());
	}

	@Test
	public void testDelete() {

		List<Load> loads = createLoads();
		Load savedindb1 = entityManager.persist(loads.get(0));
		Load savedindb2 = entityManager.persist(loads.get(1));
		Load savedindb3 = entityManager.persist(loads.get(2));

		entityManager.remove(savedindb2);

		Iterable<Load> allTrucks = loadDao.findAll();
		List<Load> list = new ArrayList<>();

		for (Load t : allTrucks) {
			list.add(t);
		}
		assertThat(list.size()).isEqualTo(2);

	}

	public List<Load> createLoads()
	{
		List<Load> loads = Arrays.asList(
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000001","Nagpur","Nagpur","Maharashtra","id:1","Raipur","Raipur","Chhattisgarh",
			"Metal Scrap","OPEN_HALF_BODY","4","10000kg","22/01/21","no comments","pending",(long) 505500,UnitValue.PER_TON), 
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000002", "Nagpur", "Nagpur", "Maharashtra", "id:1", "Raipur", "Raipur", "Chhattisgarh",
			"Metal Scrap", "STANDARD_CONTAINER", "4", "10000kg","01/01/21", "no comments", "pending",  (long) 505500, UnitValue.PER_TON), 
		new Load("load:0a5f1700-041a-43d4-b3eb-000000000003", "Nagpur", "Nagpur", "Maharashtra", "id:2", "Raipur", "Raipur", "Chhattisgarh",
			"Metal Scrap", "OPEN_HALF_BODY", "4", "10000kg","01/01/21", "no comments", "pending",  (long) 505500, UnitValue.PER_TON)
		); 
		
		return loads;
	}
}


